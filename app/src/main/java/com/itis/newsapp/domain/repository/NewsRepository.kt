package com.itis.newsapp.domain.repository

import com.itis.newsapp.domain.dto.input.ArticleInputDto
import com.itis.newsapp.domain.entity.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NewsRepository {

    fun getArticles(source: String): Observable<ArticleEntity>

    fun insertArticle(article: ArticleInputDto): Completable

    fun getChosenArticles(): Observable<ArticleEntity>

    fun getIsArticleSaved(url: String): Single<Boolean>
}