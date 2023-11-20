package com.example.musicapplication.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: RemoteRepositoryImpl
): ViewModel() {

}