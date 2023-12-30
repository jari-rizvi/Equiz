package com.teamx.equiz.data.models.addtocart

import androidx.annotation.Keep

@Keep
data class AddtoCartData(
    val `data`: List<Data>,
    val message: String
)