package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow

interface  IEnterToTheRoomUseCase {
    suspend operator fun invoke(roomId: Int, password: String = ""): Flow<DataState<RoomItem?>>
}