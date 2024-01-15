package com.teamx.equiz.data.models.getorderData

import androidx.annotation.Keep

@Keep
data class ShippingInfo(
    val address: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val postalCode: String,
    val state: String
)

@Keep
data class ShippingInfo2(
    val address: String?,
    val label: String?,
    val phoneNumber: String?,
)