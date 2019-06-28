package com.itis.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.newsapp.data.network.pojo.response.news.Article
import io.reactivex.Completable

@Dao
abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Article): Completable

    @Query(
        """
        SELECT * FROM article
        ORDER BY publishedAt DESC
        """
    )
    abstract fun loadArticles(): LiveData<List<Article>>

    @Query(
        """
        SELECT 1 FROM article
        WHERE url = :url
        """
    )
    abstract fun getIsArticleSaved(url: String): LiveData<Boolean>

}
