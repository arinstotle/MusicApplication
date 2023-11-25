package com.example.musicapplication.chatioandroid.extensions.di

import com.example.musicapplication.chatioandroid.data.repositories.chat.ChatRepository
import com.example.musicapplication.chatioandroid.data.repositories.chat.ChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModuleSocket {

    @Binds
    abstract  fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}