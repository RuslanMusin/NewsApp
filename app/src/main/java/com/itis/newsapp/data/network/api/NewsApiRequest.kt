package com.itis.newsapp.data.network.api

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.source.Sources
import retrofit2.http.GET

interface NewsApiRequest {

    @GET("sources")
    fun getSourcesSingle(): LiveData<Sources>


}