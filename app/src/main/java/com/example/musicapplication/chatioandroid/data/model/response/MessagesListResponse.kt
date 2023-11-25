package com.kakyiretechnologies.chatioandroid.data.model.response


import androidx.annotation.Keep
import com.example.musicapplication.chatioandroid.data.model.response.Message

@Keep
data class MessagesListResponse(
    val messages: List<Message>,
    val count: Int
)