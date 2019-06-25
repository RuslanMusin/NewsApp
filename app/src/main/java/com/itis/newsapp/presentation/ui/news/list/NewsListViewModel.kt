package com.itis.newsapp.presentation.ui.news.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.news.NewsRepository
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject

class NewsListViewModel @Inject constructor(application: Application, val repository: NewsRepository) : AndroidViewModel(application) {

    private val mObservableProducts: MediatorLiveData<List<Article>>

    val articles: LiveData<List<Article>>
        get() = mObservableProducts

    init {
        mObservableProducts = MediatorLiveData<List<Article>>()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

    }

    fun setNews(source: Source) {
        val products = repository.getNews(source.id)

        // observe the changes of the articles from the database and forward them
        mObservableProducts.addSource<List<Article>>(products, mObservableProducts::setValue)
    }
}