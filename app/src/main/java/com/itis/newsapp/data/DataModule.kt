package com.itis.newsapp.data

import com.itis.newsapp.data.db.DatabaseModule
import com.itis.newsapp.data.network.NetworkModule
import com.itis.newsapp.data.repository.RepositoryBinder
import dagger.Module

@Module(includes = [NetworkModule::class, RepositoryBinder::class, DatabaseModule::class])
class DataModule {
}