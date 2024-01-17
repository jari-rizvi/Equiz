package com.teamx.equiz.data.models.getcart

import androidx.annotation.Keep

@Keep

data class Product(
    val _id: String,
    val images: List<String>,
    val point: Double,
    val title: String
)