package com.example.musicapplication.presentation.profile

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapplication.R
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.navigation.NavigationRouter
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.theme.TextWhite
import com.example.musicapplication.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout:()-> Unit = { viewModel.onEvent(ProfileEvent.OnLogout) }
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .padding(start = 26.dp),
                        text = NavigationRouter.currentScreen.value.title,
                        style = TextStyle(
                            fontSize = 21.sp,
                            fontFamily = FontFamily(Font(R.font.spartan_bold)),
                            color = TextWhite
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onLogout()
                        navController.navigate(Screen.AuthScreen.route){
                            popUpTo(Screen.AuthScreen.route){
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { values ->
        if(viewModel.profileState.value is UiState.Success<*>){
            ProfileScreenFragment(values, user = viewModel.profileState.value.data!!)
        }
        else{
            Box(modifier = Modifier.fillMaxSize()
                .background(DarkBackground)) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}
@Composable
fun ProfileScreenFragment(
    paddingValues: PaddingValues,
    user: UserItem
) {
    Column(modifier = Modifier
        .background(DarkBackground, RectangleShape)
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding()+150.dp,
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
            bottom = paddingValues.calculateBottomPadding())){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photoUrl?: R.drawable.sample_avatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.sample_avatar),
            contentDescription = "profile_photo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Text(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 100.dp),
            text = user.name,
            color = TextWhite,
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.spartan_extrabold, FontWeight.Normal))
            )
        )
        Text(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp),
            text = user.email,
            color = TextWhite,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.spartan_extrabold, FontWeight.Normal))
            )
        )
    }
}