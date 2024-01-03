package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class Data(
    val billing_thresholds: Any,
    val created: Int,
    val id: String,
    val metadata: MetadataXXX,
    val `object`: String,
    val plan: PlanX,
    val price: Price,
    val quantity: Int,
    val subscription: String,
    val tax_rates: List<Any>
)