package com.example.musicapplication.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel():ViewModel() {

    private var _authState= mutableStateOf(AuthState(name = "", email = "", password = ""))
    val authState = _authState
    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.OnLogin -> {
                _authState.value=event.authState.copy()
                login()
            }
            is AuthEvent.OnRegister -> {
                _authState.value=event.authState.copy()
                register()
            }
        }
    }

    private fun login(){}
    private fun register(){}
}