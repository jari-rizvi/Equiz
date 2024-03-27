package com.teamx.equiz.ui.fragments.claimPrize.putmodel
import androidx.annotation.Keep

@Keep
data class NextRaffles(
    val id: String,
    val name: String,
    val quizId: List<Any>
)