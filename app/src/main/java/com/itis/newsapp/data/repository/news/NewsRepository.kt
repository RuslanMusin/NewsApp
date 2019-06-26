package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import io.reactivex.Completable

interface NewsRepository {

    fun getNews(source: String): LiveData<List<Article>>

    fun insertArticle(article: Article): Completable

    fun getArticles(): LiveData<List<Article>>
}