package com.itis.newsapp.data.repository.source

import com.itis.newsapp.data.network.api.NewsApiRequest
import com.itis.newsapp.domain.entity.SourceEntity
import com.itis.newsapp.domain.repository.SourceRepository
import io.reactivex.Observable
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor() : SourceRepository {

    @Inject
    lateinit var apiRequest: NewsApiRequest

    override fun getSources(): Observable<SourceEntity> {
        return apiRequest
            .getSourcesSingle()
            .map { it.sources }
            .flatMapObservable {
                Observable.fromIterable(it)
            }.map {
                SourceEntity(
                    it.id,
                    it.name,
                    it.description,
                    it.url,
                    it.category,
                    it.language,
                    it.country
                )
            }

    }

}