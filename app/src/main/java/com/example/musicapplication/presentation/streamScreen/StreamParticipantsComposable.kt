package com.example.musicapplication.presentation.streamScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapplication.R

@Composable
fun StreamParticipantsComposable(participantsList: List<ParticipantUI>,
                                 addParticipantClick: () -> Unit,
                                 removeParticipantClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            IconButton(
                onClick = addParticipantClick,
                modifier = Modifier
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.tertiary, CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add participant",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
            Text(
                text = "Add Participant",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.tertiary,
                    ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        ParticipantsList(participantsList = participantsList, removeParticipantClick)
    }
}

@Composable
fun ParticipantsList(participantsList: List<ParticipantUI>, removeParticipantClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 4.dp
            )
    ) {
        items(participantsList.size) { song ->
            ParticipantCard(participant = participantsList[song], removeParticipantClick)
        }
    }
}

@Composable
fun ParticipantCard(participant: ParticipantUI, removeParticipantClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = participant.avatarURL,
                placeholder = painterResource(id = R.drawable.sample_avatar),
                error = painterResource(id = R.drawable.sample_avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${participant.name} ${participant.lastname}",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                        color = MaterialTheme.colorScheme.onTertiary
                        ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (participant.isCreator) {
                    Text(
                        text = "Creator",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily(Font(R.font.mts_wide_medium)),
                            color = MaterialTheme.colorScheme.tertiary),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = removeParticipantClick,
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}