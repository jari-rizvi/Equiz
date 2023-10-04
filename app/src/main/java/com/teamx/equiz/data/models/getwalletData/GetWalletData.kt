package com.teamx.equiz.data.models.getwalletData

data class GetWalletData(
    val `data`: Int,
    val recentExpiration: List<Any>,
    val transactions: List<Transaction>
)