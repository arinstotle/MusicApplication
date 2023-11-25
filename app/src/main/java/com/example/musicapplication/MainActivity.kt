package com.example.musicapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.musicapplication.navigation.Navigation
import com.example.musicapplication.navigation.NavigationRouter
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.theme.MusicApplicationTheme
import com.example.musicapplication.presentation.bottomNavigation.CustomBottomNavigation
import com.example.musicapplication.presentation.theme.DarkRed
import com.example.musicapplication.presentation.theme.MtsBackgroundGreyDark
import com.example.musicapplication.presentation.theme.MtsRed
import dagger.hilt.android.AndroidEntryPoint


@Suppress("UNUSED_EXPRESSION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            window.statusBarColor = MtsBackgroundGreyDark.toArgb()
            window.navigationBarColor = MtsBackgroundGreyDark.toArgb()
            MusicApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(DarkBackground),
                    color = DarkBackground) {
                    Scaffold(
                        modifier = Modifier.background(DarkBackground),
                        bottomBar = {
                            when (NavigationRouter.currentScreen.value) {
                                Screen.AuthScreen -> null
                                Screen.MainScreen, Screen.SearchScreen,
                                Screen.ProfileScreen -> {
                                    CustomBottomNavigation(currentScreenRoute = NavigationRouter.currentScreen.value.route) { screen ->
                                        if (screen.route != NavigationRouter.currentScreen.value.route) {
                                            NavigationRouter.currentScreen.value = screen
                                            navController.navigate(screen.route)
                                        }
                                    }
                                }
                                else -> {}
                            }
                        }
                    ) {
                            contentPadding ->
                        run {
                            Box(modifier = Modifier.padding(contentPadding).background(
                                DarkBackground)) {
                                Navigation(navController, applicationContext)
                            }
                        }
                    }
                }
            }
        }
    }
}

