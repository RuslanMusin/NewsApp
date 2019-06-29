package com.itis.newsapp.domain.usecase

import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.repository.news.NewsRepository
import io.reactivex.Single
import javax.inject.Inject


class LoadArticlesUseCase @Inject
constructor(private val newsRepository: NewsRepository) {

    fun getArticlesSingle(sourceId: String): Single<List<Article>> {
        return newsRepository.getArticles(sourceId)
    }
}