package com.teamx.equiz.ui.fragments.cards.modelcards
import androidx.annotation.Keep

@Keep
data class Networks(
    val available: List<String>,
    val preferred: Any
)