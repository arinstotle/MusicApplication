package com.example.musicapplication.domain.repo

import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.UserItem

interface Repository {
    suspend fun login(user:UserItem): UserItem?

    suspend fun signup(user: UserItem): UserItem?

    suspend fun me(): UserItem?
    suspend fun getAllRooms(order: OrdersTypes) : List<RoomItem>
    suspend fun createNewRoom(roomItem: RoomItem): RoomItem?
    suspend fun joinARoom(roomId: Int): RoomItem?
    suspend fun roomInfoById(roomId: Int): RoomItem?
}