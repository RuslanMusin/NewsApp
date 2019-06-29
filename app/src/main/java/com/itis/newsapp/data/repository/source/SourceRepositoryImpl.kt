package com.itis.newsapp.data.repository.source

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import io.reactivex.Single
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor() : SourceRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest

    override fun getSources(): Single<List<Source>> {
        return apiRequest
            .getSourcesSingle()
            .map { it.sources }

    }

}