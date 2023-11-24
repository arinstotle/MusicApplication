package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.LocalRepository
import com.example.musicapplication.domain.RemoteRepository
import javax.inject.Inject

class ClearAllRoomsUseCase @Inject constructor(
    private val repository: LocalRepository
):IClearAllRoomsUseCase {
    override suspend fun invoke() {
        repository.clearDatabase()
    }
}