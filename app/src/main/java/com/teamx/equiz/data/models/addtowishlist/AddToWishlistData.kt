package com.teamx.equiz.data.models.addtowishlist

import androidx.annotation.Keep

@Keep
data class AddToWishlistData(
    val `data`: List<Data>,
    val message: String
)