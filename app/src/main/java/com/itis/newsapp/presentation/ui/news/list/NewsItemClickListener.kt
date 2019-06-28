package com.itis.newsapp.presentation.ui.news.list

import com.itis.newsapp.data.network.pojo.response.news.Article

interface NewsItemClickListener {
    fun onClick(article: Article)
}
