package com.teamx.equiz.ui.fragments.cards.modelcards

import androidx.annotation.Keep

@Keep
data class CardsModel(
    val default: Default?,
    val paymentMethod: List<PaymentMethod>
)