package com.teamx.equiz.data.models.orderDetailData

import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val price: Double,
    val title: String
)