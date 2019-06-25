package com.itis.newsapp.data.db

import android.app.Application
import androidx.room.Room
import com.itis.newsapp.data.network.NetworkModule
import com.itis.newsapp.data.repository.RepositoryBinder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDb {
        return Room
            .databaseBuilder(app, NewsDb::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDb): NewsDao {
        return db.newsDao()
    }
}