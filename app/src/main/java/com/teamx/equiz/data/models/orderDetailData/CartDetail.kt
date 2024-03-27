package com.teamx.equiz.data.models.orderDetailData
import androidx.annotation.Keep

@Keep
data class CartDetail(
    val __v: Int,
    val _id: String,
    val cartPrice: Double,
    val productDetails: List<ProductDetail>
)