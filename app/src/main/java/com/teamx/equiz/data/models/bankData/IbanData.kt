package com.teamx.equiz.data.models.bankData
import com.google.errorprone.annotations.Keep

@Keep
data class IbanData(
    val BBAN: String,
    val account_number: String,
    val bank_code: String,
    val branch: String,
    val checksum: String,
    val country: String,
    val country_code: String,
    val country_iban_format_as_regex: String,
    val country_iban_format_as_swift: String,
    val national_checksum: String,
    val sepa_country: Boolean
)