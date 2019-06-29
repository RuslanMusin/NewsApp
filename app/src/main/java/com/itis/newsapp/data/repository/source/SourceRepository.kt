package com.itis.newsapp.data.repository.source

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import io.reactivex.Single

interface SourceRepository {

    fun getSources(): Single<List<Source>>
}