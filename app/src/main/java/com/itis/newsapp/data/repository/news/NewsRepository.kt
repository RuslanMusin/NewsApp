package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source

interface NewsRepository {

    fun getNews(source: String): LiveData<List<Article>>
}