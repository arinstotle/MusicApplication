package com.example.musicapplication.di

import com.example.musicapplication.domain.usecases.AddNewRoomUseCase
import com.example.musicapplication.domain.usecases.ClearAllRoomsUseCase
import com.example.musicapplication.domain.usecases.EnterToTheRoomUseCase
import com.example.musicapplication.domain.usecases.GetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.IAddNewRoomUseCase
import com.example.musicapplication.domain.usecases.IClearAllRoomsUseCase
import com.example.musicapplication.domain.usecases.IEnterToTheRoomUseCase
import com.example.musicapplication.domain.usecases.IGetAllRoomsUseCase
import com.example.musicapplication.domain.usecases.IMeUseCase
import com.example.musicapplication.domain.usecases.IOverwriteLocalDatabaseUseCase
import com.example.musicapplication.domain.usecases.MeUseCase
import com.example.musicapplication.domain.usecases.OverwriteLocalDatabaseUseCase
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
    fun bindClearAllRoomsUseCase(useCase:ClearAllRoomsUseCase): IClearAllRoomsUseCase

    @Binds
    fun bindOverwriteLocalDatabaseUseCase(useCase:OverwriteLocalDatabaseUseCase): IOverwriteLocalDatabaseUseCase

    @Binds
    fun bindMeUseCase(useCase: MeUseCase): IMeUseCase
}