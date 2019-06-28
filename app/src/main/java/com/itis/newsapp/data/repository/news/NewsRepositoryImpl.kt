package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.db.NewsDao
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import io.reactivex.Completable
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest
    @Inject
    lateinit var newsDao: NewsDao

    override fun getNews(source: String): LiveData<DataWrapper<News>> {
        return apiRequest
            .getNews(source)
    }

    override fun insertArticle(article: Article): Completable {
        return newsDao.insert(article)
    }

    override fun getChosenArticles(): LiveData<List<Article>> {
        return newsDao.loadArticles()
    }

    override fun getIsArticleSaved(url: String): LiveData<Boolean> {
        return newsDao.getIsArticleSaved(url)
    }

}