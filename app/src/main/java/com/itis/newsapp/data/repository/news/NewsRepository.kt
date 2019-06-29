package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {

    fun getArticles(source: String): Single<List<Article>>

    fun insertArticle(article: Article): Completable

    fun getChosenArticles(): LiveData<List<Article>>

    fun getIsArticleSaved(url: String): LiveData<Boolean>
}