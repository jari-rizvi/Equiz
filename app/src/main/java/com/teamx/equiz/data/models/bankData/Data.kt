package com.teamx.equiz.data.models.bankData
import androidx.annotation.Keep

@Keep
data class Data(
    val bank_data: BankDataX,
    val country_iban_example: String,
    val iban: String,
    val iban_data: IbanData,
    val message: String,
    val valid: Boolean
)