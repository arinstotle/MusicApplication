package com.example.musicapplication.navigation

import com.example.musicapplication.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object MainScreen : Screen("main_screen",
        title = "Menu", icon = R.drawable.home_icon)
    object StreamScreen : Screen("stream_screen",
        title = "Stream", icon = R.drawable.stream_icon)
    object BottomScreens {
        val list = listOf(
            MainScreen, StreamScreen
        )
    }
}