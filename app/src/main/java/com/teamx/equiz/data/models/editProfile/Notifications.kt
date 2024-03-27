package com.teamx.equiz.data.models.editProfile
import androidx.annotation.Keep

@Keep
data class Notifications(
    val email: Boolean,
    val push: Boolean,
    val sms: Boolean
)