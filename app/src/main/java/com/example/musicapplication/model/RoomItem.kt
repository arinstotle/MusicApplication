package com.example.musicapplication.model

data class RoomItem(
    val id: Int,
    val roomName: String,
    val ownerId: Int,
    val messages: List<MessageItem>,
    val password: String,
    val isPrivate: Boolean,
    val isStreaming: StreamMode,
    val roommates: List<UserItem>,
    val queue: List<AudioItem>
)