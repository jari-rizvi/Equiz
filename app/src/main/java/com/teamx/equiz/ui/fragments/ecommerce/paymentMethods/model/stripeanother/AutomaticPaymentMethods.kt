package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother
import androidx.annotation.Keep

@Keep
data class AutomaticPaymentMethods(
    val allow_redirects: String,
    val enabled: Boolean
)