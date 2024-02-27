package com.teamx.equiz.data.models.cancelOrder

data class CartDetail(
    val __v: Int,
    val _id: String,
    val cartPrice: Int,
    val productDetails: List<ProductDetail>
)