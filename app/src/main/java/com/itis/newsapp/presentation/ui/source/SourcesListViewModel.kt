package com.itis.newsapp.presentation.ui.source

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject

class SourcesListViewModel @Inject constructor(application: Application, repository: SourceRepository) : AndroidViewModel(application) {

    val sources: LiveData<List<Source>?> = repository.getSources()
}