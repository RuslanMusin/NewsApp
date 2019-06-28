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
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsItemViewModel
    @Inject constructor(application: Application, val repository: NewsRepository)
    : AndroidViewModel(application) {

    private val _article = MutableLiveData<MutableMap<Int, Article>>()

    val article: LiveData<MutableMap<Int, Article>>
        get() = _article

    private val _isAdded = MediatorLiveData<Boolean>()

    val isAdded: LiveData<Boolean>
        get() = _isAdded

    private val compositeDisposable = CompositeDisposable()

    init {
        _article.value = HashMap()
        _isAdded.value = true
    }

    fun selectArticle(item: Article, menuItem: Int) {
        _article.value?.set(menuItem, item)
    }

    fun checkArticleExists(menuItem: Int) {
        article.value?.get(menuItem)?.let {
            _isAdded.addSource(repository.getIsArticleSaved(it.url), _isAdded::setValue)
        }
    }

    fun addArticle(menuItem: Int) {
        article.value?.get(menuItem)?.let {
            repository.insertArticle(it)
            .compose(PresentationCompletableTransformer())
            .doOnSubscribe {
                _isAdded.value = false
            }
            .doAfterTerminate { _isAdded.value = true }
            .subscribe(
                { Log.d("TAG", "insert article") },
                { error -> Log.e("TAG", "error insert article", error) }
            ).disposeWhenDestroy()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun Disposable.disposeWhenDestroy() {
        compositeDisposable.add(this)
    }
}