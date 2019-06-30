package com.itis.newsapp.data.network.pojo.response.news

import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import java.io.Serializable
import java.util.*

class Article : Serializable {
    var source: ShortSource? = null
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String = ""
    var urlToImage: String? = null
    var publishedAt: Date = Date()
    var content: String? = null
}