package com.teamx.equiz.data.models.loginData

import androidx.annotation.Keep

@Keep
data class LoginData(
    val token: String,
    var user: User?
)