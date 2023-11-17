package com.example.musicapplication.navigation

import com.example.musicapplication.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object MainScreen : Screen("main_screen",
        title = "Menu", icon = R.drawable.ic_launcher_foreground)

    object LoginScreen:Screen("login_screen", title = "Login", icon = -1)

    object RegistrationScreen:Screen("registration_screen", title = "Create account", icon = -1)


    object BottomScreens {
        val list = listOf(
            MainScreen
        )
    }
}