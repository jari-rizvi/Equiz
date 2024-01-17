package com.teamx.equiz.data.models.orderDetailData

data class CartDetail(
    val __v: Int,
    val _id: String,
    val cartPrice: Int,
    val productDetails: List<ProductDetail>
)