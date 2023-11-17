package com.example.musicapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DummyModule {

    @Provides
    @Singleton
    fun provideDummyParameter():String = "Music App!"
}