package com.example.musicapplication.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @ColumnInfo(name = "room_name")
    val roomName: String,
    @ColumnInfo(name = "number_of_participants")
    val numberOfParticipants: String,
    @ColumnInfo(name = "number_of_songs")
    val numberOfSongs: String,
    @ColumnInfo(name = "is_private")
    val isPrivate: Boolean,
    @ColumnInfo(name = "room_id")
    @PrimaryKey(autoGenerate = false)
    val id: Int
)

