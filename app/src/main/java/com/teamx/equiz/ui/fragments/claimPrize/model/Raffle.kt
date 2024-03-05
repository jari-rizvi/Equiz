package com.teamx.equiz.ui.fragments.claimPrize.model
import androidx.annotation.Keep

@Keep
data class Raffle(
    val _id: String,
    val name: String,
    var quiz: List<String>?,
    var quizId: List<String>,
    var isSelected: Boolean?
)