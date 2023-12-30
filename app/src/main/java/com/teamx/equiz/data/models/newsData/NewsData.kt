package com.teamx.equiz.data.models.newsData

import androidx.annotation.Keep

@Keep
data class NewsData(
    val newsData: List<NewsDataX>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)