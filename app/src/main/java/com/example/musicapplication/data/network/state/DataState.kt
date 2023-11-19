package com.example.musicapplication.data.network.state

sealed class DataState<out T> {
    data object Initial : DataState<Nothing>()
    data class Result<T>(val data: T) : DataState<T>()
    data class Exception(val cause: Throwable): DataState<Nothing>()
}