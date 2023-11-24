package com.example.musicapplication.data.room.repo

import com.example.musicapplication.data.room.RoomEntity
import com.example.musicapplication.data.room.RoomState
import com.example.musicapplication.data.room.RoomsDao
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.toRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val roomsDao: RoomsDao
) {
    fun getRooms(): Flow<RoomState<List<RoomEntity>>> = flow {
        emit(RoomState.Initial)
        roomsDao.getRooms().collect { rooms ->
            emit(RoomState.Success(rooms))
        }
    }.catch {
        emit(RoomState.Failure(it))
    }

    suspend fun addRoom(room: RoomItem) = roomsDao.addRoom(room.toRoomEntity())

    suspend fun updateRoom(room: RoomItem) = roomsDao.updateRoom(room.toRoomEntity())

    suspend fun deleteRoom(roomId: Int) = roomsDao.deleteRoom(roomId)

    fun clearDatabase() {
        roomsDao.removeRooms()
    }

    fun overwriteDatabase(rooms: List<RoomItem>) {
        roomsDao.addRooms(rooms.map(RoomItem::toRoomEntity))
    }
}