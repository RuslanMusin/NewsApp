package com.itis.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.newsapp.data.network.pojo.response.news.Article

@Database(
    entities = [
        Article::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDb : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}