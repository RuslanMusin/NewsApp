package com.itis.newsapp.data

import com.itis.newsapp.data.db.DatabaseModule
import com.itis.newsapp.data.network.NetworkModule
import com.itis.newsapp.data.repository.RepositoryBinder
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, RepositoryBinder::class, DatabaseModule::class])
class DataModule {
}