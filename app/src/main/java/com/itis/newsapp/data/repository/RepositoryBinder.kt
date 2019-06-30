package com.itis.newsapp.data.repository

import com.itis.newsapp.data.repository.news.NewsRepositoryImpl
import com.itis.newsapp.data.repository.source.SourceRepositoryImpl
import com.itis.newsapp.domain.repository.NewsRepository
import com.itis.newsapp.domain.repository.SourceRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBinder {

    @Binds
    @Singleton
    abstract fun sourceRepository(repositoryImpl: SourceRepositoryImpl): SourceRepository

    @Binds
    @Singleton
    abstract fun newsRepository(repositoryImpl: NewsRepositoryImpl): NewsRepository
}