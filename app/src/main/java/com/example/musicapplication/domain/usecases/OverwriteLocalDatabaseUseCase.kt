package com.example.musicapplication.domain.usecases

import com.example.musicapplication.domain.LocalRepository
import com.example.musicapplication.model.RoomItem
import javax.inject.Inject

class OverwriteLocalDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository
):IOverwriteLocalDatabaseUseCase {
    override suspend fun invoke(userId:Int) {
        localRepository.overwriteLocalDatabase(userId)
    }
}