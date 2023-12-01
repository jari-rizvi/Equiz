package com.teamx.equiz.data.models.orderDetailData

data class ProductDetail(
    val _id: String,
    val product: Product,
    val quantity: Int,
    val totalPrice: Int
)