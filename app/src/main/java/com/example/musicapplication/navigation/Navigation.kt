package com.example.musicapplication.navigation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicapplication.presentation.auth.AuthScreen
import com.example.musicapplication.presentation.auth.authEnterAnimation
import com.example.musicapplication.presentation.auth.authExitAnimation
import com.example.musicapplication.presentation.mainScreen.MainScreen
import com.example.musicapplication.presentation.searchRoomScreen.SearchScreen
import com.example.musicapplication.presentation.profile.ProfileScreen
import com.example.musicapplication.presentation.splashScreen.LogoWithShimmer
import com.example.musicapplication.presentation.streamScreen.StreamMainScreen
import com.example.musicapplication.presentation.viewModels.StreamViewModel

object NavigationRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SplashScreen)
    var prevScreen: MutableState<Screen> = mutableStateOf(Screen.SplashScreen)
}

@Composable
fun Navigation(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value = Screen.MainScreen
        }
        composable(
            route = Screen.AuthScreen.route,
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
            AnimatedVisibility(
                visible = true,
                enter = expandVertically(expandFrom = Alignment.Bottom),
                exit = shrinkVertically()
            ){
                AuthScreen(navController = navController)
            }
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value = Screen.AuthScreen
        }
        composable(route = Screen.StreamScreen.route + "/{roomId}",
            arguments = listOf(
                navArgument("roomId") {
                    type = NavType.IntType
                }
            )
            ) {
            val streamViewModel = hiltViewModel<StreamViewModel>()
            StreamMainScreen(navController = navController, viewModel = streamViewModel)
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value = Screen.StreamScreen
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value = Screen.SearchScreen
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value=Screen.ProfileScreen
        }
        composable(Screen.SplashScreen.route){
            LogoWithShimmer(navController = navController)
            NavigationRouter.prevScreen.value = NavigationRouter.currentScreen.value
            NavigationRouter.currentScreen.value=Screen.SplashScreen
        }
    }
}