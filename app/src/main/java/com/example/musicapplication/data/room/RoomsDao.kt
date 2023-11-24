package com.example.musicapplication.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomsDao {
    @Insert(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRoom(room: RoomEntity)

    @Query("DELETE FROM rooms WHERE room_id = :roomId")
    suspend fun deleteRoom(roomId: Int)

    @Update
    suspend fun updateRoom(room: RoomEntity)

    @Query("SELECT * FROM rooms")
    fun getRooms(): Flow<List<RoomEntity>>

    @Insert(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addRooms(tasks: List<RoomEntity>)

    @Query("DELETE FROM rooms")
    fun removeRooms()
}