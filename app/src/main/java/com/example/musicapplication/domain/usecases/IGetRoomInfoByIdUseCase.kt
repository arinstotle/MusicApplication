package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IGetRoomInfoByIdUseCase {
    suspend operator fun invoke(id: Int): Flow<DataState<RoomItem?>>

}