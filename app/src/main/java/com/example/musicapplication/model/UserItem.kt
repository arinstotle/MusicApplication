package com.example.musicapplication.model

import com.example.musicapplication.data.network.dto.request.UserRequest

data class UserItem(
    val id:Int,
    val email:String,
    val password:String,
    val photoUrl:String?,
    val name:String
)

fun UserItem.toRequest() = UserRequest(
    id, email, name, password
)

fun emptyUser() = UserItem(id = 0, name = "", email = "", password = "", photoUrl = null)