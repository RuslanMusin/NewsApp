package com.itis.newsapp.data.repository.news

import android.util.Log
import com.itis.newsapp.data.db.dao.NewsDao
import com.itis.newsapp.data.db.model.ArticleDbEntity
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.domain.dto.input.ArticleInputDto
import com.itis.newsapp.domain.entity.ArticleEntity
import com.itis.newsapp.domain.repository.NewsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest
    @Inject
    lateinit var newsDao: NewsDao

    override fun getArticles(source: String): Observable<ArticleEntity> {
        return apiRequest
            .getNews(source)
            .map { it.articles }
            .flatMapObservable {
                Observable.fromIterable(it)
            }.map {
                ArticleEntity(
                    it.source,
                    it.author,
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt,
                    it.content
                )
            }

    }

    override fun insertArticle(article: ArticleInputDto): Completable {
        return newsDao.insert(
            ArticleDbEntity (
                article.source,
                article.author,
                article.title,
                article.description,
                article.url,
                article.urlToImage,
                article.publishedAt,
                article.content
        ))
    }

    override fun getChosenArticles(): Observable<ArticleEntity> {
        return newsDao.loadArticles()
            .firstOrError()
            .toObservable()
            .flatMapIterable { it }
            .map {
                ArticleEntity(
                    it.source,
                    it.author,
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt,
                    it.content
                )
            }
    }

    override fun getIsArticleSaved(url: String): Single<Boolean> {
        return newsDao
            .getIsArticleSaved(url)
            .flatMap {
                if(it.isEmpty()) Single.just(false) else Single.just(it[0])
            }
    }

}