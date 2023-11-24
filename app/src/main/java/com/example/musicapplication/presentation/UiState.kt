package com.example.musicapplication.presentation

sealed class UiState<out T>(val data: T?=null) {
    data object Start : UiState<Nothing>()
    class Loading<T>(data: T? = null) : UiState<T>(data)
    class Success<T>(data: T?, val message: String? = null) : UiState<T>(data)
    class Error<T>(data: T?, val message: String) : UiState<T>(data)
    data class FatalError(val cause: String) : UiState<Nothing>()
    data object OK: UiState<Nothing>()

}