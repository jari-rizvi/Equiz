package com.teamx.equiz.data.models.newsData

import androidx.annotation.Keep

@Keep
data class NewsDataX(
    val __v: Int,
    val _id: String,
    val country: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val isActive: Boolean,
    val title: String,
    val type: String,
    val updatedAt: String
)