package com.itis.newsapp.presentation.ui.chosen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.domain.usecase.LoadChosenArticlesUseCase
import com.itis.newsapp.presentation.base.viewmodel.RxViewModel
import com.itis.newsapp.presentation.model.ArticleModel
import com.itis.newsapp.presentation.model.ArticleModelMapper
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import javax.inject.Inject

class ChosenNewsViewModel
    @Inject constructor(application: Application)
    : RxViewModel(application)
{

    val _response = MutableLiveData<Response<List<ArticleModel>>>()
    val response: LiveData<Response<List<ArticleModel>>> = _response

    val _isDisconnected = MutableLiveData<Boolean>()
    val isDisconnected: LiveData<Boolean> = _isDisconnected

    @Inject
    lateinit var loadChosenArticlesUseCase: LoadChosenArticlesUseCase
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    init {
        _response.value = Response.loading()
        _isDisconnected.value = false
    }

    fun loadChosenArticles() {
        loadChosenArticlesUseCase
            .getArticlesFlowable()
            .map {
                ArticleModelMapper.map(it)
            }
            .toList()
            .compose(PresentationSingleTransformer())
            .doOnSubscribe({ _response.setValue(Response.loading()) })
            .subscribe(
                { response ->
                    _response.setValue(Response.success(response))
                },
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