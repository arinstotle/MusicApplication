package com.example.musicapplication

import android.app.Application
import androidx.room.Room
import com.example.musicapplication.data.room.RoomsDatabase
import com.exyte.animatednavbar.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}