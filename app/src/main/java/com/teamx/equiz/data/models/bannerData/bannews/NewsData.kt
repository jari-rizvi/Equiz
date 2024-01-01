package com.teamx.equiz.data.models.bannerData.bannews

import androidx.annotation.Keep

@Keep
data class NewsData(
    val __v: Int,
    val _id: String,
    val country: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val isActive: Boolean,
    val title: String,
    val updatedAt: String
)