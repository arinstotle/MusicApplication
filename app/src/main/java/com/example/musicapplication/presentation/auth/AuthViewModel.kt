package com.example.musicapplication.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.musicapplication.presentation.Screen

class AuthViewModel():ViewModel() {



    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.OnLogin -> login(event.authState!!)
            is AuthEvent.OnRegister -> register(event.authState)
        }
    }

    private fun login(authState: AuthState){}
    private fun register(authState: AuthState){
    }
}