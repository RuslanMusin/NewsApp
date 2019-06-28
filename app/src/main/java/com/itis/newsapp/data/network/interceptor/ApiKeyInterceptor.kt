package com.itis.newsapp.data.network.interceptor

import com.itis.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ApiKeyInterceptor() : Interceptor {

    private val API_KEY_PARAM = "apiKey"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}