package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class AddressX(
    val city: Any,
    val country: String,
    val line1: Any,
    val line2: Any,
    val postal_code: String,
    val state: Any
)