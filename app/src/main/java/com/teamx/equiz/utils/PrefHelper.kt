package com.teamx.equiz.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

class PrefHelper private constructor() {

    companion object {
        private val sharePref = PrefHelper()
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var sharedPreferencesUser: SharedPreferences

        private val _lat = "_lat"
        private val _long = "_long_"
        private val PLACE_OBJ = "place_obj"
        private val PAYTYPE = "pay_type"
        private val SPECIAL_INSTRUCATIONS = "special_instructions"
        private val SAVE_COUPONTOKEN = "save_token"
        private val MERCHANT_ID = "merchant_id"
        private val ORDER_ID = "order_id"
        private val USER_ID = "user_id"
        private val USER_PREM = "user_prem"
        private val WALLET_AMOUNT = "wallet_amount"
        private const val dbCardP = "dbCardP"
        private val LANGTYPE = "lang_type"
        private val CARPLATE = "car_plate"
        private const val shippingConst = "_shippingAddress"
        private const val favouriteShop = "_favouriteShop"
        private const val favouriteProduct = "_favouriteProduct"
        private const val shippingConst2 = "_shippingAddress2"
        private const val carConst = "_carConstant"
        private const val userCredentials = "_userCredential"
        private const val STRIPE_ID = "stripe"
        private const val STRIPE_Name = "stripe_name"
        private const val User_Email = "user_email"
        private const val NOTIFICATION_ENABLE = "notificationEnable"


        fun getInstance(context: Context): PrefHelper {
            if (!::sharedPreferences.isInitialized) {
                synchronized(PrefHelper::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }


        fun getUSerInstance(context: Context): PrefHelper {
            if (!::sharedPreferencesUser.isInitialized) {
                synchronized(PrefHelper::class.java) {
                    if (!::sharedPreferencesUser.isInitialized) {
                        sharedPreferencesUser = context.getSharedPreferences(
                            "context.packageName", Activity.MODE_PRIVATE
                        )
                    }
                }
            }
            return sharePref
        }

    }


    val isUsed: Boolean get() = sharedPreferences.getBoolean("isused", false)
    fun setIsUser(redeem: Boolean) {
        sharedPreferences.edit().putBoolean("isused", redeem).apply()
    }

    val paymentMathod: String? get() = sharedPreferences.getString(PAYTYPE, "0")


    val payment: String? get() = sharedPreferences.getString(PAYTYPE, "ONLINE_PAYMENTS")

    fun savePaymentMethod(pay_type: String) {
//        sharedPreferences.edit().putString(PAYTYPE, pay_type).apply()
        if (pay_type.contains("card", true) || pay_type.contains("google", true)) {
            sharedPreferences.edit().putString(PAYTYPE, "ONLINE_PAYMENTS").apply()
        } else if (pay_type.contains("paypal", true)) {
            sharedPreferences.edit().putString(PAYTYPE, "PAYPAL").apply()
        } else if (pay_type.contains("arrival", true)) {
            sharedPreferences.edit().putString(PAYTYPE, "PAY_ON_ARRIVAL").apply()
        } else if (pay_type.contains("delivery", true)) {
            sharedPreferences.edit().putString(PAYTYPE, "CASH_ON_DELIVERY").apply()
        }

    }

    val checkEnableNotification: Boolean?
        get() = sharedPreferences.getBoolean(
            NOTIFICATION_ENABLE, false
        )

    fun enableNotification(redeem: Boolean) {
        sharedPreferences.edit().putBoolean(NOTIFICATION_ENABLE, redeem).apply()
    }

    val order_id: String? get() = sharedPreferences.getString(ORDER_ID, "")

    fun saveOrderId(order_id: String) {
        sharedPreferences.edit().putString(ORDER_ID, order_id).apply()

    }

    val setUserId: String? get() = sharedPreferences.getString(USER_ID, "")

    fun saveUerId(user_id: String) {
        sharedPreferences.edit().putString(USER_ID, user_id).apply()

    }

    val isPremieum: Boolean get() = sharedPreferences.getBoolean(USER_PREM, false)

    fun savePremium(premium: Boolean) {
        sharedPreferences.edit().putBoolean(USER_PREM, premium).apply()

    }

    val setWalletAmount: String? get() = sharedPreferences.getString(WALLET_AMOUNT, "")

    fun saveWalletAmount(wallet_amount: String) {
        sharedPreferences.edit().putString(WALLET_AMOUNT, wallet_amount).apply()

    }


    fun getDebitCardPointer(): String? {

        return sharedPreferences.getString(dbCardP, "0")
    }


    fun setDebitCardPointer(str: String?) {
        sharedPreferences.edit().putString(dbCardP, str).apply()
    }


//    val payment: Int get() = sharedPreferences.getInt(PLACE_OBJ, 1)
//
//    fun savePayment(placeObjStr: Int) {
//        sharedPreferences.edit().putInt(PLACE_OBJ, placeObjStr).apply()
//    }
//
//    fun removePlaceObj() {
//        sharedPreferences.edit().remove(PLACE_OBJ).apply()
//    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveLANGTYPE(lang_type: String) {
        sharedPreferences.edit().putString(LANGTYPE, lang_type).apply()
    }

    val langType: String?
        get() = sharedPreferences.getString(LANGTYPE, "en")


    fun setInitFav(key: String, filterItem: Boolean) {

        sharedPreferences.edit().putBoolean(key, filterItem).apply()
    }

    /*
    * key produt id
    * value favourite
    *
    *
    * */
    fun getInitFav(key: String): Boolean {
        try {
            return if (sharedPreferences.getBoolean(key, false)) {
                true
            } else {
                setInitFav(key, false)
                sharedPreferences.getBoolean(key, false)
            }
        } catch (e: java.lang.Exception) {
            setInitFav(key, false)
            return sharedPreferences.getBoolean(key, false)
        }
    }

//    fun saveCarPlate(car_plate: String) {
//        sharedPreferences.edit().putString(CARPLATE, carPlate).apply()
//    }
//    val carPlate: String?
//        get() = sharedPreferences.getString(CARPLATE, "")

    fun setShippingAddress(shippingAddress: ShippingAddress?) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(shippingConst, str).apply()
    }

    fun setShippingAddress2(shippingAddress: ShippingAddress?) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(shippingConst2, str).apply()
    }

    fun getShippingAddress(): ShippingAddress? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(shippingConst, ""), ShippingAddress::class.java
        )
    }

    fun getShippingAddress2(): ShippingAddress? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(shippingConst2, ""), ShippingAddress::class.java
        )
    }


    @Keep
    data class CarDetails(var carPlate: String, var carImg: String)


    @Keep
    data class UserCredential(var email: String?,var phone: String?, var Password: String, var isDetection : Boolean)

    fun setCarDetails(carDetails: CarDetails?) {
        val gson = Gson()
        val str = gson.toJson(carDetails)
        sharedPreferences.edit().putString(carConst, str).apply()
    }

    fun getCarDetails(): CarDetails? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(carConst, ""), CarDetails::class.java
        )
    }


    fun setCredentials(userCredential: UserCredential?) {
        val gson = Gson()
        val str = gson.toJson(userCredential)
        sharedPreferencesUser.edit().putString(userCredentials, str).apply()
    }

    fun getCredentials(): UserCredential? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferencesUser.getString(userCredentials, ""), UserCredential::class.java
        )
    }


    val getStripe: String?
        get() = sharedPreferences.getString(STRIPE_ID, "")


    fun savaStripe(slectStripe: String) {
        sharedPreferences.edit().putString(STRIPE_ID, slectStripe).apply()
    }

    val getStripeName: String?
        get() = sharedPreferences.getString(STRIPE_Name, "")


    fun savaStripeName(slectStripe: String) {
        sharedPreferences.edit().putString(STRIPE_Name, slectStripe).apply()
    }

    val getPhoneNumberTxt: String?
        get() = sharedPreferences.getString("phoneNumberTxt", "")


    fun savePhoneNumberTxt(slectStripe: String) {
        sharedPreferences.edit().putString("phoneNumberTxt", slectStripe).apply()
    }

    val getCountry: String?
        get() = sharedPreferences.getString("country", "")


    fun setCountry(selectCountry: String) {
        sharedPreferences.edit().putString("country", selectCountry).apply()
    }


    fun setFavouriteShop(shippingAddress: List<String>) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(favouriteShop, str).apply()
    }

    fun getFavouriteShop(): UserFavouriteShop? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(favouriteShop, "[]"),
            UserFavouriteShop::class.java
        )
    }

    fun setFavouriteProduct(shippingAddress: List<String>) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(favouriteProduct, str).apply()
    }

    fun getFavouriteProduct(): UserFavouriteProduct? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(favouriteProduct, "[]"), UserFavouriteProduct::class.java
        )
    }


    @Keep
    class UserFavouriteProduct : ArrayList<String>()


    @Keep
    class UserFavouriteShop : ArrayList<String>()


    @Keep
    data class ShippingAddress(
        val _id: String,
        val city: String,
        val country: String,
        val state: String,
        val street_address: String,
        val zip: String? = "00000"
    )


    //
    fun currentLatLng(): LatLng {
        val lat = sharedPreferences.getFloat(_lat, 0.0f).toDouble()
        val long = sharedPreferences.getFloat(_long, 0.0f).toDouble()
        return LatLng(lat, long)
    }

    fun currentLatLng(currentLatLng: LatLng) {
        val lat = currentLatLng.latitude.toFloat()
        val long = currentLatLng.longitude.toFloat()
        sharedPreferences.edit().putFloat(_lat, lat).apply()
        sharedPreferences.edit().putFloat(_long, long).apply()

    }


    //


}