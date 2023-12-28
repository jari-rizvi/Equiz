package com.teamx.equiz.ui.fragments.address.dataclasses

data class Data(
    val __v: Int,
    val _id: String,
    val coupon: String,
    val createdAt: String,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Int,
    val totalPrice: Int,
    val user: String
)