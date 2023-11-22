package com.example.musicapplication.presentation.profile

import com.example.musicapplication.presentation.auth.AuthEvent
import com.example.musicapplication.presentation.auth.AuthState

sealed class ProfileEvent {
    data object OnLogout : ProfileEvent()
}