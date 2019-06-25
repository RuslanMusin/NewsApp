package com.itis.newsapp.data.network.pojo.response.news

class News {

    var status = ""
    var totalResults: Long = 0
    var articles: List<Article> = ArrayList()
}
