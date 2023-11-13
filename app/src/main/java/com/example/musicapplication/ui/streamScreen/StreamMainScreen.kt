package com.example.musicapplication.ui.streamScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicapplication.theme.MusicApplicationTheme
import com.example.musicapplication.viewModels.StreamViewModel

@Composable fun StreamMainScreen(
    viewModel: StreamViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    MusicApplicationTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF26292D)),
            topBar = {
                StreamAppBar(
                    playOnClick = { viewModel.loadTheSong() },
                    songState = viewModel.songLoaded,
                    roomName = "",
                    message = "",
                    backButtonClickListener = {
                    },
                    pauseClickListener = { },
                    scrollState = LazyListState()
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF26292D))
                    .padding(innerPadding)
            ) {
                var selectedChipIndex by remember {
                    mutableStateOf(3)
                }
                PanelSection(selectedChipIndex = selectedChipIndex,
                    clickButton1 = {
                        selectedChipIndex = 1
                    }, clickButton2 = {
                        selectedChipIndex = 2
                    }, clickButton3 = {
                        selectedChipIndex = 3
                    })
                when (selectedChipIndex) {
                    1 -> StreamParticipantsComposable(participantsList = roommates,
                        addParticipantClick = {},
                        removeParticipantClick = {})
                    2 -> Text("Тут будет чат")
                    3 -> StreamMusicQueueComposable(songList)
                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun StreamMainScreenPreview() {
    StreamMainScreen()
}