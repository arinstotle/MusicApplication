package com.example.musicapplication.domain.usecases

import android.util.Log
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllRoomsUseCase @Inject constructor(
    private val repository: RemoteRepository
) : IGetAllRoomsUseCase {
    override operator fun invoke(order: OrdersTypes): Flow<DataState<List<RoomItem>>> = flow {
        emit(DataState.Initial)
        when (order) {
            OrdersTypes.NATURAL -> {
                emit(DataState.Result(repository.getAllRooms()))
            }
            OrdersTypes.ALPHABETICAL -> {
                emit(DataState.Result(repository.getAllRooms().sortedBy { it.roomName }))
            }
            OrdersTypes.REVERSE_ALPHABETICAL -> {
                emit(DataState.Result(repository.getAllRooms().sortedByDescending { it.roomName }))
            }
        }
    }.catch {
        Log.d("UC ROOMS", "EXC")
        emit(DataState.Exception(it))
    }
}