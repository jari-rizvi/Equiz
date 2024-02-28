package com.teamx.equiz.data.models.wishlistdata

data class WishlistData(
    val `data`: List<Data>,
    val page: Int,
    val totalPages: Int,
    val totalUsersCount: Int
)