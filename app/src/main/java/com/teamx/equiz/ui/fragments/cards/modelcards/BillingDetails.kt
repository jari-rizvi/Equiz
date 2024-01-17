package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class BillingDetails(
    val address: Address,
    val email: Any,
    val name: Any,
    val phone: Any
)