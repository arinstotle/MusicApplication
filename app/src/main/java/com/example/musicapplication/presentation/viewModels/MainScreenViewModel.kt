package com.example.musicapplication.presentation.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.usecases.GetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.GetUsersRoomsUseCase
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.utils.ConnectivityObserver
import com.example.musicapplication.utils.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val getUsersRoomsUseCase: GetUsersRoomsUseCase,
    private val connectivityObserver: NetworkConnectivityObserver,
    ): ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val connectionState = _connectionState.asStateFlow()

    private var _allRooms: MutableStateFlow<UiState<List<RoomItem>>> = MutableStateFlow(UiState.Start)
    val allRooms = _allRooms

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _userState: MutableState<UiState<UserItem>> = mutableStateOf(UiState.Start)
    val userState = _userState

    init{
        observeNetwork()
        loadRooms()
        loadUserData()
    }

    private fun observeNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest {
                _connectionState.emit(it)
            }
        }
    }

    //да да, пока так
    fun loadUserData(){
        _userState.value = UiState.Success(
            UserItem(
                id = sharedPreferencesHelper.getUserId(),
                name = sharedPreferencesHelper.getUserName(),
                email = sharedPreferencesHelper.getUserEmail(),
                password = "",
                photoUrl = null
        )
        )
    }

    fun getUserData() = UserItem(
        id = sharedPreferencesHelper.getUserId(),
        name = sharedPreferencesHelper.getUserName(),
        email = sharedPreferencesHelper.getUserEmail(),
        password = "",
        photoUrl = null)


    fun loadRooms(){
        viewModelScope.launch {
            _isLoading.value = true
            getUsersRoomsUseCase(sharedPreferencesHelper.getUserId()).collect { state ->
                when (state) {
                    is DataState.Result -> {
                        _allRooms.emit(UiState.Success(state.data))
                        _isLoading.value = false
                    }
                    is DataState.Exception -> {
                        _allRooms.emit(UiState.FatalError(state.cause.message ?: state.cause.stackTraceToString()))
                    }
                    else -> {
                        _allRooms.emit(UiState.Start)
                    }
                }
            }
        }
    }


}