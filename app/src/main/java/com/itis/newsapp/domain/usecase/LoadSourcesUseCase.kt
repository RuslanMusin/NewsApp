package com.itis.newsapp.domain.usecase

import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.news.NewsRepository
import com.itis.newsapp.data.repository.source.SourceRepository
import io.reactivex.Single
import javax.inject.Inject

class LoadSourcesUseCase @Inject
constructor(private val sourceRepository: SourceRepository) {

    fun getArticlesSingle(): Single<List<Source>> {
        return sourceRepository.getSources()
    }
}