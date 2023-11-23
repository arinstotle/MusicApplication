package com.example.musicapplication.domain.usecases

import android.util.Log
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddNewRoomUseCase @Inject constructor(
    private val repository: RemoteRepository
) : IAddNewRoomUseCase {
    override suspend fun invoke(
        roomName: String, password: String?,
        isPrivate: Boolean,
        owner: Int
    ): Flow<DataState<RoomItem?>> = flow {
        Log.d("tag","vfji")
        emit(DataState.Initial)
        val newRoom = repository.createNewRoom(roomName, password, isPrivate, owner)
        emit(DataState.Result(newRoom))
    }.catch {
        emit(DataState.Exception(it))
    }
}