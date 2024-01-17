package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class ChecksX(
    val address_line1_check: Any,
    val address_postal_code_check: String,
    val cvc_check: String
)