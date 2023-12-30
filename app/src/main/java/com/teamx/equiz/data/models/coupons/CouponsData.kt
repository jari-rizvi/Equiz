package com.teamx.equiz.data.models.coupons

import androidx.annotation.Keep

@Keep
data class CouponsData(
    val `data`: List<Data>,
    val totalCoupons: Int,
    val totalPages: Int
)
