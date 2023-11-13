package com.example.musicapplication.ui.streamScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicapplication.R

@Composable
fun StreamMusicQueueComposable(songsList: List<SongUI>,
                               songOnClick: (SongUI) -> Unit) {
    var selectedSong by remember { mutableStateOf<SongUI?>(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        SongsList(songsList = songsList, onSongSelected = songOnClick)
    }
}


@Composable
fun SongsList(songsList: List<SongUI>, onSongSelected: (SongUI) -> Unit) {
    var isSongSelected by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = if (isSongSelected) 48.dp else 4.dp
            )
    ) {
        items(songsList.size) { song ->
            SongCard(song = songsList[song], onClick = onSongSelected)
        }
    }
}

@Composable
fun SongCard(song: SongUI, onClick: (SongUI) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(song) },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = song.imageUrl,
                placeholder = painterResource(id = R.drawable.sample_album),
                error = painterResource(id = R.drawable.sample_album),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                        color = MaterialTheme.colorScheme.onTertiary
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.spartan_bold)),
                        color = MaterialTheme.colorScheme.tertiary),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = song.duration,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.spartan_extrabold)),
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {

                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun MediaPlayerCard(modifier: Modifier = Modifier, song: SongUI) {
    var songState by remember { mutableStateOf(false) }
    Card(modifier = modifier, elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = song.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = if (songState) {
                    Icons.Filled.Star
                } else {
                    Icons.Filled.PlayArrow
                },
                contentDescription = "Play/Pause",
                modifier = Modifier.clickable {
                    songState = !songState
                })
        }
    }


}

