package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class BillingDetailsX(
    val address: AddressX,
    val email: Any,
    val name: Any,
    val phone: Any
)