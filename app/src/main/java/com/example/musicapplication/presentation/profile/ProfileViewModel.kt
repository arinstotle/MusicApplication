package com.example.musicapplication.presentation.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.model.emptyUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo:RemoteRepositoryImpl,
    private val sharedPreferencesHelper: SharedPreferencesHelper
):ViewModel() {

    private val _profileState = mutableStateOf(ProfileState(user = emptyUser()))
    val profileState = _profileState

    init{
        me()
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            ProfileEvent.OnLogout -> {
                sharedPreferencesHelper.clearCookie()
            }
        }
    }
    private fun me(){
        viewModelScope.launch(Dispatchers.IO){
            val currentUser = repo.me()
            if(currentUser!=null){
                _profileState.value = profileState.value.copy(user = currentUser)
                Log.d("PROFILE DATA", currentUser.toString())
            }
        }
    }
}