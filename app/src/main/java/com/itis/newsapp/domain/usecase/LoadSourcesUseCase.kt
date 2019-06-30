package com.itis.newsapp.domain.usecase

import com.itis.newsapp.domain.dto.output.SourceOutputDto
import com.itis.newsapp.domain.mappers.SourceDtoMapper
import com.itis.newsapp.domain.repository.SourceRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoadSourcesUseCase @Inject
constructor(private val sourceRepository: SourceRepository) {

    fun getArticlesSingle(): Observable<SourceOutputDto> {
        return sourceRepository.getSources()
            .map { SourceDtoMapper.map(it) }

    }
}