package com.itis.newsapp.presentation.ui.news.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.repository.news.NewsRepository
import javax.inject.Inject

class NewsListViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    private val _articles: MediatorLiveData<DataWrapper<News>> = MediatorLiveData()
    val articles: LiveData<DataWrapper<News>> = _articles

    init {
        _articles.value = null
    }

    fun setNews(sourceId: String) {
        _articles.addSource(repository.getNews(sourceId), _articles::setValue)
    }
}