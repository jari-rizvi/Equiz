package com.teamx.equiz.data.models.newsData

data class NewsData(
    val newsData: List<NewsDataX>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)