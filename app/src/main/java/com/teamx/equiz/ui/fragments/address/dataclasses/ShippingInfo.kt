package com.teamx.equiz.ui.fragments.address.dataclasses

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