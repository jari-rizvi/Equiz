package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class Recurring(
    val aggregate_usage: Any,
    val interval: String,
    val interval_count: Int,
    val trial_period_days: Any,
    val usage_type: String
)