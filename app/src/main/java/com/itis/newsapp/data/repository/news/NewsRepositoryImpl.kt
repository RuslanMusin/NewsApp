package com.itis.newsapp.data.repository.news

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.db.NewsDao
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest
    @Inject
    lateinit var newsDao: NewsDao

    override fun getArticles(source: String): Single<List<Article>> {
        return apiRequest
            .getNews(source)
            .map { it.articles }
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