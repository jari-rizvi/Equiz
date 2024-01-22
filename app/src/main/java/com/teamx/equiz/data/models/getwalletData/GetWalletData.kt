package com.teamx.equiz.data.models.getwalletData

import androidx.annotation.Keep

@Keep
data class GetWalletData(
    val `data`: Double,
    val recentExpiration: List<Any>,
    val transactions: List<Transaction>
)