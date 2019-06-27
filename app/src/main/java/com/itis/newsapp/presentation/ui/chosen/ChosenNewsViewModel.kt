package com.itis.newsapp.presentation.ui.chosen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.db.NewsDao
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.news.NewsRepository
import com.itis.newsapp.presentation.rx.transformer.PresentationCompletableTransformer
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChosenNewsViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    val articles: LiveData<List<Article>> = repository.getArticles()
}