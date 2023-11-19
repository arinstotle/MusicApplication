package com.example.musicapplication.data.network.dto.request

import com.example.musicapplication.utils.Constants
import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName(Constants.ID)
    val id:Int,
    @SerializedName(Constants.EMAIL)
    val email:String,
    @SerializedName(Constants.NAME)
    val name:String,
    @SerializedName(Constants.PASSWORD)
    val password:String
)