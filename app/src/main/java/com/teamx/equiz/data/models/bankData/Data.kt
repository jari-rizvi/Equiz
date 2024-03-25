package com.teamx.equiz.data.models.bankData

data class Data(
    val bank: Bank,
    val bank_account: String,
    val bban: String,
    val country_code: String,
    val country_name: String,
    val currency_code: String,
    val iso_alpha3: String,
    val sepa: Sepa,
    val sepa_member: String
)