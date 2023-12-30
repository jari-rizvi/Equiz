package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother
import androidx.annotation.Keep

@Keep
data class StripeCheckoutModelData(
    val checkout: Checkout,
    val message: String
)