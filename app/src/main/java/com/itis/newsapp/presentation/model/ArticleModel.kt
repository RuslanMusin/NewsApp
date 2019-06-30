package com.itis.newsapp.presentation.model

import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import com.itis.newsapp.domain.dto.ArticleOutputDto
import java.util.*

data class ArticleModel(
    var source: ShortSource? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String?,
    var url: String,
    var urlToImage: String?,
    var publishedAt: Date?,
    var content: String?
)

object ArticleModelMapper {

    fun map(articleOutputDto: ArticleOutputDto): ArticleModel {
        return ArticleModel(
            articleOutputDto.articleEntity.source,
            articleOutputDto.articleEntity.author,
            articleOutputDto.articleEntity.title,
            articleOutputDto.articleEntity.description,
            articleOutputDto.articleEntity.url,
            articleOutputDto.articleEntity.urlToImage,
            articleOutputDto.articleEntity.publishedAt,
            articleOutputDto.articleEntity.content
        )
    }

}