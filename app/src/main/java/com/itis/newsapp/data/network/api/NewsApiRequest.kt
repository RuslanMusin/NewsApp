package com.itis.newsapp.data.network.api

import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.network.pojo.response.source.Sources
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiRequest {

    @GET("sources")
    fun getSourcesSingle(): Single<Sources>

    @GET("top-headlines")
    fun getNews(@Query("sources") sources: String): Single<News>

}