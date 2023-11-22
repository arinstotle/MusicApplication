package com.example.musicapplication.navigation

import com.example.musicapplication.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object MainScreen : Screen("main_screen",
        title = "Menu", icon = R.drawable.ic_launcher_foreground)

    object AuthScreen:Screen("login_screen", title = "Authorization", icon = -1)
    object StreamScreen : Screen("stream_screen",
        title = "Stream", icon = R.drawable.stream_icon)

    object ProfileScreen:Screen(route = "profile_screen", title = "Profile", icon = R.drawable.ic_default_profile_photo)
    object BottomScreens {
        val list = listOf(
            MainScreen, StreamScreen
        )
    }
}