package com.teamx.equiz.data.models.orderDetailData

data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val point: Double,
    val title: String
)