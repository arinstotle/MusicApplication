package com.example.musicapplication

import android.app.Application
import androidx.room.Room
import com.example.musicapplication.data.room.RoomsDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

}