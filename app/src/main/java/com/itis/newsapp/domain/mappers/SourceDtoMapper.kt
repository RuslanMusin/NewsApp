package com.itis.newsapp.domain.mappers

import com.itis.newsapp.domain.dto.output.SourceOutputDto
import com.itis.newsapp.domain.entity.SourceEntity

object SourceDtoMapper {

    fun map(sourceEntity: SourceEntity): SourceOutputDto {
        return SourceOutputDto(sourceEntity)
    }
}