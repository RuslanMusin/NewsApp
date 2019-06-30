package com.itis.newsapp.presentation.ui.news.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.domain.usecase.LoadArticlesUseCase
import com.itis.newsapp.presentation.base.viewmodel.RxViewModel
import com.itis.newsapp.presentation.model.ArticleModel
import com.itis.newsapp.presentation.model.ArticleModelMapper
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import javax.inject.Inject

class NewsListViewModel
    @Inject constructor(application: Application)
    : RxViewModel(application) {

    @Inject
    lateinit var loadArticlesUseCase: LoadArticlesUseCase
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    private val _response = MutableLiveData<Response<List<ArticleModel>>>()
    val response: LiveData<Response<List<ArticleModel>>> = _response

    val _isDisconnected = MutableLiveData<Boolean>()
    val isDisconnected: LiveData<Boolean> = _isDisconnected

    fun initState() {
        _response.value = Response.loading()
        _isDisconnected.value = false
    }

    fun loadNews(sourceId: String) {
        loadArticlesUseCase
            .getArticlesSingle(sourceId)
            .map { ArticleModelMapper.map(it) }
            .toList()
            .compose(PresentationSingleTransformer())
            .doOnSubscribe({ _response.setValue(Response.loading()) })
            .subscribe(
                { response -> _response.setValue(Response.success(response)) },
                { throwable ->
                    if(throwable is NoInternetConnectionException) {
                        _isDisconnected.value = true
                    }
                    _response.setValue(
                        Response.error(exceptionProcessor.processException(throwable)))
                }
            ).disposeWhenDestroy()
    }
}