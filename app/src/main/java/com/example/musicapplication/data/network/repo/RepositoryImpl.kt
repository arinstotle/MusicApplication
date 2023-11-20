package com.example.musicapplication.data.network.repo

import com.example.musicapplication.data.network.api.NetworkSource
import com.example.musicapplication.data.network.exceptions.MainNetworkException
import com.example.musicapplication.data.network.exceptions.WrongDataException
import com.example.musicapplication.data.network.state.NetworkState
import com.example.musicapplication.domain.repo.Repository
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.UserItem
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource
):Repository {
    override suspend fun login(user: UserItem): UserItem? {
        val state = networkSource.login(user)
        when(state){
            is NetworkState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is NetworkState.Result -> {
                return state.data
            }

            else -> return null
        }
        return null
    }

    override suspend fun signup(user: UserItem): UserItem? {
        val state = networkSource.signup(user)
        when(state){
            is NetworkState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is NetworkState.Result -> {
                return state.data
            }

            else -> return null
        }
        return null
    }

    override suspend fun me(): UserItem? {
        val state = networkSource.me()
        when(state){
            is NetworkState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is NetworkState.Result -> {
                return state.data
            }

            else -> return null
        }
        return null
    }

    @Throws(MainNetworkException::class)
    override suspend fun getAllRooms(order: OrdersTypes): List<RoomItem> {
        var rooms: List<RoomItem> = emptyList()
        networkSource.getRooms().collect { state ->
            rooms = when (state) {
                is NetworkState.Exception -> throw MainNetworkException("Internet is not available!")
                is NetworkState.Result -> {
                    when (order) {
                        OrdersTypes.NATURAL -> state.data
                        OrdersTypes.ALPHABETICAL -> state.data.sortedBy { it.roomName.toLowerCase() }
                        OrdersTypes.REVERSE_ALPHABETICAL -> state.data.sortedByDescending { it.roomName.toLowerCase() }
                    }
                }
                else -> emptyList()
            }
        }
        return rooms
    }

    @Throws(MainNetworkException::class)
    override suspend fun createNewRoom(roomItem: RoomItem): RoomItem? {
        var room: RoomItem? = null
        networkSource.createRoom(
            roomItem
            ).collect {
                when (it) {
                    is NetworkState.Exception -> throw MainNetworkException("Room is not created!")
                    is NetworkState.Result -> room = it.data
                    else -> {}
                }
            }
        return room
    }
    @Throws(MainNetworkException::class)
    override suspend fun joinARoom(roomId: Int): RoomItem? {
        var room: RoomItem? = null
        networkSource.joining(
            roomId
        ).collect {
            when (it) {
                is NetworkState.Exception -> throw MainNetworkException("Unable to connect to the room!")
                is NetworkState.Result -> room = it.data
                else -> {}
            }
        }
        return room
    }
    @Throws(MainNetworkException::class)
    override suspend fun roomInfoById(roomId: Int): RoomItem? {
        var room: RoomItem? = null
        networkSource.getRoomInfoById(
            roomId
        ).collect {
            when (it) {
                is NetworkState.Exception -> throw MainNetworkException("Error getting information about the room!")
                is NetworkState.Result -> room = it.data
                else -> {}
            }
        }
        return room
    }

}