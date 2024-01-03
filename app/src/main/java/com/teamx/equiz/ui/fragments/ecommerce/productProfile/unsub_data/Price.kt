package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class Price(
    val active: Boolean,
    val billing_scheme: String,
    val created: Int,
    val currency: String,
    val custom_unit_amount: Any,
    val id: String,
    val livemode: Boolean,
    val lookup_key: Any,
    val metadata: MetadataXXX,
    val nickname: Any,
    val `object`: String,
    val product: String,
    val recurring: Recurring,
    val tax_behavior: String,
    val tiers_mode: Any,
    val transform_quantity: Any,
    val type: String,
    val unit_amount: Int,
    val unit_amount_decimal: String
)