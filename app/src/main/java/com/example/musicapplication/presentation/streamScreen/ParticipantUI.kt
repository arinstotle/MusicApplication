package com.example.musicapplication.presentation.streamScreen

data class ParticipantUI (
    val avatarURL: String,
    val name: String,
    val lastname: String,
    val isCreator: Boolean
)

val roommates = listOf(
    ParticipantUI(
        avatarURL = "url_1",
        name = "John",
        lastname = "Doe",
        isCreator = true
    ),
    ParticipantUI(
        avatarURL = "url_2",
        name = "Jane",
        lastname = "Smith",
        isCreator = false
    ),
    ParticipantUI(
        avatarURL = "url_3",
        name = "Alice",
        lastname = "Johnson",
        isCreator = false
    )
    // Добавьте здесь других соседей по аналогии
)