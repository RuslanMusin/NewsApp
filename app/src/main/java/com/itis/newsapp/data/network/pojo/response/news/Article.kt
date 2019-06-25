package com.itis.newsapp.data.network.pojo.response.news

import com.itis.newsapp.data.network.pojo.response.source.Source

class Article {
    var source: Source? = null
    var author: String = ""
    var title: String = ""
    var description: String = ""
    var url: String = ""
    var urlToImage: String = ""
    var publishedAt: String = ""
    var content: String = ""
}