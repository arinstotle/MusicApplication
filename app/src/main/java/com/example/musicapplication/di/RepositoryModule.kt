package com.example.musicapplication.di

import com.example.musicapplication.data.network.repo.RemoteRepositoryImpl
import com.example.musicapplication.data.room.repo.LocalRepositoryImpl
import com.example.musicapplication.domain.LocalRepository
import com.example.musicapplication.domain.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRemoteRepository(repository: RemoteRepositoryImpl): RemoteRepository
    @Binds
    fun bindLocalRepository(repository: LocalRepositoryImpl): LocalRepository

}