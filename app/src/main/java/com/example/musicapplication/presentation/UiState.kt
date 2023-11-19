package com.example.musicapplication.presentation

sealed class UiState<out T>(val data: T?=null) {
    class Loading<T>(data: T? = null) : UiState<T>(data)
    class Success<T>(data: T?, val message: String? = null) : UiState<T>(data)
    class Error<T>(data: T?, val message: String) : UiState<T>(data)
}