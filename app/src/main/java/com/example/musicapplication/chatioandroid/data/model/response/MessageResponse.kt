package com.example.musicapplication.chatioandroid.data.model.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MessageResponseSocket(
    val sender: String,
    val `receiver`: String,
    val chatId: String,
    val text: String,
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)