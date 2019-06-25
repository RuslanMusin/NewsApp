package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itis.newsapp.data.network.api.ApiSuccessResponse
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest

    override fun getNews(source: String): LiveData<List<Article>> {
        return apiRequest
            .getNews(source)
            .map {
                (it as ApiSuccessResponse<News>).body.articles
            }
    }

}