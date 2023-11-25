package com.example.musicapplication.chatioandroid.data.model.response


import androidx.annotation.Keep
import com.example.musicapplication.chatioandroid.data.model.response.Chat

@Keep
data class ChatListResponse(
    val chats: List<Chat>,
    val count: Int
)