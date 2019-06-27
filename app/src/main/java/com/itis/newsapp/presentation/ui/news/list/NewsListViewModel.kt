package com.itis.newsapp.presentation.ui.news.list

import android.app.Application
import androidx.lifecycle.*
import com.bumptech.glide.load.engine.Resource
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.news.NewsRepository
import javax.inject.Inject

class NewsListViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    private val _sourceId: MutableLiveData<String> = MutableLiveData()
    val sourceId: LiveData<String>
        get() = _sourceId

    val articles: LiveData<List<Article>> = Transformations
        .switchMap(sourceId) { id -> repository.getNews(id)}


    fun setNews(sourceId: String) {
        _sourceId.value = sourceId
    }
}