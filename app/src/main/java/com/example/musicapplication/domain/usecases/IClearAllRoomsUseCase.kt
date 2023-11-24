package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow

interface IClearAllRoomsUseCase {
    suspend operator fun invoke()
}