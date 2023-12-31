package com.example.musicapplication.presentation.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.domain.usecases.OverwriteLocalDatabaseUseCase
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.model.emptyUser
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.auth.AuthEvent
import com.example.musicapplication.presentation.auth.AuthState
import com.example.musicapplication.utils.ConnectivityObserver
import com.example.musicapplication.utils.NetworkConnectivityObserver

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

enum class Fragment{
    LOGIN,
    REGISTER
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val remoteRepo:RemoteRepositoryImpl,
    private val overwriteLocalDatabaseUseCase: OverwriteLocalDatabaseUseCase,
    private val connectivityObserver: NetworkConnectivityObserver,
    private val sharedPreferencesHelper: SharedPreferencesHelper
):ViewModel() {

    private val _connectionState = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val connectionState = _connectionState.asStateFlow()

    private var _authState= mutableStateOf(
        AuthState(
        user = emptyUser(),
        isAuthorized = false,
        isCreated = true,
        isWrongData = false)
    )
    val authState = _authState

    private var _fragmentState = mutableStateOf(Fragment.LOGIN)
    val fragmentState = _fragmentState

    private var _allRooms: MutableStateFlow<UiState<List<RoomItem>>> = MutableStateFlow(UiState.Start)
    val allRooms = _allRooms.asStateFlow()

    init {
        observeNetwork()
        _fragmentState.value = Fragment.LOGIN
        me()
    }


    private fun observeNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest {
                _connectionState.emit(it)
            }
        }
    }
    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.OnLogin -> {
                _authState.value=event.authState.copy()
                login()

            }
            is AuthEvent.OnRegister -> {
                _authState.value = event.authState.copy()
                signup()
            }

            is AuthEvent.GoToLogin -> {
                _fragmentState.value= Fragment.LOGIN
                _authState.value=authState.value.copy(isCreated = true)
            }
            is AuthEvent.GoToRegister -> {
                _fragmentState.value= Fragment.REGISTER
                _authState.value=authState.value.copy(isCreated = false, isAuthorized = false)
            }
            is AuthEvent.Logout ->{
                _authState.value=authState.value.copy(isAuthorized = false)
            }
        }
    }

    private fun login(){
        viewModelScope.launch(Dispatchers.IO){
            val logedUser = remoteRepo.login(authState.value.user)
            if(logedUser!=null){
                _authState.value = authState.value.copy(user = logedUser, isAuthorized = true, isWrongData = false)
                sharedPreferencesHelper.putUserData(
                    authState.value.user.id,
                    authState.value.user.name,
                    authState.value.user.email
                )
                overwriteLocalDatabaseUseCase.invoke(sharedPreferencesHelper.getUserId())
            }
            else{
                _authState.value = authState.value.copy(user = emptyUser(),  isAuthorized = false, isWrongData = true)
            }
        }

        //saveRooms()
    }
    private fun signup(){
        viewModelScope.launch(Dispatchers.IO){
            val signedUser = remoteRepo.signup(authState.value.user)
            if(signedUser!=null) {
                _authState.value = authState.value
                    .copy(user = signedUser, isAuthorized = false, isWrongData = false, isCreated = true)
            }
            else{
                _authState.value = authState.value.copy(user = emptyUser(), isAuthorized = false, isWrongData = true, isCreated = false)
            }
        }
    }

    private fun me(){
        viewModelScope.launch(Dispatchers.IO){
            val currentUser = remoteRepo.me()
            if(currentUser!=null){
                _authState.value = authState.value.copy(user = currentUser.copy(password = ""), isAuthorized = true, isCreated = true)
                sharedPreferencesHelper.putUserData(
                    authState.value.user.id,
                    authState.value.user.name,
                    authState.value.user.email)
            }
        }
    }

//    private fun saveRooms(){
//        Log.d("DOWNLOADED ROOMS", _allRooms.value.data.toString())
//        viewModelScope.launch(Dispatchers.IO) {
//            when(_allRooms.value){
//                is UiState.Success -> {
//                    _allRooms.value.data?.let { saveAllRoomsUseCase.invoke(_allRooms.value.data!!) }
//                }
//                else -> { Log.d("DOWNLOAD ROOMS", "UNSUCCESS") }
//            }
//
//        }
//    }
}