package com.itis.newsapp.presentation.model

import com.itis.newsapp.domain.dto.output.SourceOutputDto

data class SourceModel(
    val id: String,
    val name: String?,
    val description: String?
)

object SourceModelMapper {

    fun map(sourceOutputDto: SourceOutputDto): SourceModel {
        return SourceModel(
            sourceOutputDto.sourceEntity.id,
            sourceOutputDto.sourceEntity.name,
            sourceOutputDto.sourceEntity.description
        )
    }

}