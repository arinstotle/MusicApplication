package com.example.musicapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomEntity::class],
    version = 1
)
abstract class RoomsDatabase: RoomDatabase() {
    abstract fun roomDao(): RoomsDao
}