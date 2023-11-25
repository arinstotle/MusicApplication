package com.example.musicapplication.domain.usecases

import android.util.Log
import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersRoomsUseCase @Inject constructor(
    private val remoteRepo:RemoteRepositoryImpl
):IGetUsersRoomsUseCase {
    override suspend fun invoke(userId: Int): Flow<DataState<List<RoomItem>?>> = flow {
        emit(DataState.Initial)
        emit(DataState.Result(remoteRepo.getAllUserRooms(userId)))
    }.catch {
        Log.d("UC ROOMS", "EXC")
        emit(DataState.Exception(it))
    }
}