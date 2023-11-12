package com.example.musicapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapplication.R
import com.example.musicapplication.model.ProfileItem
import com.example.musicapplication.ui.theme.DarkBackground
import com.example.musicapplication.ui.theme.TextWhite
import com.example.musicapplication.utils.Constants

@Composable
@Preview(showBackground = true)
fun ProfileScreenFragment(profileItem:ProfileItem = Constants.mockedProfile) {
    Column(modifier = Modifier
        .background(DarkBackground, RectangleShape)
        .fillMaxSize()
        .padding(top = 250.dp, bottom = 250.dp),
        verticalArrangement = Arrangement.SpaceBetween){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileItem.photoUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_default_profile_photo),
            contentDescription = "profile_photo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Text(modifier = Modifier.align(Alignment.CenterHorizontally),
            text = profileItem.nickname,
            color = TextWhite,
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.spartan_extrabold, FontWeight.Normal))
            )
        )
    }
}