package com.itis.newsapp.domain.entity

data class SourceEntity(
    val id: String,
    val name: String?,
    val description: String?,
    var url: String?,
    var category: String?,
    var language: String?,
    var country: String?
)