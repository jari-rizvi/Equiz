package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother

import androidx.annotation.Keep

@Keep
data class Checkout(
    val `data`: Data,
    val ephemeralKey: EphemeralKey,
    val status: Int
)