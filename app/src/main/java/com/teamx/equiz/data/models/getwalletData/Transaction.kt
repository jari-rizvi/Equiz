package com.teamx.equiz.data.models.getwalletData

import androidx.annotation.Keep

@Keep
data class Transaction(
    val __v: Int,
    val _id: String,
    val expiresAt: String,
    val points: Int,
    val timestamp: String,
    val type: String,
    val userId: String
)