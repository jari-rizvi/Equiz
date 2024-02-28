package com.teamx.equiz.ui.fragments.claimPrize.model

import androidx.annotation.Keep

@Keep
data class ClaimPrizeModel(
    val claimed: Boolean,
    val prizeObj: PrizeObj
)