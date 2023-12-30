package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother

import androidx.annotation.Keep

@Keep
data class Card(
    val installments: Any,
    val mandate_options: Any,
    val network: Any,
    val request_three_d_secure: String
)