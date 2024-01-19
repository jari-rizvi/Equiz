package com.teamx.equiz.ui.fragments.address.dataclasses

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val coupon: String,
    val createdAt: String,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Double,
    val totalPrice: Double,
    val user: String
)