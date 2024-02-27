package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class Notifications(
    val email: Boolean,
    val push: Boolean,
    val sms: Boolean
)