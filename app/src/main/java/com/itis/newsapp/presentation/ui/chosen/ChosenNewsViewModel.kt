package com.itis.newsapp.presentation.ui.chosen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.repository.news.NewsRepository
import javax.inject.Inject

class ChosenNewsViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    private val _articles: MediatorLiveData<List<Article>> = MediatorLiveData()
    val articles: LiveData<List<Article>> = repository.getChosenArticles()

    init {
        _articles.value = null
        _articles.addSource(repository.getChosenArticles(), _articles::setValue)
    }

    fun requestArticles() {
        _articles.addSource(repository.getChosenArticles(), _articles::setValue)
    }
}