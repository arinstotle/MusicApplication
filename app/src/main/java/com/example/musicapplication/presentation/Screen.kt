package com.example.musicapplication.presentation

sealed class Screen(val route: String) {
    object RoomsScreen: Screen("rooms_screen")
    object SearchScreen: Screen("search_screen")
    object ProfileScreen:Screen("profile_screen")
    object LogScreen:Screen("log_screen")

    object RegisterScreen:Screen("reg_screen")
}