package com.teamx.equiz.data.models.orderDetailData

data class ProductDetail(
    val _id: String,
    val discount: Double,
    val product: Product,
    val quantity: Int,
    val totalPoint: Double,
    var orderStatus: String?,
)