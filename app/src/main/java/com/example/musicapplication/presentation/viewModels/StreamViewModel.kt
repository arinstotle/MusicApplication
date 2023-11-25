package com.example.musicapplication.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.usecases.GetRoomInfoByIdUseCase
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.streamScreen.StreamUserEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StreamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRoomInfoByIdUseCase: GetRoomInfoByIdUseCase
) : ViewModel() {

    private var job: Job? = null

    private var _currentRoom: MutableStateFlow<UiState<RoomItem>> = MutableStateFlow(UiState.Start)
    val currentRoom: StateFlow<UiState<RoomItem>> = _currentRoom.asStateFlow()

    private val _roomId: StateFlow<Int> = savedStateHandle.getStateFlow<Int>("roomId", 0)

    private val _isLoading = MutableStateFlow(false)

    val isLoading = _isLoading.asStateFlow()

    private val _inviteEmail = mutableStateOf("")
    val inviteState = _inviteEmail
    init {
        job = viewModelScope.launch {
            _isLoading.value = true
            getRoomInfoByIdUseCase(_roomId.value).collect { state ->
                when (state) {
                    is DataState.Result -> {
                        _currentRoom.emit(UiState.Success(state.data))
                        _isLoading.value = false
                    }
                    is DataState.Exception -> {
                        _currentRoom.emit(UiState.FatalError(state.cause.message ?: state.cause.stackTraceToString()))
                    }
                    else -> {
                        _currentRoom.emit(UiState.Start)
                    }
                }
            }
        }
    }

    val songLoaded = MutableLiveData<Boolean>()
    fun loadTheSong() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                delay(4000)
            }
            songLoadedSuccessful()
        }
    }
    private fun songLoadedSuccessful() {
        songLoaded.value = true
    }

    fun onEvent(event: StreamUserEvent){
        when(event){
            is StreamUserEvent.OnInvite -> inviteUser(event.email)
        }
    }
    fun inviteUser(email:String){

    }
}