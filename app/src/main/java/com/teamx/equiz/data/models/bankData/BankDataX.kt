package com.teamx.equiz.data.models.bankData
import androidx.annotation.Keep

@Keep
data class BankDataX(
    val bank_code: String,
    val bic: String,
    val city: String,
    val name: String,
    val zip: String
)