package com.example.musicapplication.model

import androidx.compose.runtime.Stable
import com.example.musicapplication.data.network.dto.response.MessageResponse

data class MessageItem(
    val contents: String,
    val senderName: String = "",
    val userId: Int,
    val timestamp: String = "",
    val isOwnMessage: Boolean = false,
    val type: MessageType,
    val messageState: MessageState = MessageState.Completed
)

@Stable
sealed class MessageState {
    data object Sending : MessageState()
    class SendFailed(val reason: String) : MessageState()
    data object Completed : MessageState()
}

fun MessageItem.toMessageResponse(): MessageResponse {
    return MessageResponse(
        contents = this.contents,
        type = this.type.code,
        userId = this.userId
    )
}