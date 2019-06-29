package com.itis.newsapp.presentation.ui.source

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.domain.usecase.LoadSourcesUseCase
import com.itis.newsapp.presentation.base.RxViewModel
import javax.inject.Inject
import retrofit2.adapter.rxjava2.Result.response
import com.itis.newsapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.newsapp.presentation.ui.model.Response
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor


class SourcesListViewModel @Inject
constructor(application: Application) :
RxViewModel(application) {

    val _response = MutableLiveData<Response<List<Source>>>()
    val response: LiveData<Response<List<Source>>> = _response

    val _isDisconnected = MutableLiveData<Boolean>()
    val isDisconnected: LiveData<Boolean> = _isDisconnected

    @Inject
    lateinit var loadSourcesUseCase: LoadSourcesUseCase
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    init {
        _response.value = Response.loading()
        _isDisconnected.value = false
    }

    fun loadSources() {
        loadSourcesUseCase
            .getArticlesSingle()
            .compose(PresentationSingleTransformer())
            .doOnSubscribe({ _response.setValue(Response.loading()) })
            .subscribe(
                { greeting ->
                    Log.d("TAG", "loaded")
                    _response.setValue(Response.success(greeting))
                },
                { throwable ->
                    Log.d("TAG", "${throwable.message}")
                    if(throwable is NoInternetConnectionException) {
                        _isDisconnected.value = true
                    }
                    _response.setValue(
                        Response.error(exceptionProcessor.processException(throwable)))
                }
            ).disposeWhenDestroy()
    }

}