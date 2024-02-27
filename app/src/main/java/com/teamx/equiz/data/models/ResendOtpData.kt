package com.teamx.equiz.data.models
import androidx.annotation.Keep

@Keep
data class ResendOtpData(
    val message: String,
    val newUniqueID: String
)