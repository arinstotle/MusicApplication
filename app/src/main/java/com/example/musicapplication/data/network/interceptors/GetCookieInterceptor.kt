package com.example.musicapplication.data.network.interceptors

import android.util.Log
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class GetCookieInterceptor@Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request());
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            sharedPreferencesHelper.putCookie(originalResponse.headers("Set-Cookie").joinToString(" "))
            //Log.d("COOKIE SH PREF", sharedPreferencesHelper.getCookie())
        }
        return originalResponse
    }
}