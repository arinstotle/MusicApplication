package com.example.musicapplication.data.network.dto.request

import com.example.musicapplication.data.network.dto.response.MessageResponse
import com.example.musicapplication.data.network.dto.response.UserResponse
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.toMessageResponse
import com.example.musicapplication.model.toUserResponse
import com.example.musicapplication.utils.Constants
import com.google.gson.annotations.SerializedName

data class RoomRequest(
    @SerializedName(Constants.ID)
    val id: Int?,
    @SerializedName(Constants.MESSAGES)
    val messages: List<MessageResponse>?,
    @SerializedName(Constants.NAME)
    val name: String,
    @SerializedName(Constants.OWNER)
    val ownerId: Int,
    @SerializedName(Constants.PASSWORD)
    val password: String?,
    @SerializedName(Constants.PRIVATE)
    val isPrivate: Boolean,
    @SerializedName(Constants.USERS)
    val users: List<UserResponse>?
)

fun RoomItem.toRoomRequest(): RoomRequest {
    return RoomRequest(
        id = this.id,
        messages = this.messages?.map { it.toMessageResponse() },
        name = this.roomName,
        ownerId = this.ownerId,
        password = this.password,
        isPrivate = this.isPrivate,
        users = this.roommates?.map { it.toUserResponse() }
    )
}