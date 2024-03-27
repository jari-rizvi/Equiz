package com.teamx.equiz.data.models.meModel

import androidx.annotation.Keep

@Keep
data class Addresse(
    val _id: String,
    val address: String,
    val city: String,
    val country: String,
    val label: String,
    val landmark: String,
    val phoneNumber: String,
    val postalCode: String
)