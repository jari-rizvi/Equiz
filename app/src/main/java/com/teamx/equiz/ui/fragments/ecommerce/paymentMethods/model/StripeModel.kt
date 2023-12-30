package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model

import androidx.annotation.Keep


@Keep
data class StripeModel(
    val client_secret: String,
    val message: String
)