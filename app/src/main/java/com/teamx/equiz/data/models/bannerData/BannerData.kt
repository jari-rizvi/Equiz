package com.teamx.equiz.data.models.bannerData

import androidx.annotation.Keep

@Keep
data class BannerData(
    val `data`: List<Data>,
    val message: String
)