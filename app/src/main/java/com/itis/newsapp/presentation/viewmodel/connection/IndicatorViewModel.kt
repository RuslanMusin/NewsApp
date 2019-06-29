package com.itis.newsapp.presentation.viewmodel.connection

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.domain.usecase.LoadSourcesUseCase
import com.itis.newsapp.presentation.base.RxViewModel
import com.itis.newsapp.presentation.ui.model.Response
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import com.itis.newsapp.util.ConnectionLiveData
import javax.inject.Inject

class IndicatorViewModel @Inject
    constructor(application: Application) : AndroidViewModel(application) {

    private val _isWaiting = MutableLiveData<Boolean>()
    val isWaiting: LiveData<Boolean> = _isWaiting

    private val _isDisconnected = MutableLiveData<Boolean>()
    val isDisconnected: LiveData<Boolean> = _isDisconnected

    private val _showIndicators = MutableLiveData<Boolean>()
    val showIndicators: LiveData<Boolean> = _showIndicators

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    init {
        _isWaiting.value = false
        _isDisconnected.value = false
        _showIndicators.value = false
    }

    fun setWaitIndicator(isWaiting: Boolean) {
        if(isWaiting) {
            _isDisconnected.value = false
        }
        _isWaiting.value = isWaiting
        setShowIndicators()
    }

    fun setDisconnectIndicator(isDisconnected: Boolean) {
        if(isDisconnected) {
            _isWaiting.value = false
        }
        _isDisconnected.value = isDisconnected
        setShowIndicators()
    }

    fun setShowIndicators() {
        _showIndicators.value = isDisconnected.value?.or(isWaiting.value ?: false) ?: false
        Log.d("TAG", "dis = ${isDisconnected.value} " +
                "wait = ${isWaiting.value} " +
                "showInd = ${showIndicators.value}")
    }
}

//<!--android:visibility="@{indicatorModel.showIndicators ? View.VISIBLE : View.GONE}"-->
//android:visibility="@{indicatorModel.isWaiting() ? View.VISIBLE : View.GONE}"
