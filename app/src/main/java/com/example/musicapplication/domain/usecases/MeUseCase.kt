package com.example.musicapplication.domain.usecases

import android.util.Log
import com.example.musicapplication.domain.RemoteRepository
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.presentation.UiState
import javax.inject.Inject

class MeUseCase @Inject constructor(
    private val remoteRepo:RemoteRepository
):IMeUseCase {
    override suspend fun invoke(): UiState<UserItem> {
        val currentUser = remoteRepo.me()
        if(currentUser!=null){
            return UiState.Success(currentUser)
            Log.d("PROFILE DATA", currentUser.toString())
        }
        else return UiState.Error(null, "cannot get user data")
    }
}