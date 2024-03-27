package com.teamx.equiz.data.models.editProfile
import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val addresses: List<Addresse>,
    val cart: List<Any>,
    val chances: Int,
    val country: String,
    val createdAt: String,
    val dateOfBirth: String?,
    val email: String?,
    val fcmToken: String,
    val id: String,
    val image: String?,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isEmailValid: Boolean,
    val isPhoneValid: Boolean,
    val isPremium: Boolean,
    val isSubscribed: Boolean,
    val isValid: Boolean,
    val name: String?,
    val notifications: Notifications,
    val password: String,
    val permission: List<Any>,
    val phone: String?,
    val profileProgress: Int,
    val referralCode: String,
    val resetTokenExpiration: String,
    val roles: String,
    val score: Int,
    val social: List<Social>,
    val street: String,
    val stripeID: String,
    val subscriptionID: String,
    val uniqueID: String,
    val updatedAt: String,
    val wallet: Double,
    val wishlist: List<Wishlist>
)