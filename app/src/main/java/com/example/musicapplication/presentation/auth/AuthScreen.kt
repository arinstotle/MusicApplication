package com.example.musicapplication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.musicapplication.ui.theme.DarkBackground

@Composable
//@Preview(showBackground = true)
fun AuthScreen(
    a:String="l",
    navController:NavController,
    viewModel: AuthViewModel = AuthViewModel()
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBackground),
        verticalArrangement = Arrangement.Bottom) {
        when(a){
            "l" -> LoginScreenFragment(navController)
            else -> RegistrationScreenFragment()
        }
    }
}


