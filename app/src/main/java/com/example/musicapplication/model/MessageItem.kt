package com.example.musicapplication.model

import com.example.musicapplication.data.network.dto.response.MessageResponse

data class MessageItem(
    val contents: String,
    val userId: Int,
    val type: MessageState
)

fun MessageItem.toMessageResponse(): MessageResponse {
    return MessageResponse(
        contents = this.contents,
        type = this.type.code,
        userId = this.userId
    )
}