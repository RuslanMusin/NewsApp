package com.itis.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.newsapp.data.db.dao.NewsDao
import com.itis.newsapp.data.db.model.ArticleDbEntity

@Database(
    entities = [
        ArticleDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDb : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}