package com.example.musicapplication.di

import com.example.musicapplication.domain.usecases.GetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.IGetAllRoomsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetAllRoomsUseCase(useCase: GetAllRoomsUseCase): IGetAllRoomsUseCase
}