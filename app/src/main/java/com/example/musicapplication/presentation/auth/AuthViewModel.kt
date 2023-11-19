package com.example.musicapplication.presentation.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.network.repo.RepositoryImpl
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo:RepositoryImpl
):ViewModel() {

    private var _authState= mutableStateOf(AuthState(
        user = UserItem(id = 0, name = "", email = "", password = "", photoUrl = null),
        isAuthorized = false,
        isCreated = true,
        isWrongData = false)
    )
    val authState = _authState
    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.OnLogin -> {
                _authState.value=event.authState.copy()
                login()
            }
            is AuthEvent.OnRegister -> {
                //Log.d("REG_EVENT", event.authState.toString())
                _authState.value=event.authState.copy()
                //Log.d("REG", authState.value.user.toString())
                signup()
            }

            is AuthEvent.GoToLogin -> {
                _authState.value=authState.value.copy(isCreated = true)
            }
            is AuthEvent.GoToRegister -> {
                _authState.value=authState.value.copy(isCreated = false)
            }
        }
    }

    private fun login(){
        viewModelScope.launch(Dispatchers.IO){
            val logedUser = repo.login(authState.value.user)
            if(logedUser!=null){
                _authState.value = authState.value.copy(user = logedUser, isAuthorized = true, isWrongData = false)
            }
            else{
                _authState.value = authState.value.copy(isAuthorized = false, isWrongData = true)
            }
        }
    }
    private fun signup(){
        viewModelScope.launch(Dispatchers.IO){
            val signedUser = repo.signup(authState.value.user)
            if(signedUser!=null) {
                _authState.value = authState.value
                    .copy(user = signedUser, isAuthorized = false, isWrongData = false, isCreated = true)
            }
            else{
                _authState.value = authState.value.copy(isAuthorized = false, isWrongData = true, isCreated = false)
            }
            //Log.d("VM STATE", authState.value.user.toString())
        }
    }

//    private fun me(){
//        viewModelScope.launch(Dispatchers.IO){
//            _authState.value = authState.value.copy(repo.me(), isAuthorized = true)
//        }
//    }
}