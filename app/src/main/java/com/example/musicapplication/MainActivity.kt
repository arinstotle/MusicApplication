package com.example.musicapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicapplication.theme.DarkBackground

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicapplication.presentation.MainScreen
import com.example.musicapplication.presentation.auth.AuthNavigation
import com.example.musicapplication.presentation.auth.AuthScreen

import androidx.navigation.compose.rememberNavController
import com.example.musicapplication.navigation.Navigation
import com.example.musicapplication.navigation.NavigationRouter
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.theme.MusicApplicationTheme
import com.example.musicapplication.ui.bottomNavigation.CustomBottomNavigation


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MusicApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = DarkBackground
//                ) {}
//            }

            val navController = rememberNavController()
            window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
            window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()
            MusicApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = {
                            when (NavigationRouter.currentScreen.value) {
                                // если не нужен боттом бар - -> null
                                else -> {
                                    CustomBottomNavigation(currentScreenRoute = NavigationRouter.currentScreen.value.route) { screen ->
                                        if (screen.route != NavigationRouter.currentScreen.value.route) {
                                            NavigationRouter.currentScreen.value = screen
                                            navController.navigate(screen.route)
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                            contentPadding ->
                        run {
                            Box(modifier = Modifier.padding(contentPadding)) {
                                Navigation(navController, applicationContext)
                            }
                        }
                    }
                }
            }
        }
    }
}

