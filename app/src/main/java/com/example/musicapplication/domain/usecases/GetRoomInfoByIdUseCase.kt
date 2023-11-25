package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoomInfoByIdUseCase @Inject constructor(
    private val repository: RemoteRepository
): IGetRoomInfoByIdUseCase {
    override suspend fun invoke(
       id: Int
    ): Flow<DataState<RoomItem?>> = flow {
        emit(DataState.Initial)
        val room = repository.roomInfoById(id)
        emit(DataState.Result(room))
    }.catch {
        emit(DataState.Exception(it))
    }
}