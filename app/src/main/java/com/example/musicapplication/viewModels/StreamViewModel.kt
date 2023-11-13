package com.example.musicapplication.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StreamViewModel : ViewModel() {
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