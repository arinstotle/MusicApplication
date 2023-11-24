package com.example.musicapplication.domain

import com.example.musicapplication.data.room.RoomEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getRooms(): Flow<DataState<List<RoomEntity>>>
    suspend fun addRoom(
        roomName: String, password: String?,
        isPrivate: Boolean, owner: Int
    )

    fun clearDatabase()
    suspend fun overwriteLocalDatabase(userId: Int)
}