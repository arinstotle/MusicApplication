package com.example.musicapplication.utils

import com.example.musicapplication.model.AudioItem
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.StreamMode

object Constants {
    const val PROFILE_HEADER = "Profile"
    const val SEARCH_MUSIC_HEADER = "Music"

    val mockedUser = UserItem(id = 0, email = "a@mail.ru", password = "1234", name = "music_crazy", photoUrl = null)
    val mockedUserList = listOf(
        mockedUser,
        mockedUser,
        mockedUser
    )
    val mockedAudio = AudioItem(
        title = "Idol",
        artist = "BTS",
        duration = 207000,
        coverUrl = null
    )
    val mockedAudioList = listOf(
        mockedAudio,
        mockedAudio,
        mockedAudio,
        mockedAudio
    )
    val mockedRoom = RoomItem(
        roomName = "chilling with bros",
        isStreaming = StreamMode.PAUSED,
        roommates = mockedUserList,
        queue = mockedAudioList
    )
    val mockedRoomList = listOf(
        mockedRoom,
        mockedRoom,
        mockedRoom,
        mockedRoom,
        mockedRoom
    )

    //api
    const val EMAIL = "email"
    const val ID = "id"
    const val NAME = "name"
    const val PASSWORD = "password"

    const val BASE_URL = "http://46.19.65.33:8080/"
    const val PATH_LOGIN = "login/"
    const val PATH_ME = "me/"
    const val PATH_SIGNUP = "signup/"
    const val AUTH_TOKEN = "Authorization"
    const val TOKEN = "token"
    const val USER = "user"

    const val TIMEOUT:Long = 120

    const val KEY = "AUTH TOKEN"
    const val SHARED_PREF = "SHARED PREFERENCES"

    const val HASH_FUNC = "SHA-256"
    const val HEADER_COOKIE = "Set-Cookie"
}