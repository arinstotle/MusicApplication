package com.example.musicapplication.presentation.auth

sealed class AuthEvent{
    data class OnLogin(val authState: AuthState?):AuthEvent()
    data class OnRegister(val authState: AuthState):AuthEvent()
}