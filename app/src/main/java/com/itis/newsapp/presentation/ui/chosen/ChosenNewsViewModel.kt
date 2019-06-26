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

class ChosenNewsViewModel @Inject constructor(application: Application, val repository: NewsRepository) : AndroidViewModel(application) {

//    private val mObservableProducts: MediatorLiveData<List<Article>>

    @Inject
    lateinit var newsDao: NewsDao
    val articles: LiveData<List<Article>>
        get() = newsDao.loadContributors()

    /* init {
         mObservableProducts = MediatorLiveData<List<Article>>()
         // set by default null, until we get data from the database.
         mObservableProducts.value = null

     }

     fun setNews(source: Source) {
         val products = repository.getNews(source.id)

         // observe the changes of the articles from the database and forward them
         mObservableProducts.addSource<List<Article>>(products, mObservableProducts::setValue)
     }*/
}