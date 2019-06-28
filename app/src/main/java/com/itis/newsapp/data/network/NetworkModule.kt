package com.itis.newsapp.data.network

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itis.newsapp.BuildConfig
import com.itis.newsapp.data.network.adapter.LiveDataCallAdapterFactory
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.api.NewsApiRequestDecorator
import com.itis.newsapp.data.network.interceptor.ApiKeyInterceptor
import com.itis.newsapp.util.ConnectionLiveData
import com.itis.newsapp.util.Const.TIME_NETWORK_FORMAT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
    }

    @Singleton
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun apiInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                              apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat(TIME_NETWORK_FORMAT).create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApiRequest {
        val service = retrofit.create(NewsApiRequest::class.java)
        return NewsApiRequestDecorator(service)
    }

    @Singleton
    @Provides
    fun provideConnectionLiveData(application: Application): ConnectionLiveData {
        return ConnectionLiveData(application)
    }
}