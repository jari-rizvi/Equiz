package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class Addresse(
    val _id: String,
    val address: String,
    val label: String,
    val phoneNumber: String
)