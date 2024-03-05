package com.teamx.equiz.data.models.meModel
import com.google.errorprone.annotations.Keep


@Keep
data class User(
    val __v: Int,
    val _id: String,
    val addresses: List<Any>,
    val cart: List<Any>,
    val chances: Int,
    val country: String,
    val createdAt: String,
    val dateOfBirth: String,
    val email: String,
    val phone: String,
    val fcmToken: String,
    val id: String,
    val identification: String,
    val image: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isEmailValid: Boolean,
    val isPhoneValid: Boolean,
    val isPremium: Boolean,
    val isSubscribed: Boolean,
    val isValid: Boolean,
    val name: String,
    val notifications: Notifications,
    val password: String,
    val permission: List<Any>,
    val profileProgress: Int,
    val referralCode: String,
    val resetTokenExpiration: String,
    val roles: String,
    val score: Int,
    val social: List<Social>,
    val street: String,
    val stripProductId: StripProductId,
    val stripeID: String,
    val uniqueID: String,
    val updatedAt: String,
    val wallet: Any,
    val wishlist: List<Any>
)