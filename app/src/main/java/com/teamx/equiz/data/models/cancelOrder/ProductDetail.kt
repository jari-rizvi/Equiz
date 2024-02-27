package com.teamx.equiz.data.models.cancelOrder

data class ProductDetail(
    val _id: String,
    val discount: Int,
    val product: Product,
    val quantity: Int,
    val totalPoint: Int
)