package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class PaymentMethod(
    val billing_details: BillingDetailsX,
    val card: CardX,
    val created: Int,
    val customer: String,
    var default: Boolean,
    val id: String,
    val livemode: Boolean,
    val metadata: Metadata,
    val `object`: String,
    val type: String
)