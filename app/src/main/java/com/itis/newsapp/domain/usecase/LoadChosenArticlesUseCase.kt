package com.itis.newsapp.domain.usecase

import com.itis.newsapp.domain.dto.ArticleOutputDto
import com.itis.newsapp.domain.mappers.ArticleDtoMapper
import com.itis.newsapp.domain.repository.NewsRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoadChosenArticlesUseCase @Inject
constructor(private val newsRepository: NewsRepository) {

    fun getArticlesFlowable(): Observable<ArticleOutputDto> {
        return newsRepository
            .getChosenArticles()
            .map { ArticleDtoMapper.map(it) }

    }
}