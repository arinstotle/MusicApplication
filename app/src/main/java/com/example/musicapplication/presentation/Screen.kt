package com.example.musicapplication.presentation

sealed class Screen(val route: String) {
    object RoomsScreen: Screen("rooms_screen")
    object SearchScreen: Screen("search_screen")
    object ProfileScreen:Screen("profile_screen")
}