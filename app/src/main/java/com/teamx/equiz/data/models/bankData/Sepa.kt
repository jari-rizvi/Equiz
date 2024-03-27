package com.teamx.equiz.data.models.bankData

data class Sepa(
    val sepa_b2b: String,
    val sepa_card_clearing: String,
    val sepa_credit_transfer: String,
    val sepa_direct_debit: String,
    val sepa_sdd_core: String
)