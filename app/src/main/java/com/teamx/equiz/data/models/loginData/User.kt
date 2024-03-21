package com.teamx.equiz.data.models.loginData

import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val apartment: String,
    val cart: List<Any>,
    val chances: Int,
    val profileProgress: Int?,
    val city: String,
    val country: String,
    val dateOfBirth: Any,
    val isAdmin: Boolean,
    val isPremium: Boolean,
    val isValid: Boolean,
    val name: String,
    val password: String,
    val phone: String?,
    val email: String?,
    val referralCode: String,
    val registrationDate: String,
    val resetTokenExpiration: String,
    val street: String,
    val uniqueID: String,
    var image: String?,
    val updatedAt: String,
    val wallet: Double,
    val wishlist: List<Wishlist>,
    val zip: String
)