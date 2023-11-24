package com.example.musicapplication.data.room

sealed class RoomState <out T> {
    object Initial : RoomState<Nothing>()
    data class Success<T>(val data: T) : RoomState<T>()
    data class Failure(val cause: Throwable): RoomState<Nothing>()
}