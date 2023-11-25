package com.example.musicapplication.di

import com.example.musicapplication.domain.usecases.AddNewRoomUseCase
import com.example.musicapplication.domain.usecases.EnterToTheRoomUseCase
import com.example.musicapplication.domain.usecases.GetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.GetRoomInfoByIdUseCase
import com.example.musicapplication.domain.usecases.IAddNewRoomUseCase
import com.example.musicapplication.domain.usecases.IEnterToTheRoomUseCase
import com.example.musicapplication.domain.usecases.IGetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.IGetRoomInfoByIdUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetAllRoomsUseCase(useCase: GetAllRoomsUseCase): IGetAllRoomsUseCase

    @Binds
    fun bindAddNewRoomUseCase(useCase: AddNewRoomUseCase): IAddNewRoomUseCase

    @Binds
    fun bindEnterToTheRoomUseCase(useCase: EnterToTheRoomUseCase): IEnterToTheRoomUseCase

    @Binds
    fun bindGetRoomByIdUseCase(useCase: GetRoomInfoByIdUseCase): IGetRoomInfoByIdUseCase
}