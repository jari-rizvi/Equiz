package com.teamx.equiz.data.models.loginData

data class User(
    val __v: Int,
    val _id: String,
    val apartment: String,
    val cart: List<Any>,
    val chances: Int,
    val city: String,
    val country: String,
    val dateOfBirth: Any,
    val isAdmin: Boolean,
    val isPremium: Boolean,
    val isValid: Boolean,
    val name: String,
    val password: String,
    val phone: String,
    val referralCode: String,
    val registrationDate: String,
    val resetTokenExpiration: String,
    val street: String,
    val uniqueID: String,
    val updatedAt: String,
    val wallet: Int,
    val wishlist: List<Wishlist>,
    val zip: String
)