package com.teamx.equiz.data.models.bannerData.bannews

data class BanNews(
    val newsData: List<NewsData>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)