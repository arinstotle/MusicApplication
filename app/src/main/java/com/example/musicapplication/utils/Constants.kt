package com.example.musicapplication.utils

import com.example.musicapplication.model.AudioItem
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.StreamMode

object Constants {
    const val PROFILE_HEADER = "Profile"
    const val SEARCH_MUSIC_HEADER = "Music"

    val mockedUser = UserItem(nickname = "music_crazy", photoUrl = null)
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
}