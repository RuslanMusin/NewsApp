package com.itis.newsapp.domain.mappers

import com.itis.newsapp.domain.dto.ArticleOutputDto
import com.itis.newsapp.domain.entity.ArticleEntity

object ArticleDtoMapper {

    fun map(articleEntity: ArticleEntity): ArticleOutputDto {
        return ArticleOutputDto(articleEntity)
    }
}