package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class Default(
    val billing_details: BillingDetails,
    val card: Card,
    val created: Int,
    val customer: String,
    val id: String?,
    val livemode: Boolean,
    val metadata: Metadata,
    val `object`: String,
    val type: String
)