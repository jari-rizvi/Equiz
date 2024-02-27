package com.teamx.equiz.data.models.addressbyid
import androidx.annotation.Keep

@Keep
data class Data(
    val _id: String,
    val address: String,
    val label: String,
    val phoneNumber: String
)