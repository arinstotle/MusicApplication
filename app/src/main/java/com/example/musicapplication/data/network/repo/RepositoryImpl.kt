package com.example.musicapplication.data.network.repo

import com.example.musicapplication.data.network.api.NetworkSource
import com.example.musicapplication.data.network.exceptions.WrongDataException
import com.example.musicapplication.data.network.state.DataState
import com.example.musicapplication.domain.repo.Repository
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.presentation.UiState
import com.example.musicapplication.presentation.auth.AuthState
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource
):Repository {
    override suspend fun login(user: UserItem): UserItem? {
        val state = networkSource.login(user)
        when(state){
            is DataState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is DataState.Result -> {
                return state.data
            }

            else -> {}
        }
        return null
    }

    override suspend fun signup(user: UserItem): UserItem? {
        val state = networkSource.signup(user)
        when(state){
            is DataState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is DataState.Result -> {
                return state.data
            }

            else -> {}
        }
        return null
    }

    override suspend fun me(): UserItem? {
        val state = networkSource.me()
        when(state){
            is DataState.Exception -> {
                if(state.cause is WrongDataException){
                    return null
                }
            }
            is DataState.Result -> {
                return state.data
            }

            else -> {}
        }
        return null
    }
}