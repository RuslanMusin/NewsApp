package com.itis.newsapp.presentation.ui.source

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject

class SourcesListViewModel @Inject constructor(application: Application, val repository: SourceRepository) : AndroidViewModel(application) {

    private val _sources: MediatorLiveData<DataWrapper<Sources>>

    val sources: LiveData<DataWrapper<Sources>>
        get() = _sources

    init {
        _sources = MediatorLiveData<DataWrapper<Sources>>()
        _sources.value = null
        _sources.addSource<DataWrapper<Sources>>(repository.getSources(), _sources::setValue)
    }

    fun requestSources() {
        _sources.addSource<DataWrapper<Sources>>(repository.getSources(), _sources::setValue)
    }
}