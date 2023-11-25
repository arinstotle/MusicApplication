package com.example.musicapplication.presentation.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.domain.DataState
import com.example.musicapplication.domain.usecases.EnterToTheRoomUseCase
import com.example.musicapplication.domain.usecases.IAddNewRoomUseCase
import com.example.musicapplication.domain.usecases.IGetAllRoomsUseCase
import com.example.musicapplication.model.OrdersTypes
import com.example.musicapplication.model.RoomItem
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.utils.ConnectivityObserver
import com.example.musicapplication.utils.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getAllRoomsUseCase: IGetAllRoomsUseCase,
    private val addNewRoomUseCase: IAddNewRoomUseCase,
    private val enterToTheRoomUseCase: EnterToTheRoomUseCase,
    private val connectivityObserver: NetworkConnectivityObserver,
    ):ViewModel() {

    private var job: Job? = null

    private val _connectionState = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val connectionState = _connectionState.asStateFlow()

    private var _currentRoom: MutableStateFlow<RoomItem?> = MutableStateFlow(null)
    val currentRoom: StateFlow<RoomItem?> = _currentRoom.asStateFlow()

    private var _roomName: MutableStateFlow<String> = MutableStateFlow("")
    val roomName: StateFlow<String> = _roomName.asStateFlow()

    private var _password: MutableStateFlow<String?> = MutableStateFlow(null)
    val password: StateFlow<String?> = _password.asStateFlow()

    private var _enteringPassword: MutableStateFlow<String?> = MutableStateFlow(null)
    val enteringPassword: StateFlow<String?> = _enteringPassword.asStateFlow()

    private var _isPrivate: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPrivate: StateFlow<Boolean> = _isPrivate.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    var isDialogShown by mutableStateOf(false)
        private set
    var isEnterDialogShown by mutableStateOf(false)
        private set

    fun setEnteringRoom(enteringRoom: RoomItem) {
        _currentRoom.value = enteringRoom
    }
    fun unsetEnteringRoom() {
        _currentRoom.value = null
    }
    fun onAddNewRoomClick() {
        isDialogShown = true
    }
    fun onEnterToTheRoomClick() {
        isEnterDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun onDismissEnterDialog() {
        isEnterDialogShown = false
    }
    fun renameRoom(newName: String) {
        _roomName.value = newName
    }

    fun changeRoomType(isPrivate: Boolean) {
        _isPrivate.value = isPrivate
    }

    fun changeRoomPassword(password: String) {
        _password.value = password
    }

    fun changeEnteringRoomPassword(password: String) {
        _enteringPassword.value = password
    }

    fun unsetEnteringRoomPassword() {
        _enteringPassword.value = null
    }

    fun unsetRoomPassword() {
        _password.value = null
    }
    fun unsetRoomName() {
        _roomName.value = ""
    }
    fun unsetRoomType() {
        _isPrivate.value = false
    }

    private fun observeNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest {
                _connectionState.emit(it)
            }
        }
    }

    private var _allRooms: MutableStateFlow<UiState<List<RoomItem>>> = MutableStateFlow(UiState.Start)

    @OptIn(FlowPreview::class)
    val allRooms = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_allRooms) { text, allRooms ->
            if(text.isBlank()) {
                allRooms
            } else {
                delay(2000L)
                allRooms.data?.filter {
                    it.roomName == text
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _allRooms.value
        )

    private val _sortType = MutableStateFlow(OrdersTypes.NATURAL)

    init {
        observeNetwork()
        job = viewModelScope.launch {
            _isLoading.value = true
            getAllRoomsUseCase(OrdersTypes.NATURAL).collect { state ->
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

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun changeSortType(ordersTypes: OrdersTypes) {
        _sortType.value = ordersTypes
    }

    fun updateRooms(): Flow<UiState<Any>> = flow {
        getAllRoomsUseCase(_sortType.value).collect { state ->
            when (state) {
                is DataState.Result -> _allRooms.value = UiState.Success(state.data)
                is DataState.Exception -> emit(UiState.FatalError(state.cause.message ?: state.cause.stackTraceToString()))
                else -> emit(UiState.Start)
            }
        }
    }

    fun addRoom(): Flow<UiState<Any>> = flow {
        val roomName: String = roomName.value
        val password: String? = password.value
        val isPrivate: Boolean = isPrivate.value
        val owner: Int = 1
        addNewRoomUseCase(roomName, password, isPrivate, owner).collect { state ->
            when (state) {
                is DataState.Initial -> emit(UiState.Start)
                is DataState.Result -> emit(UiState.Success(state.data))
                is DataState.Exception -> emit(UiState.FatalError(state.cause.message ?: state.cause.stackTraceToString()))
                else -> {
                }
            }
        }
    }.catch {
        emit(UiState.FatalError(it.cause?.message.toString()))
    }

    fun enterToTheRoom(room: RoomItem): Flow<UiState<Any>> = flow {
        if (room.isPrivate) {
            enterToTheRoomUseCase(room.id!!, room.password!!).collect { state ->
                when (state) {
                    is DataState.Initial -> emit(UiState.Start)
                    is DataState.Result -> emit(UiState.Success(state.data))
                    is DataState.Exception -> emit(
                        UiState.FatalError(
                            state.cause.message ?: state.cause.stackTraceToString()
                        )
                    )
                    else -> {
                    }
                }
            }
        } else {
            enterToTheRoomUseCase(room.id!!).collect { state ->
                when (state) {
                    is DataState.Initial -> emit(UiState.Start)
                    is DataState.Result -> emit(UiState.Success(state.data))
                    is DataState.Exception -> emit(
                        UiState.FatalError(
                            state.cause.message ?: state.cause.stackTraceToString()
                        )
                    )
                    else -> {
                    }
                }
            }
        }
    }.catch {
        emit(UiState.FatalError(it.cause?.message.toString()))
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}