package com.teamx.equiz.ui.fragments.claimPrize.model

data class PrizeObj(
    val prize: String,
    val prizeId: String,
    val raffleId: String,
    val raffles: List<Raffle>?,
    val type: String,
    val scratchcard_type: String?,
    val value: Int,
    val winnerId: String
)