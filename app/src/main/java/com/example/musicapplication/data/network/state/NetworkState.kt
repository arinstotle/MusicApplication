package com.example.musicapplication.data.network.state

sealed class NetworkState<out T> {
    data object Initial : NetworkState<Nothing>()
    data class Result<T>(val data: T) : NetworkState<T>()
    data class Exception(val cause: Throwable): NetworkState<Nothing>()
}