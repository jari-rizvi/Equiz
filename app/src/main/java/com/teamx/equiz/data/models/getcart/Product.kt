package com.teamx.equiz.data.models.getcart

import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val icon: List<String>,
    val images: List<String>,
    val price: Double,
    val title: String
)