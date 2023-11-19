package com.example.musicapplication.domain.repo

import com.example.musicapplication.model.UserItem

interface Repository {
    suspend fun login(user:UserItem): UserItem?

    suspend fun signup(user: UserItem): UserItem?

    suspend fun me(): UserItem?
}