package com.example.musicapplication.data.sharedPref

import android.content.Context
import android.util.Log
import com.example.musicapplication.utils.Constants.KEY
import com.example.musicapplication.utils.Constants.SHARED_PREF
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun putToken(token:String){
        editor.putString(KEY, token)
        editor.apply()
        Log.println(Log.INFO, "TOKEN", token)
    }

    fun getToken():String= sharedPreferences.getString(KEY,"")!!
}