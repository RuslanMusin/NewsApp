package com.itis.newsapp.data.repository

import com.itis.newsapp.data.repository.source.SourceRepository
import com.itis.newsapp.data.repository.source.SourceRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBinder {

    @Binds
    @Singleton
    abstract fun sourceRepository(repositoryImpl: SourceRepositoryImpl): SourceRepository
}