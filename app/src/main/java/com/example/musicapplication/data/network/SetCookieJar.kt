package com.example.musicapplication.data.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class SetCookieJar : CookieJar {
    private var cookieStore: HashMap<HttpUrl, List<Cookie>>? = HashMap()
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookieStore!!.put(url, cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore!!.get(url) ?: ArrayList()
    }
}