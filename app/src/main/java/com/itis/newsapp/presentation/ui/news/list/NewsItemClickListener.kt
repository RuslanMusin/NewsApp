package com.itis.newsapp.presentation.ui.news.list

import com.itis.newsapp.presentation.model.ArticleModel

interface NewsItemClickListener {
    fun onClick(article: ArticleModel)
}
