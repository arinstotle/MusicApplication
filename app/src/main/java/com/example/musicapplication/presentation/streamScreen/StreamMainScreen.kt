package com.example.musicapplication.presentation.streamScreen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicapplication.presentation.theme.MusicApplicationTheme
import com.example.musicapplication.presentation.viewModels.StreamViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable fun StreamMainScreen(
    navController: NavHostController,
    roomId: Int?,
    viewModel: StreamViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val scope = rememberCoroutineScope()

    val songSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showSongBottomSheet by remember { mutableStateOf(false) }

    var currentSong by remember {
        mutableStateOf<SongUI?>(null)
    }

    MusicApplicationTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF26292D)),
            topBar = {
                StreamAppBar(
                    playOnClick = { viewModel.loadTheSong() },
                    songState = viewModel.songLoaded,
                    roomName = "$roomId",
                    message = "",
                    backButtonClickListener = {
                    },
                    pauseClickListener = { },
                    scrollState = LazyListState()
                )
            }
        ) { innerPadding ->
            if (showSongBottomSheet) {
                ModalBottomSheet(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    scrimColor = Color(0x99000000),
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),
                    onDismissRequest = {
                        showSongBottomSheet = false
                    },
                    sheetState = songSheetState,
                    content = {
                        scope.launch {
                           songSheetState.expand()
                        }
                        SongComposable({ OnBackPressedDispatcher() }, currentSong)
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
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
                    3 -> StreamMusicQueueComposable(songList) { song ->
                        currentSong = song
                        showSongBottomSheet = true
                    }
                    else -> {}
                }
            }
        }
    }
}