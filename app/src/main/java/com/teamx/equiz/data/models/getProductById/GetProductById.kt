package com.teamx.equiz.data.models.getProductById

import com.google.errorprone.annotations.Keep

@Keep
data class GetProductById(
    val `data`: Data,
    val message: String
)