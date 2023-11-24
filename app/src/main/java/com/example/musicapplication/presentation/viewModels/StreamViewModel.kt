package com.example.musicapplication.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StreamViewModel @Inject constructor(

) : ViewModel() {
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
}