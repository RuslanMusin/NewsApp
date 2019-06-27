package com.itis.newsapp.presentation.ui.news.item

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.repository.news.NewsRepository
import com.itis.newsapp.presentation.rx.transformer.PresentationCompletableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsItemViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    val _article = MutableLiveData<Article>()

    val article: LiveData<Article>
        get() = _article

    fun selectArticle(item: Article) {
        _article.value = item
    }

    private val disposable = CompositeDisposable()

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