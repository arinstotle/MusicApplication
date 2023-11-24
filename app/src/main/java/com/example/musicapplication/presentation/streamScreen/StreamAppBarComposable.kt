package com.example.musicapplication.presentation.streamScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.musicapplication.R
import com.example.musicapplication.utils.Constants.AppBarCollapsedHeight
import com.example.musicapplication.utils.Constants.AppBarExpendedHeight
import com.example.musicapplication.utils.Constants.loadingBar
import com.example.musicapplication.utils.Constants.pauseIcon
import com.example.musicapplication.utils.Constants.playIcon


@Composable
fun StreamAppBar(playOnClick: () -> Unit,
                 songState: MutableLiveData<Boolean>,
                 roomName: String,
                 message: String,
                 backButtonClickListener: () -> Unit,
                 pauseClickListener: () -> Unit,
                 scrollState: LazyListState) {
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() } - 700
    val offset = kotlin.math.min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = kotlin.math.max(0f, offset * 3f - 2f + maxOffset) / maxOffset
    androidx.compose.material.TopAppBar(
        contentPadding = PaddingValues(0.dp),
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .heightIn(min = AppBarExpendedHeight)
            .padding(0.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            ),
        elevation = if (offset == maxOffset) 20.dp else 0.dp,
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                ),
                colors = CardDefaults
                    .cardColors(MaterialTheme.colorScheme.onPrimary)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        ) {
                        IconButton(
                            onClick = backButtonClickListener,
                            modifier = Modifier.size(35.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                                contentDescription = "Play",
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp)
                            ) {
                                Text(
                                    text = roomName,
                                    style = TextStyle(
                                        fontSize = 21.sp,
                                        fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Translation is going",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                        fontWeight = FontWeight(700),
                                        color = MaterialTheme.colorScheme.onSecondary,
                                        textAlign = TextAlign.End
                                    )
                                )
                            }
                    }
                    Box(
                        modifier = Modifier.size(55.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            MusicButton(playOnClick, songState = songState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PanelSection(
    selectedChipIndex: Int,
    clickButton1: () -> Unit,
    clickButton2: () -> Unit,
    clickButton3: () -> Unit) {
    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "STREAM",
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center,
                letterSpacing = 2.09.sp,
            ),
            modifier = Modifier.padding(top = 32.dp, start = 8.dp, bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            IconButton(
                onClick = clickButton1,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.users_icon),
                    modifier = Modifier.size(35.dp),
                    tint = if (selectedChipIndex == 1)
                        MaterialTheme.colorScheme.onTertiary
                    else
                        MaterialTheme.colorScheme.tertiary,
                    contentDescription = null)
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = clickButton2,
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = if (selectedChipIndex == 2)
                        MaterialTheme.colorScheme.onTertiary
                    else
                        MaterialTheme.colorScheme.tertiary,
                    imageVector = ImageVector.vectorResource(id = R.drawable.chat_icon),
                    contentDescription = null)
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = clickButton3,
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    tint = if (selectedChipIndex == 3)
                        MaterialTheme.colorScheme.onTertiary
                    else
                        MaterialTheme.colorScheme.tertiary,
                    imageVector = ImageVector.vectorResource(id = R.drawable.playlist_icon),
                    contentDescription = null)
            }
        }
    }
}

@Composable
fun MusicButton(
    loader: () -> Unit,
    songState: MutableLiveData<Boolean>,
    context: Context = LocalContext.current.applicationContext
) {
    val songLoaded by songState.observeAsState(false)
    var buttonIcon by remember {
        mutableStateOf(playIcon)
    }
    OutlinedButton(
        modifier = Modifier
            .size(72.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        onClick = {
            if (!songLoaded) {
                Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                loader.invoke()
                buttonIcon = loadingBar
            } else {
                if (buttonIcon == playIcon) {
                    buttonIcon = pauseIcon
                } else if (buttonIcon == pauseIcon) {
                    buttonIcon = playIcon
                }
            }
        }
    ) {
        when (buttonIcon) {
            loadingBar -> {
                if (songLoaded) {
                    buttonIcon = pauseIcon
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                }
            }
            playIcon -> {
                SetButtonIcon(
                    icon = Icons.Filled.PlayArrow,
                    iconDescription = "Play Song"
                )
                if (songLoaded) {
                    pauseTheSong(context = context)
                }
            }
            pauseIcon -> {
                SetButtonIcon(icon = ImageVector.vectorResource(R.drawable.pause_icon), iconDescription = "Pause Song")
                if (songLoaded) {
                    playTheSong(context = context)
                }
            }
        }
    }
}

private fun playTheSong(context: Context) {
    Log.i("SemicolonSpace", "playTheSong()")
    Toast.makeText(context, "Playing....", Toast.LENGTH_SHORT).show()
}

private fun pauseTheSong(context: Context) {
    Log.i("SemicolonSpace", "pauseTheSong")
    Toast.makeText(context, "Paused", Toast.LENGTH_SHORT).show()
}

@Composable
private fun SetButtonIcon(
    icon: ImageVector,
    iconDescription: String
) {
    Icon(
        modifier = Modifier
            .fillMaxSize(),
        imageVector = icon,
        contentDescription = iconDescription,
        tint = Color.White
    )
}

