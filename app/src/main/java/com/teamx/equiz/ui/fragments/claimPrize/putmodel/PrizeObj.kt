package com.teamx.equiz.ui.fragments.claimPrize.putmodel

data class PrizeObj(
    val nextRaffles: NextRaffles?,
    val prizeId: String,
    val raffleId: String,
    val type: String,
    val value: Int,
    val winnerId: String,
    val scratchcard_type: String?
)