package com.itis.newsapp.presentation.ui.fragments.news.list

import com.itis.newsapp.presentation.model.ArticleModel

interface NewsItemClickListener {
    fun onClick(article: ArticleModel)
}
