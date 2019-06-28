package com.itis.newsapp.data.network.pojo.response.news

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.itis.newsapp.data.db.DateConverter
import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import java.io.Serializable
import java.util.*

@Entity(tableName = "article", primaryKeys = ["url"])
@TypeConverters(DateConverter::class)
class Article : Serializable {
    @Embedded(prefix = "source")
    var source: ShortSource? = null
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""
    var urlToImage: String = ""
    var publishedAt: Date = Date()
    var content: String = ""
}