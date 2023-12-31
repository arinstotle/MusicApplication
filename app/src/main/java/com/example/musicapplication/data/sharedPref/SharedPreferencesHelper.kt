package com.example.musicapplication.data.sharedPref

import android.content.Context
import android.util.Log
import com.example.musicapplication.model.UserItem
import com.example.musicapplication.utils.Constants.KEY
import com.example.musicapplication.utils.Constants.SHARED_PREF
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun putCookie(cookie:String){
        editor.putString(KEY, cookie)
        editor.apply()
    }

    fun getCookie():String= sharedPreferences.getString(KEY,"")!!

    fun clearCookie() = putCookie("")

    fun putUserId(userId:Int){
        editor.putInt("ID", userId)
        editor.apply()
    }
    fun getUserId():Int = sharedPreferences.getInt("ID", -1)


    fun putUserName(name:String){
        editor.putString("NAME", name)
        editor.apply()
    }
    fun getUserName():String = sharedPreferences.getString("NAME", "user")!!

    fun putUserEmail(email:String){
        editor.putString("EMAIL", email)
        editor.apply()
    }
    fun getUserEmail():String = sharedPreferences.getString("EMAIL", "email")!!

    fun putUserData(id:Int, name:String, email: String){
        putUserId(id)
        putUserName(name)
        putUserEmail(email)
    }
    fun clearUserData(){
        putUserId(-1)
        putUserName("")
        putUserEmail("")
    }
}