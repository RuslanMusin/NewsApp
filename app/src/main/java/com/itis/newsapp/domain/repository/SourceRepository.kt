package com.itis.newsapp.domain.repository

import com.itis.newsapp.domain.entity.SourceEntity
import io.reactivex.Observable

interface SourceRepository {

    fun getSources(): Observable<SourceEntity>
}