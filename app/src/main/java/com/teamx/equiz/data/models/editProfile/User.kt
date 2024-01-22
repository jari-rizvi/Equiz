package com.teamx.equiz.data.models.editProfile

import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val apartment: String,
    val dateOfBirth: String?,
    val cart: List<Any>,
    val city: String,
    val country: String,
    val isAdmin: Boolean,
    val isPremium: Boolean,
    val image: String,
    val isValid: Boolean,
    val name: String,
    val password: String,
    val email: String,
    val phone: String,
    val referralCode: String,
    val registrationDate: String,
    val resetTokenExpiration: String,
    val street: String,
    val uniqueID: String,
    val wallet: Double,
    val zip: String
)