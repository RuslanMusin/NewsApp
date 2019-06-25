package com.itis.newsapp.presentation.ui.source

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject

class SourceListViewModel @Inject constructor(application: Application, repository: SourceRepository) : AndroidViewModel(application) {

    private val mObservableProducts: MediatorLiveData<List<Source>>

    val sources: LiveData<List<Source>>
        get() = mObservableProducts

    init {

        mObservableProducts = MediatorLiveData<List<Source>>()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

        val products = repository.getSources()

        // observe the changes of the sources from the database and forward them
        mObservableProducts.addSource<List<Source>>(products, mObservableProducts::setValue)
    }
}