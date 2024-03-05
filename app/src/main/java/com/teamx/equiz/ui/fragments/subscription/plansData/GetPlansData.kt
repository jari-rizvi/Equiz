package com.teamx.equiz.ui.fragments.subscription.plansData
import com.google.errorprone.annotations.Keep


@Keep
data class GetPlansData(
    val `data`: List<Data>,
    val totalPages: Int
)