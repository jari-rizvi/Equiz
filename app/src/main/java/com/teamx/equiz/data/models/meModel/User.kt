package com.teamx.equiz.data.models.meModel

import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val apartment: String,
    val cart: List<Any>,
    val chances: Int,
    val city: String,
    val country: String,
    val createdAt: String,
    val dateOfBirth: String,
    val email: String,
    val fcmToken: String,
    val image: String,
    val isAdmin: Boolean,
    val isPremium: Boolean,
    val isValid: Boolean,
    val isEmailValid : Boolean,
    val isPhoneValid : Boolean,
    val name: String,
    val password: String,
    val permission: List<Any>,
    val phone: String,
    val referralCode: String,
    val registrationDate: String,
    val resetTokenExpiration: String,
    val roles: String,
    val score: Double,
    val street: String,
    val uniqueID: String,
    val updatedAt: String,
    val profileProgress: Int,
    val wallet: Double,
    val wishlist: List<Any>,
    val zip: String
)