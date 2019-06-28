package com.itis.newsapp.data.network.api

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.network.pojo.response.source.Sources

class NewsApiRequestDecorator(val apiRequest: NewsApiRequest) : NewsApiRequest {

    override fun getSourcesSingle(): LiveData<DataWrapper<Sources>> {
        return apiRequest
            .getSourcesSingle()
    }

    override fun getNews(sources: String): LiveData<DataWrapper<News>> {
        return apiRequest
            .getNews(sources)
    }
}

