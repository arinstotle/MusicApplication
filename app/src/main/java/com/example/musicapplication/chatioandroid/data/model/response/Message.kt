package com.example.musicapplication.chatioandroid.data.model.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Message(
    @SerializedName("_id")
    val id: String,
    val `receiver`: String,
    val chatId: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)