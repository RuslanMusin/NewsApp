package com.itis.newsapp.data.network.pojo.response.news

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itis.newsapp.data.network.pojo.response.source.Source
import java.io.Serializable

@Entity(tableName = "article")
class Article : Serializable {
    @Embedded(prefix = "source")
    var source: Source? = null
    var author: String = ""
    var title: String = ""
    var description: String = ""
    @PrimaryKey
    var url: String = ""
    var urlToImage: String = ""
    var publishedAt: String = ""
    var content: String = ""
}