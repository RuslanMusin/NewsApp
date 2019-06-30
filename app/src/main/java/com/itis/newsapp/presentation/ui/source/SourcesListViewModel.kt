package com.itis.newsapp.presentation.ui.source

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.domain.usecase.LoadSourcesUseCase
import com.itis.newsapp.presentation.base.viewmodel.RxViewModel
import com.itis.newsapp.presentation.model.SourceModel
import com.itis.newsapp.presentation.model.SourceModelMapper
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import javax.inject.Inject


class SourcesListViewModel
    @Inject constructor(application: Application)
    :RxViewModel(application)
{

    val _response = MutableLiveData<Response<List<SourceModel>>>()
    val response: LiveData<Response<List<SourceModel>>> = _response

    val _isDisconnected = MutableLiveData<Boolean>()
    val isDisconnected: LiveData<Boolean> = _isDisconnected

    @Inject
    lateinit var loadSourcesUseCase: LoadSourcesUseCase
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun initState() {
        _response.value = Response.loading()
        _isDisconnected.value = false
    }

    fun loadSources() {
        loadSourcesUseCase
            .getArticlesSingle()
            .map { SourceModelMapper.map(it) }
            .toList()
            .compose(PresentationSingleTransformer())
            .doOnSubscribe({ _response.setValue(Response.loading()) })
            .subscribe(
                { response ->
                    _response.setValue(Response.success(response))
                },
                { throwable ->
                    Log.d("TAG","eror = ${throwable.message}")
                    if(throwable is NoInternetConnectionException) {
                        _isDisconnected.value = true
                    }
                    _response.setValue(
                        Response.error(exceptionProcessor.processException(throwable)))

                }
            ).disposeWhenDestroy()
    }

}