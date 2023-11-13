package com.example.musicapplication.ui.streamScreen

import androidx.activity.*
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.*
import coil.request.ImageRequest
import com.example.musicapplication.R
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun SongComposable(
    backPressedClicker: () -> Unit,
    song: SongUI?
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }
        )) {
        if (song != null) {
            SongScreenBody(
                song = song,
                backPressedClicker = backPressedClicker
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SongScreenBody(
    song: SongUI,
    backPressedClicker: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val endAnchor = LocalConfiguration.current.screenHeightDp * LocalDensity.current.density
    val anchors = mapOf(
        0f to 0,
        endAnchor to 1
    )
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressedClicker.invoke()
            }
        }
    }
    val backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
    var dominantColor by remember {
        mutableStateOf(backgroundColor)
    }
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(song.imageUrl)
        .build()

    val iconResId =
        if (true) R.drawable.chat_icon else R.drawable.ic_launcher_foreground
    val isSongPlaying = true
    var sliderIsChanging by remember { mutableStateOf(false) }
    var localSliderValue by remember { mutableFloatStateOf(0f) }
    var localSongValue by remember {
        mutableFloatStateOf(0f)
    }
    val sliderProgress =
        if (sliderIsChanging) localSliderValue else localSliderValue
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.34f) },
                orientation = Orientation.Vertical
            )
    ) {
        if (swipeableState.currentValue >= 1) {
            LaunchedEffect("key") {
            }
        }
        SongScreenContent(
            song = song,
            isSongPlaying = isSongPlaying,
            imagePainter = painterResource(id = R.drawable.sample_album),
            dominantColor = dominantColor,
            playbackProgress = sliderProgress,
            currentTime = "",
            totalTime = "",
            playPauseIcon = iconResId,
            yOffset = swipeableState.offset.value.roundToInt(),
            playOrToggleSong = {  },
            playNextSong = {  },
            playPreviousSong = {  },
            onSliderChange = { newPosition ->
                localSliderValue = newPosition
                sliderIsChanging = true
            },
            onSliderChangeFinished = {
            },
            onForward = {
            },
            onRewind = {
            },
            onClose = {
            }
        )
    }

    LaunchedEffect("playbackPosition") {
    }

    DisposableEffect(backPressedClicker) {
        onDispose {
            backCallback.remove()
        }
    }
}

@Composable
fun SongScreenContent(
    song: SongUI,
    isSongPlaying: Boolean,
    imagePainter: Painter,
    dominantColor: Color,
    playbackProgress: Float,
    currentTime: String,
    totalTime: String,
    @DrawableRes playPauseIcon: Int,
    yOffset: Int,
    playOrToggleSong: () -> Unit,
    playNextSong: () -> Unit,
    playPreviousSong: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onSliderChangeFinished: () -> Unit,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onClose: () -> Unit

) {
    val gradientColors = if (isSystemInDarkTheme()) {
        listOf(
            dominantColor,
            androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
        )
    } else {
        listOf(
            androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
            androidx.compose.material3.MaterialTheme.colorScheme.secondary
        )
    }
    val sliderColors = if (isSystemInDarkTheme()) {
        SliderDefaults.colors(
            thumbColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
            activeTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
            inactiveTrackColor = androidx.compose.material3
                .MaterialTheme.colorScheme.onSecondary.copy(
                alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity
            ),
        )
    } else SliderDefaults.colors(
        thumbColor = dominantColor,
        activeTrackColor = dominantColor,
        inactiveTrackColor = dominantColor.copy(
            alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = gradientColors,
                            endY = LocalConfiguration.current.screenHeightDp.toFloat() * LocalDensity.current.density
                        )
                    )
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Column {
                    IconButton(
                        onClick = onClose
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp).size(50.dp),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Close",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .weight(1f, fill = false)
                                .aspectRatio(1f)

                        ) {
                            VinylAnimation(painter = imagePainter, isSongPlaying = isSongPlaying)
                        }
                        Text(
                            text = song.title,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                fontWeight = FontWeight(700),
                                ),
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            song.artist,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                                fontWeight = FontWeight(600)
                                ),
                            color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.graphicsLayer {
                                alpha = 0.60f
                            }
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Slider(
                                value = playbackProgress,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = sliderColors,
                                onValueChange = onSliderChange,
                                onValueChangeFinished = onSliderChangeFinished
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                    Text(
                                        "00:00",
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                            fontWeight = FontWeight(700),
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary
                                        )
                                    )
                                }
                                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                    Text(
                                        "05:00",
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily(Font(R.font.spartan_bold)),
                                            fontWeight = FontWeight(700),
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary
                                        )
                                    )
                                }
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.prev_icon),
                                contentDescription = "Skip Previous",
                                tint = androidx.compose.material3
                                    .MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = playPreviousSong)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.forward_icon),
                                contentDescription = "Replay 10 seconds",
                                tint = androidx.compose.material3
                                    .MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onRewind)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = androidx.compose.material3
                                    .MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(androidx.compose.material3.MaterialTheme.colorScheme.secondary)
                                    .clickable(onClick = playOrToggleSong)
                                    .size(64.dp)
                                    .padding(8.dp)
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.replay_icon),
                                tint = androidx.compose.material3
                                    .MaterialTheme.colorScheme.onTertiary,
                                contentDescription = "Forward 10 seconds",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onForward)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.next_icon),
                                tint = androidx.compose.material3
                                    .MaterialTheme.colorScheme.surface,
                                contentDescription = "Skip Next",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = playNextSong)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Vinyl(
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,
    painter: Painter
) {
    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .clip(CircleShape)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationDegrees)
                .blur(30.dp)
            ,
            painter = painterResource(id = R.drawable.sample_album),
            contentDescription = "Vinyl Background",
            colorFilter = ColorFilter.tint(color = Color(0x31000000))
        )
        Image(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .rotate(rotationDegrees)
                .aspectRatio(1.0f)
                .align(Alignment.Center)
                .clip(CircleShape),
            painter = painter,
            contentDescription = "Song cover"
        )
    }
}

@Composable
fun VinylAnimation(
    modifier: Modifier = Modifier,
    isSongPlaying: Boolean = true,
    painter: Painter
) {
    var currentRotation by remember {
        mutableFloatStateOf(0f)
    }

    val rotation = remember {
        androidx.compose.animation.core.Animatable(currentRotation)
    }

    LaunchedEffect(isSongPlaying) {
        if (isSongPlaying) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }
    Vinyl(painter = painter, rotationDegrees = rotation.value)
}