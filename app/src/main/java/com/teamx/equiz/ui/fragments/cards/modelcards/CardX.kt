package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class CardX(
    val brand: String,
    val checks: ChecksX,
    val country: String,
    val exp_month: Int,
    val exp_year: Int,
    val fingerprint: String,
    val funding: String,
    val generated_from: Any,
    val last4: String,
    val networks: Networks,
    val three_d_secure_usage: ThreeDSecureUsage,
    val wallet: Any
)