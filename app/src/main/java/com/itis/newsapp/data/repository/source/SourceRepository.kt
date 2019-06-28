package com.itis.newsapp.data.repository.source

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Sources

interface SourceRepository {

    fun getSources(): LiveData<DataWrapper<Sources>>
}