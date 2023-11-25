package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow

interface IGetUsersRoomsUseCase {
    suspend operator fun invoke(userId:Int): Flow<DataState<List<RoomItem>?>>
}