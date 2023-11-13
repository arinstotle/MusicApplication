package com.example.musicapplication.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapplication.ui.mainScreen.MainScreen
import com.example.musicapplication.ui.streamScreen.StreamMainScreen

object NavigationRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.MainScreen)
}

@Composable
fun Navigation(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.StreamScreen.route) {
            StreamMainScreen(navController = navController)
        }
    }
}