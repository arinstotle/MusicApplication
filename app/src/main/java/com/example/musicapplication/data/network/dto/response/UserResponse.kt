package com.example.musicapplication.data.network.dto.response

import com.example.musicapplication.model.UserItem
import com.example.musicapplication.utils.Constants
import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName(Constants.ID)
    val id:Int,
    @SerializedName(Constants.EMAIL)
    val email:String,
    @SerializedName(Constants.NAME)
    val name:String,
    @SerializedName(Constants.PASSWORD)
    val password:String
)

fun UserResponse.toItem():UserItem = UserItem(
    id = id, email = email, name = name, password = password, photoUrl = null
)

