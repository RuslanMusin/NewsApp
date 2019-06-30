package com.itis.newsapp.domain.dto.input

import com.itis.newsapp.data.network.pojo.response.source.ShortSource
import com.itis.newsapp.presentation.model.ArticleModel
import java.util.*

class ArticleInputDto(
    var source: ShortSource? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String?,
    var url: String,
    var urlToImage: String?,
    var publishedAt: Date?,
    var content: String?
)

object ArticleInputMapper {

    fun map(articleModel: ArticleModel): ArticleInputDto {
        return ArticleInputDto(
            articleModel.source,
            articleModel.author,
            articleModel.title,
            articleModel.description,
            articleModel.url,
            articleModel.urlToImage,
            articleModel.publishedAt,
            articleModel.content
            )
    }
}