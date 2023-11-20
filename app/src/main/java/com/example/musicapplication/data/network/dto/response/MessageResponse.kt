package com.example.musicapplication.data.network.dto.response

import com.example.musicapplication.model.MessageItem
import com.example.musicapplication.model.MessageState
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
        0 -> MessageState.ROOM_CREATED
        1 -> MessageState.TEXT_MESSAGE
        2 -> MessageState.KICK_USER
        3 -> MessageState.USER_ENTERED
        4 -> MessageState.USER_LEFT
        else -> {
            MessageState.TEXT_MESSAGE
        }
    })