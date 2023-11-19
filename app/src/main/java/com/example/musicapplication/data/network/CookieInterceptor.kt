package com.example.musicapplication.data.network

import android.util.Log
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CookieInterceptor @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.headers(Constants.HEADER_COOKIE).isNotEmpty()) {
            sharedPreferencesHelper.putToken(response.headers(Constants.HEADER_COOKIE)[0])
        }
        Log.d("COOKIE", sharedPreferencesHelper.getToken())
        return response
    }
}