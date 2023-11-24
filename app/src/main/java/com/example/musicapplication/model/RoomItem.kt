package com.example.musicapplication.model

import com.example.musicapplication.data.room.RoomEntity

data class RoomItem(
    val id: Int?,
    val roomName: String,
    val ownerId: Int,
    val messages: List<MessageItem>?,
    val password: String?,
    val isPrivate: Boolean,
    val isStreaming: StreamMode,
    val roommates: List<UserItem>?,
    val queue: List<AudioItem>?
)

fun RoomItem.toRoomEntity(): RoomEntity {
    return RoomEntity(
        roomName = this.roomName,
        numberOfParticipants = this.roommates?.size?.toString() ?: "0",
        numberOfSongs = this.queue?.size?.toString() ?: "0",
        isPrivate = this.isPrivate,
        id = this.id ?: 0
    )
}