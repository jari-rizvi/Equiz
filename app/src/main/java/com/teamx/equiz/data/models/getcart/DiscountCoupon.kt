package com.teamx.equiz.data.models.getcart

import androidx.annotation.Keep

@Keep

data class DiscountCoupon(
    val __v: Int,
    val _id: String,
    val amount: Double,
    val code: String,
    val createdAt: String,
    val endDate: String,
    val isActive: Boolean,
    val startDate: String,
    val title: String,
    val type: String,
    val updatedAt: String,
    val used: Boolean
)