package com.teamx.equiz.data.models.bannerData.news_banner

import androidx.annotation.Keep

@Keep
data class New(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val image: String,
    val isActive: Boolean,
    val tags: String,
    val title: String,
    val updatedAt: String
)