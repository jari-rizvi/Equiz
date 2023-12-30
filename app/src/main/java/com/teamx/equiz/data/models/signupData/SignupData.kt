package com.teamx.equiz.data.models.signupData

import androidx.annotation.Keep

@Keep
data class SignupData(
    val message: String,
    val otp: String
)