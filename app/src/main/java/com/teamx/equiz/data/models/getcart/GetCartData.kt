package com.teamx.equiz.data.models.getcart

data class GetCartData(
    val cartPrice: Int,
    val `data`: List<Data>,
    val discount: Double,
    val discountCoupon: DiscountCoupon
)