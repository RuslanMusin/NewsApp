package com.itis.newsapp.data.repository.source

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Sources
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor() : SourceRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest

    override fun getSources(): LiveData<DataWrapper<Sources>> {
        return apiRequest.getSourcesSingle()
    }

}