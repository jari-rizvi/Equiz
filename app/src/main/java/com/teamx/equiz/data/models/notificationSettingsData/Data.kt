package com.teamx.equiz.data.models.notificationSettingsData
import androidx.annotation.Keep

@Keep
data class Data(
    val email: Boolean,
    val push: Boolean,
    val sms: Boolean
)