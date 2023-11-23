package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow

interface IAddNewRoomUseCase {
    suspend operator fun invoke(roomName: String, password: String?,
                                isPrivate: Boolean,
                                owner: Int): Flow<DataState<RoomItem?>>
}