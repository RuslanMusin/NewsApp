package com.itis.newsapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.newsapp.data.db.model.ArticleDbEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: ArticleDbEntity): Completable

    @Query(
        """
        SELECT * FROM article
        ORDER BY publishedAt DESC
        """
    )
    abstract fun loadArticles(): Observable<List<ArticleDbEntity>>

    @Query(
        """
        SELECT 1 FROM article
        WHERE url = :url
        """
    )
    abstract fun getIsArticleSaved(url: String): Single<List<Boolean>>

}
