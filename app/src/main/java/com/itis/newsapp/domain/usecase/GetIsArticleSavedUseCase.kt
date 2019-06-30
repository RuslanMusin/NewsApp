package com.itis.newsapp.domain.usecase

import android.util.Log
import com.itis.newsapp.domain.repository.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetIsArticleSavedUseCase @Inject
constructor(private val newsRepository: NewsRepository) {

    fun getIsArticleSavedSingle(url: String): Single<Boolean> {
        Log.d("TAG", "getSaved")
        return newsRepository.getIsArticleSaved(url)
    }
}