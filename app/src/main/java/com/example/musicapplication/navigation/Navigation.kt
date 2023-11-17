package com.example.musicapplication.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapplication.presentation.auth.AuthScreen
import com.example.musicapplication.presentation.auth.authEnterAnimation
import com.example.musicapplication.presentation.auth.authExitAnimation
import com.example.musicapplication.ui.mainScreen.MainScreen

object NavigationRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)
}

@Composable
fun Navigation(navController: NavHostController, context: Context) {


    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
            NavigationRouter.currentScreen.value = Screen.MainScreen
        }
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = {
                authEnterAnimation()
            },
            exitTransition = {
                authExitAnimation()
            },
            popEnterTransition = {
                authEnterAnimation()
            },
            popExitTransition = {
                authExitAnimation()
            }
        ){
            AuthScreen("l", navController = navController)
            NavigationRouter.currentScreen.value = Screen.LoginScreen
        }
        composable(
            route = Screen.RegistrationScreen.route,
            enterTransition = {
                authEnterAnimation()
            },
            exitTransition = {
                authExitAnimation()
            },
            popEnterTransition = {
                authEnterAnimation()
            },
            popExitTransition = {
                authExitAnimation()
            }
        ){
            AuthScreen("r", navController = navController)
            NavigationRouter.currentScreen.value = Screen.RegistrationScreen
        }
    }
}