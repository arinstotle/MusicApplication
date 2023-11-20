package com.example.musicapplication.model

enum class MessageState(val code: Int) {
    ROOM_CREATED(0),
    TEXT_MESSAGE(1),
    KICK_USER(2),
    USER_ENTERED(3),
    USER_LEFT(4)
}