package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class PaymentSettings(
    val payment_method_options: Any,
    val payment_method_types: Any,
    val save_default_payment_method: String
)