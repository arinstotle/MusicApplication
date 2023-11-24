package com.example.musicapplication.data.room.repo

import com.example.musicapplication.data.network.exceptions.MainNetworkException
import com.example.musicapplication.data.room.RoomEntity
import com.example.musicapplication.data.room.RoomState
import com.example.musicapplication.data.room.exceptions.ValidationException
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.LocalRepository
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.StreamMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val databaseSource: DatabaseSource,
    private val remoteRepository: RemoteRepository
): LocalRepository {

    override fun getRooms(): Flow<DataState<List<RoomEntity>>> = flow {
        databaseSource.getRooms().collect {
            when (it) {
                is RoomState.Initial -> DataState.Initial
                is RoomState.Success -> emit(DataState.Result(it.data))
                is RoomState.Failure -> emit(DataState.Exception(it.cause))
            }
        }
    }

    @Throws(ValidationException::class, MainNetworkException::class)
    override suspend fun addRoom(roomName: String, password: String?,
                                 isPrivate: Boolean, owner: Int) {
        validation(roomName, password, isPrivate)
        val task = buildRoomModel(roomName, password, isPrivate, owner)
        databaseSource.addRoom(task)
        remoteRepository.createNewRoom(roomName, password, isPrivate, owner)
    }

    // когда-то здесь будет удаление. After a long time...
//    @Throws(MainNetworkException::class)
//    override suspend fun deleteRoom(room: RoomItem) {
//        databaseSource.deleteRoom(room.id)
//        remoteRepository.deleteRoom(room)
//    }

    // когда-то здесь будет обновление. After a long time...
//    @Throws(ValidationException::class, MainNetworkException::class)
//    override suspend fun updateRoom(room: RoomItem) {
//        validation(room.roomName, room.password, room.isPrivate)
//        databaseSource.updateRoom(room)
//        remoteRepository.updateRoom(room)
//    }

    override fun clearDatabase() {
        databaseSource.clearDatabase()
    }

    @Throws(MainNetworkException::class)
    override suspend fun overwriteLocalDatabase(userId: Int) {
        val rooms = remoteRepository.getAllUserRooms(userId)
        if (rooms != null)
            databaseSource.overwriteDatabase(rooms)
    }

    @Throws(ValidationException::class)
    private fun validation(roomName: String, password: String?,
                           isPrivate: Boolean) {
        if (roomName.isBlank()) {
            throw ValidationException("Room name is blank!")
        }
        if (isPrivate && password.isNullOrBlank()
            ) {
            throw ValidationException("Password is blank!")
        }
    }

    private fun buildRoomModel(
        roomName: String, password: String?,
        isPrivate: Boolean,
        owner: Int
    ): RoomItem = RoomItem(
        null,
        roomName,
        owner,
        null,
        password,
        isPrivate,
        StreamMode.SLEEP,
        null,
        null
    )

}