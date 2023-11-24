package com.example.musicapplication.domain

import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.UserItem

interface RemoteRepository {
    suspend fun login(user:UserItem): UserItem?
    suspend fun signup(user: UserItem): UserItem?
    suspend fun me(): UserItem?
    suspend fun getAllRooms() : List<RoomItem>
    suspend fun createNewRoom( roomName: String, password: String?,
                               isPrivate: Boolean,
                               owner: Int): RoomItem?
    suspend fun joinARoom(roomId: Int): RoomItem?
    suspend fun roomInfoById(roomId: Int): RoomItem?
    suspend fun getAllUserRooms(userId: Int): List<RoomItem>?
}