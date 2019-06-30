package com.itis.newsapp.presentation.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.presentation.base.databinding.livedata.ConnectionLiveData
import javax.inject.Inject

class IndicatorViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

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
    }
}

