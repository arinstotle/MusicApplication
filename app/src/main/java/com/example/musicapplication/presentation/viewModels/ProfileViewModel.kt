package com.example.musicapplication.presentation.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.domain.usecases.ClearAllRoomsUseCase
import com.example.musicapplication.domain.usecases.MeUseCase
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.profile.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val meUseCase: MeUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val clearAllRoomsUseCase: ClearAllRoomsUseCase
):ViewModel() {

    private val _profileState:MutableState<UiState<UserItem>> = mutableStateOf(UiState.Start)
    val profileState = _profileState

    init{
        me()
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            ProfileEvent.OnLogout -> {
                logout()
            }
        }
    }

    private fun me(){
        viewModelScope.launch(Dispatchers.IO){
            _profileState.value = meUseCase.invoke()
        }
    }

    private fun logout(){
        sharedPreferencesHelper.clearCookie()
        sharedPreferencesHelper.clearUserData()
        viewModelScope.launch(Dispatchers.IO) {
            clearAllRoomsUseCase.invoke()
        }
    }
}