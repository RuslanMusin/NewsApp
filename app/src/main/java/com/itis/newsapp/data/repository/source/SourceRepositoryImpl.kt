package com.itis.newsapp.data.repository.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.source.Source
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor() : SourceRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest

    override fun getSources(): LiveData<List<Source>?> {
        return apiRequest
            .getSourcesSingle()
            .map {
                it.sources
            }
    }

}