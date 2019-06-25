package com.itis.newsapp.presentation.ui.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.repository.source.SourceRepository
import javax.inject.Inject


class SourceListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mRepository: SourceRepository

    private val mObservableProducts: MediatorLiveData<List<Source>>

    val sources: LiveData<List<Source>>
        get() = mObservableProducts

    init {

        mObservableProducts = MediatorLiveData<List<Source>>()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

        val products = mRepository.getSources()

        // observe the changes of the sources from the database and forward them
        mObservableProducts.addSource<List<Source>>(products, mObservableProducts::setValue)
    }

    fun searchProducts(): LiveData<List<Source>?> {
        return mRepository.getSources()
    }
}