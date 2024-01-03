package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class PlanX(
    val active: Boolean,
    val aggregate_usage: Any,
    val amount: Int,
    val amount_decimal: String,
    val billing_scheme: String,
    val created: Int,
    val currency: String,
    val id: String,
    val interval: String,
    val interval_count: Int,
    val livemode: Boolean,
    val metadata: MetadataXXX,
    val nickname: Any,
    val `object`: String,
    val product: String,
    val tiers_mode: Any,
    val transform_usage: Any,
    val trial_period_days: Any,
    val usage_type: String
)