package com.teamx.equiz.data.models.getProductById

import androidx.annotation.Keep

@Keep
data class GetProductByIdData(
    val `data`: Data,
    val message: String
)