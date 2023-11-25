package com.example.musicapplication.model

import androidx.compose.runtime.Stable

enum class MessageType(val code: Int) {
    ROOM_CREATED(0),
    TEXT_MESSAGE(1),
    USER_ENTERED(2),
    USER_LEFT(3),
    KICK_USER(4),
}