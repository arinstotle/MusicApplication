package com.example.musicapplication.navigation

import com.example.musicapplication.R

sealed class Screen(val route : String, val icon : Int, val title : String) {
    object ProfileScreen:Screen(route = "profile_screen", title = "Profile", icon = R.drawable.ic_default_profile_photo)
    object MainScreen : Screen("main_screen", title = "Menu", icon = R.drawable.ic_launcher_foreground)
    object AuthScreen: Screen("login_screen", title = "Login", icon = -1)
    object StreamScreen: Screen("stream_screen",
        title = "Stream", icon = -1)
    object SearchScreen: Screen("search_screen",
        title = "Search", icon = R.drawable.stream_icon)
    object BottomScreens {
        val list = listOf(
            MainScreen, SearchScreen, ProfileScreen
        )
    }
    object SplashScreen: Screen("splash_screen", title = "Splash", icon = -1)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}