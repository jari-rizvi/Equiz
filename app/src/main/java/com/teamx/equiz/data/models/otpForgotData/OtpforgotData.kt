package com.teamx.equiz.data.models.otpForgotData

import androidx.annotation.Keep

@Keep
data class OtpforgotData(
    val message: String,
    val newUniqueID: String
)