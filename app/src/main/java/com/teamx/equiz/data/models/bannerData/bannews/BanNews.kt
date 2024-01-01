package com.teamx.equiz.data.models.bannerData.bannews

import androidx.annotation.Keep

@Keep
data class BanNews(
    val newsData: List<NewsData>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)