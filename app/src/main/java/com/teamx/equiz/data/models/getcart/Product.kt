package com.teamx.equiz.data.models.getcart

data class Product(
    val _id: String,
    val icon: List<String>,
    val images: List<String>,
    val price: Double,
    val title: String
)