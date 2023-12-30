package com.teamx.equiz.data.models.wishlistdata

import androidx.annotation.Keep

@Keep
data class WishlistData(
    val `data`: List<Data>,
    val message: String
)