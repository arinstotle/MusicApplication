package com.example.musicapplication.di

import android.content.Context
import androidx.room.Room
import com.example.musicapplication.data.network.api.ApiService
import com.example.musicapplication.data.network.repo.NetworkSource
import com.example.musicapplication.data.room.RoomsDao
import com.example.musicapplication.data.room.RoomsDatabase
import com.example.musicapplication.data.room.repo.DatabaseSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomsDatabase = Room
            .databaseBuilder(
                context,
                RoomsDatabase::class.java,
                "rooms_database"
            ).build()

    @Singleton
    @Provides
    fun provideRoomsDao(db: RoomsDatabase): RoomsDao = db.roomDao()

    @Provides
    @Singleton
    fun provideDatabaseSource(
        roomsDao: RoomsDao
    ): DatabaseSource = DatabaseSource(roomsDao)
}