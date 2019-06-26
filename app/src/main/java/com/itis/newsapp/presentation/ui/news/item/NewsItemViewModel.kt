package com.itis.newsapp.presentation.ui.news.item

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.db.NewsDao
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.news.NewsRepository
import com.itis.newsapp.presentation.rx.transformer.PresentationCompletableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsItemViewModel @Inject constructor(application: Application, val repository: NewsRepository) : AndroidViewModel(application) {

    private val mObservableProducts: MediatorLiveData<Article>

    private val disposable = CompositeDisposable()


    val article: LiveData<Article>
        get() = mObservableProducts

    init {
        mObservableProducts = MediatorLiveData<Article>()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

    }

    fun setNews(source: Article) {
        val products = MutableLiveData<Article>()
        products.value = source

        // observe the changes of the articles from the database and forward them
        mObservableProducts.addSource<Article>(products, mObservableProducts::setValue)
    }

    fun addArticle() {
        article.value?.let {
            disposable.add(
                repository.insertArticle(it)
                    .compose(PresentationCompletableTransformer())
                    .subscribe({ Log.d("TAG", "insert article") },
                        { error -> Log.e("TAG", "error insert article", error) })
            )
        }
    }
}