package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data

import androidx.annotation.Keep

@Keep
data class Subscription(
    val application: Any,
    val application_fee_percent: Any,
    val automatic_tax: AutomaticTax,
    val billing_cycle_anchor: Int,
    val billing_thresholds: Any,
    val cancel_at: Any,
    val cancel_at_period_end: Boolean,
    val canceled_at: Int,
    val cancellation_details: CancellationDetails,
    val collection_method: String,
    val created: Int,
    val currency: String,
    val current_period_end: Int,
    val current_period_start: Int,
    val customer: String,
    val days_until_due: Any,
    val default_payment_method: Any,
    val default_source: Any,
    val default_tax_rates: List<Any>,
    val description: Any,
    val discount: Any,
    val ended_at: Int,
    val id: String,
    val items: Items,
    val latest_invoice: String,
    val livemode: Boolean,
    val metadata: MetadataXXX,
    val next_pending_invoice_item_invoice: Any,
    val `object`: String,
    val on_behalf_of: Any,
    val pause_collection: Any,
    val payment_settings: PaymentSettings,
    val pending_invoice_item_interval: Any,
    val pending_setup_intent: Any,
    val pending_update: Any,
    val plan: PlanX,
    val quantity: Int,
    val schedule: Any,
    val start_date: Int,
    val status: String,
    val test_clock: Any,
    val transfer_data: Any,
    val trial_end: Any,
    val trial_settings: TrialSettings,
    val trial_start: Any
)