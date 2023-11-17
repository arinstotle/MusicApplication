package com.example.musicapplication.model

data class RoomItem(
    val roomName:String,
    val isStreaming:StreamMode,
    val roommates:List<UserItem>,
    val queue:List<AudioItem>
)