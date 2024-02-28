package com.teamx.equiz.ui.fragments.claimPrize.model
import androidx.annotation.Keep

@Keep
data class Raffle(
    val _id: String,
    val name: String,
    val quiz: List<String>
)