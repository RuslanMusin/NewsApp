package com.itis.newsapp.data.network.api

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.network.pojo.response.source.Sources
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiRequest {

    @GET("sources")
    fun getSourcesSingle(): LiveData<ApiResponse<Sources>>

    @GET("top-headlines")
    fun getNews(@Query("sources") sources: String): LiveData<ApiResponse<News>>

}