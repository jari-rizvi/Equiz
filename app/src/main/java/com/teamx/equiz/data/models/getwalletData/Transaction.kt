package com.teamx.equiz.data.models.getwalletData

import androidx.annotation.Keep

@Keep
data class Transaction(
    val __v: Int,
    val _id: String,
    val expiresAt: String,
    val isActive: Boolean,
    val pointType: String,
    val points: Double,
    val timestamp: String,
    val transactionType: Boolean,
    val userId: String
)