package com.itis.newsapp.presentation.ui.fragments.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.presentation.base.viewmodel.RxViewModel
import com.itis.newsapp.presentation.model.ArticleModel
import javax.inject.Inject

class ArticleSharedViewModel
    @Inject constructor(application: Application)
    : RxViewModel(application)
{

    private val _article = MutableLiveData<MutableMap<Int, ArticleModel>>()
    val article: LiveData<MutableMap<Int, ArticleModel>> = _article

    init {
        _article.value = HashMap()
    }

    fun selectArticle(item: ArticleModel, menuItem: Int) {
        _article.value?.set(menuItem, item)
    }
}