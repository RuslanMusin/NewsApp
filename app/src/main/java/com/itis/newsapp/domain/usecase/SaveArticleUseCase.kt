package com.itis.newsapp.domain.usecase

import com.itis.newsapp.domain.dto.input.ArticleInputDto
import com.itis.newsapp.domain.repository.NewsRepository
import io.reactivex.Completable
import javax.inject.Inject

class SaveArticleUseCase @Inject
constructor(private val newsRepository: NewsRepository) {

    fun getSaveArticleCompletable(article: ArticleInputDto): Completable {
        return newsRepository.insertArticle(article)
    }
}