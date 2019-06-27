package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itis.newsapp.data.db.NewsDao
import com.itis.newsapp.data.db.NewsDb
import com.itis.newsapp.data.network.api.ApiSuccessResponse
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import io.reactivex.Completable
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest
    @Inject
    lateinit var newsDb: NewsDb
    @Inject
    lateinit var newsDao: NewsDao

    override fun getNews(source: String): LiveData<List<Article>> {
        return apiRequest
            .getNews(source)
            .map {
                (it as ApiSuccessResponse<News>).body.articles
            }
    }

    override fun insertArticle(article: Article): Completable {
        return newsDao.insert(article)
    }

    override fun getArticles(): LiveData<List<Article>> {
        return newsDao
            .loadArticles()
    }

}