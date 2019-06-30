package com.itis.newsapp.domain.entity

import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import java.util.*

data class ArticleEntity(
    val source: ShortSource?,
    val author: String?,
    val title: String?,
    var description: String?,
    var url: String,
    var urlToImage: String?,
    var publishedAt: Date?,
    var content: String?
)