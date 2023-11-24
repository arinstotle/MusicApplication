package com.example.musicapplication.domain.usecases

import com.example.musicapplication.model.UserItem
import com.example.musicapplication.presentation.UiState

interface IMeUseCase{
    suspend operator fun invoke(): UiState<UserItem>
}