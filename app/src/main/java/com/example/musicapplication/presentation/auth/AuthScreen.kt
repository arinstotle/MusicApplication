package com.example.musicapplication.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicapplication.navigation.Screen
import com.example.musicapplication.presentation.theme.DarkBackground
import com.example.musicapplication.presentation.viewModels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    navController:NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var visible by remember { mutableStateOf(false) }
    val connectionState = viewModel.connectionState.collectAsState()
    LaunchedEffect(key1 = viewModel.fragmentState) {
        if(visible){
            visible =! visible
            delay(300)
        }
        delay(300)
        visible=!visible
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Bottom) {
        if(viewModel.authState.value.isCreated && viewModel.authState.value.isAuthorized) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(Screen.MainScreen.route) {
                    inclusive = true
                }
            }
        }
        else if(!viewModel.authState.value.isCreated) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 1000,
                        delayMillis = 500,
                        easing = LinearOutSlowInEasing
                    ),
                    initialOffsetY = {it}
                ) + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + shrinkVertically() + fadeOut()) {
                RegistrationScreenFragment(
                    authState = viewModel.authState.value,
                    connectionState = connectionState.value,
                    goToLogin = { viewModel.onEvent(AuthEvent.GoToLogin) },
                    onRegister = {viewModel.onEvent(AuthEvent.OnRegister(it))}
                )
            }
        }
        else if(!viewModel.authState.value.isAuthorized) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = 100,
                        easing = LinearOutSlowInEasing
                    ),
                    initialOffsetY = {it}
                ),
                exit = slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = 100,
                        easing = LinearOutSlowInEasing
                    ),
                    targetOffsetY = { it }) + shrinkVertically()
            ) {
                LoginScreenFragment(
                    authState = viewModel.authState.value,
                    connectionState = connectionState.value,
                    goToRegister = {viewModel.onEvent(AuthEvent.GoToRegister)},
                    onLogin = {viewModel.onEvent(AuthEvent.OnLogin(it))}
                )
            }
        }
    }
}


