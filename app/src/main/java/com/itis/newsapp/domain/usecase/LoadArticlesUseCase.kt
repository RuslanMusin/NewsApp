package com.itis.newsapp.domain.usecase

import com.itis.newsapp.domain.dto.ArticleOutputDto
import com.itis.newsapp.domain.mappers.ArticleDtoMapper
import com.itis.newsapp.domain.repository.NewsRepository
import io.reactivex.Observable
import javax.inject.Inject


class LoadArticlesUseCase @Inject
constructor(private val newsRepository: NewsRepository) {

    fun getArticlesSingle(sourceId: String): Observable<ArticleOutputDto> {
        return newsRepository
            .getArticles(sourceId)
            .map { ArticleDtoMapper.map(it) }
    }
}