package com.teamx.equiz.data.models.editProfile

data class Notifications(
    val email: Boolean,
    val push: Boolean,
    val sms: Boolean
)