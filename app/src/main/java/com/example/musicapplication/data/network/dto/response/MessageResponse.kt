package com.example.musicapplication.data.network.dto.response

import com.example.musicapplication.model.MessageItem
import com.example.musicapplication.model.MessageType
import com.example.musicapplication.utils.Constants
import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName(Constants.CONTENT)
    val contents: String,
    @SerializedName(Constants.TYPE)
    val type: Int,
    @SerializedName(Constants.USERID)
    val userId: Int
)

fun MessageResponse.toMessageItem(): MessageItem =
    MessageItem(contents = contents, userId = userId, type =
    when(type) {
        0 -> MessageType.ROOM_CREATED
        1 -> MessageType.TEXT_MESSAGE
        2 -> MessageType.KICK_USER
        3 -> MessageType.USER_ENTERED
        4 -> MessageType.USER_LEFT
        else -> {
            MessageType.TEXT_MESSAGE
        }
    })