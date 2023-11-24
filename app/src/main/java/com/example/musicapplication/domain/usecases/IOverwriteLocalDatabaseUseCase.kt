package com.example.musicapplication.domain.usecases

import com.example.musicapplication.model.RoomItem

interface IOverwriteLocalDatabaseUseCase {
    suspend operator fun invoke(userId:Int)
}