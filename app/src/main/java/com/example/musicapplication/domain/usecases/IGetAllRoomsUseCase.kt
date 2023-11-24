package com.example.musicapplication.domain.usecases

import com.example.musicapplication.data.network.state.NetworkState
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import kotlinx.coroutines.flow.Flow

interface IGetAllRoomsUseCase {
    operator fun invoke(order: OrdersTypes): Flow<DataState<List<RoomItem>>>
}