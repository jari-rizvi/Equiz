package com.teamx.equiz.data.models.bankData

data class BankData(
    val `data`: Data,
    val expremental: Int,
    val message: String,
    val result: Int,
    val validations: List<Validation>
)