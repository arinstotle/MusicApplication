package com.example.musicapplication.navigation

import com.example.musicapplication.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object MainScreen : Screen("main_screen",
        title = "Menu", icon = R.drawable.ic_launcher_foreground)


    object BottomScreens {
        val list = listOf(
            MainScreen
        )
    }
}