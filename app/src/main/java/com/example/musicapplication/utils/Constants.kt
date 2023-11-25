package com.example.musicapplication.utils

import androidx.compose.ui.unit.dp
import com.example.musicapplication.model.AudioItem
import com.example.musicapplication.model.MessageItem
import com.example.musicapplication.model.MessageType
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
        id = 1,
        roomName = "Комната 1",
        ownerId = 101,
        messages = listOf(
            MessageItem("Комната комната1 создана", "System", -1, "12:30", false, MessageType.ROOM_CREATED),
            MessageItem("Шашлык вошел в чат", "System", -1, "12:32", false, MessageType.USER_ENTERED),
            MessageItem("Помидорка вошел в чат", "System", -1, "12:32", false, MessageType.USER_ENTERED)
        ),
        password = "securePassword",
        isPrivate = true,
        isStreaming = StreamMode.GOING,
        roommates = listOf(
            UserItem(201, "user1@example.com", "password1", null, "User 1"),
            UserItem(202, "user2@example.com", "password2", null, "User 2")
        ),
        queue = listOf(
            AudioItem("https://audio1.mp3", 180, "best song", "best author"),
            AudioItem("https://audio2.mp3", 240, "best song", "best author")
        )
    )

    val mockedRoomList = listOf(
        mockedRoom,
        mockedRoom,
        mockedRoom,
        mockedRoom,
        mockedRoom
    )

    val AppBarCollapsedHeight = 100.dp
    val AppBarExpendedHeight = 140.dp
    const val playIcon = 1
    const val loadingBar = 2
    const val pauseIcon = 3

    //api
    const val EMAIL = "email"
    const val ID = "id"
    const val NAME = "name"
    const val PASSWORD = "password"

    const val MESSAGES = "messages"
    const val OWNER = "owner_id"
    const val PRIVATE = "private"
    const val USERS = "user_ids"
    const val CONTENT = "contents"
    const val TYPE = "type"
    const val USERID = "userId"

    const val BASE_URL = "http://85.193.80.129:8080/"
    const val PATH_USERS = "/users/"
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

    const val PATH_ROOMS = "/rooms"
    const val PATH_ROOMS_WITH_ID = "/rooms/"
}