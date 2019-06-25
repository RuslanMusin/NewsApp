package com.itis.newsapp.data.network.interceptor

import okhttp3.Interceptor
import java.io.IOException
import okhttp3.HttpUrl
import okhttp3.Response


class ApiKeyInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", "60bd0949dac3428d91bd7fc95a12ab20")
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}