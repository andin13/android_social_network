package com.example.androidsocialnetwork.data

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.stream.Collectors

val cookieStorage: MutableList<Cookie> = ArrayList()

class SimpleOkHttpCookieJar : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStorage.addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {

        // Remove expired Cookies
        cookieStorage.removeIf { cookie: Cookie -> cookie.expiresAt < System.currentTimeMillis() }

        // Only return matching Cookies
        return cookieStorage.stream().filter { cookie: Cookie ->
            cookie.matches(
                url
            )
        }.collect(Collectors.toList())
    }
}