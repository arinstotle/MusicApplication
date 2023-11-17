package com.example.musicapplication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musicapplication.theme.DarkBackground

@Composable
//@Preview(showBackground = true)
fun AuthScreen(
    //временный "индикатор" назначения экрана
    a:String="l",
    navController:NavController,
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBackground),
        verticalArrangement = Arrangement.Bottom) {
        when(a){
            "l" -> LoginScreenFragment(navController = navController, authState = viewModel.authState.value) {
                viewModel.onEvent(
                    AuthEvent.OnLogin(
                        viewModel.authState.value
                    )
                )
            }
            else -> RegistrationScreenFragment(navController = navController, authState = viewModel.authState.value){
                viewModel.onEvent(
                    AuthEvent.OnRegister(
                        viewModel.authState.value
                    )
                )
            }
        }
    }
}


