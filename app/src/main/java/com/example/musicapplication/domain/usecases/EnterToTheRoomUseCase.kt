package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EnterToTheRoomUseCase @Inject constructor(
    private val repository: RemoteRepository
) : IEnterToTheRoomUseCase {
    override suspend fun invoke(
       roomId: Int
    ): Flow<DataState<RoomItem?>> = flow {
        emit(DataState.Initial)
        val entered = repository.joinARoom(roomId)
        emit(DataState.Result(entered))
    }.catch {
        emit(DataState.Exception(it))
    }
}