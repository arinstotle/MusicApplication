package com.example.musicapplication.data.network

import android.util.Log
import com.example.musicapplication.data.sharedPref.SharedPreferencesHelper
import com.example.musicapplication.utils.Constants
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.net.HttpCookie
import javax.inject.Inject


class SetCookieJar @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
): CookieJar {
    private var cookieStore: HashMap<HttpUrl, List<Cookie>> = HashMap()
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val listCookies = mutableListOf<Cookie>()

        if(sharedPreferencesHelper.getCookie()!=""){
            val sharedCookiesString = sharedPreferencesHelper.getCookie().split("; ").toList()
            val sharedCookiesPairs = mutableMapOf<String, String>()
            with(sharedCookiesPairs){
                sharedCookiesString.forEach {
                    this[it.split("=")[0]] = it.split("=")[1]
                }
            }
            listCookies.add(
                Cookie.Builder()
                    .name("XAuthorizationToken")
                    .value(sharedCookiesPairs["XAuthorizationToken"]!!)
                    .path(sharedCookiesPairs["Path"]!!)
                    .domain(sharedCookiesPairs["Domain"]!!)
                    .build()
            )
        }
        if(listCookies.isNotEmpty()){
            this.cookieStore[url] = listCookies
        }
        else this.cookieStore[url] = cookies
        Log.d("COOKIE JAR SAVE", cookieStore.toString())
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {

        val listCookies = mutableListOf<Cookie>()

        if(sharedPreferencesHelper.getCookie()!=""){
            val sharedCookiesString = sharedPreferencesHelper.getCookie().split("; ").toList()
            val sharedCookiesPairs = mutableMapOf<String, String>()
            sharedCookiesString.forEach {
                sharedCookiesPairs[it.split("=")[0]] = it.split("=")[1]
            }

            listCookies.add(
                Cookie.Builder()
                    .name("XAuthorizationToken")
                    .value(sharedCookiesPairs["XAuthorizationToken"]!!)
                    .path(sharedCookiesPairs["Path"]!!)
                    .domain(sharedCookiesPairs["Domain"]!!)
                    .build()
            )
        }

        return if(listCookies.isNotEmpty()){
            Log.d("COOKIE JAR LOAD", listCookies.toString())
            listCookies
        } else{
            ArrayList()
        }
    }
}