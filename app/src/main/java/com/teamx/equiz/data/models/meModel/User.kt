package com.teamx.equiz.data.models.meModel

import com.teamx.equiz.data.models.editProfile.IdentityDocument
import androidx.annotation.Keep

@Keep
data class User(
    val __v: Int,
    val _id: String,
    val addresses: List<Addresse>,
    val bank: Bank?,
    val cart: List<Any>,
    val chances: Double,
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
    val identityDocuments: List<IdentityDocument>,
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
    val score: Double,
    val social: List<Social>,
    val street: String,
    val stripProductId: StripProductId?,
    val subscriberId: SubscriberId?,
    val stripeID: String,
    val uniqueID: String,
    val updatedAt: String,
    val wallet: Double,
    val wishlist: List<Any>
)