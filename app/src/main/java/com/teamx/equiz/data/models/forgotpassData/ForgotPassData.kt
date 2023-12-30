package com.teamx.equiz.data.models.forgotpassData

import androidx.annotation.Keep

@Keep
data class ForgotPassData(
    val message: String,
    val updatedData: UpdatedData
)