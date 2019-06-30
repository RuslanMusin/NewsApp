package com.itis.newsapp.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.itis.newsapp.data.db.converter.DateConverter
import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import java.io.Serializable
import java.util.*

@Entity(tableName = "article", primaryKeys = ["url"])
@TypeConverters(DateConverter::class)
class ArticleDbEntity(
    @Embedded(prefix = "source")
    var source: ShortSource? = null,
    val author: String?,
    var title: String? = null,
    var description: String?,
    var url: String,
    var urlToImage: String?,
    var publishedAt: Date?,
    var content: String?
) : Serializable {

}