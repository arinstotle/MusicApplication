package com.example.musicapplication.presentation.streamScreen

import com.example.musicapplication.presentation.auth.AuthEvent
import com.example.musicapplication.presentation.auth.AuthState

sealed class StreamUserEvent(){
    data class OnInvite(val email:String): StreamUserEvent()
    //data class OnKick: StreamUserEvent()
}