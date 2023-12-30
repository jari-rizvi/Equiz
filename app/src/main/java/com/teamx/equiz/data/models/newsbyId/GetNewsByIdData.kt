package com.teamx.equiz.data.models.newsbyId

import androidx.annotation.Keep

@Keep
data class GetNewsByIdData(
    val `data`: Data,
    val message: String
)