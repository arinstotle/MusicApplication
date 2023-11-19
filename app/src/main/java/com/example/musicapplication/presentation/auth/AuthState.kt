package com.example.musicapplication.presentation.auth

import com.example.musicapplication.model.UserItem

data class AuthState(
    val user:UserItem,
    val isCreated:Boolean,
    val isAuthorized:Boolean,
    val isWrongData:Boolean
)