package com.example.musicapplication.data.network.dto.response

import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.StreamMode
import com.example.musicapplication.utils.Constants
import com.google.gson.annotations.SerializedName

data class RoomResponse(
    @SerializedName(Constants.ID)
    val id: Int,
    @SerializedName(Constants.MESSAGES)
    val messages: List<MessageResponse>?,
    @SerializedName(Constants.NAME)
    val name: String,
    @SerializedName(Constants.OWNER)
    val ownerId: Int,
    @SerializedName(Constants.PASSWORD)
    val password: String,
    @SerializedName(Constants.PRIVATE)
    val isPrivate: Boolean,
    @SerializedName(Constants.USERS)
    val users: List<UserResponse>?
)

fun List<RoomResponse>.toRoomItemList(): List<RoomItem> =
    map { roomResponse ->
        RoomItem(
            id = roomResponse.id,
            roomName = roomResponse.name,
            ownerId = roomResponse.ownerId,
            messages = roomResponse.messages?.map { it.toMessageItem() },
            password = roomResponse.password,
            isPrivate = roomResponse.isPrivate,
            isStreaming = StreamMode.GOING,
            roommates = roomResponse.users?.map { it.toItem() },
            queue = emptyList()
        )
    }

fun RoomResponse.toRoomItem(): RoomItem {
    return RoomItem(
        id = id,
        roomName = name,
        ownerId = ownerId,
        messages = messages?.map { it.toMessageItem() },
        password = password,
        isPrivate = isPrivate,
        isStreaming = StreamMode.GOING,
        roommates = users?.map { it.toItem() },
        queue = emptyList()
    )
}
