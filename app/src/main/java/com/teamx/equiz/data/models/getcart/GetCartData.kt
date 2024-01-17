package com.teamx.equiz.data.models.getcart

import androidx.annotation.Keep

@Keep

data class GetCartData(
    val cartPrice: Double,
    val `data`: List<Data>,
    val discount: Double,
    val discountCoupon: DiscountCoupon
)