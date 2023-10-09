package com.teamx.equiz.constants

class NetworkCallPoints {
    companion object{
        const val API_GET_ALL_PRODUCTS ="/products";
        const val LOGIN_PHONE ="/api/auth/login";
        const val FOTGOTPASS_EMAIL ="/api/auth/forgetPassword";
        const val SIGNUP_EMAIL ="/api/auth/register";
        const val OTP_VERIFY ="/api/auth/verifyOtp/{uniqueID}";
        const val OTP_VERIFY_FORGOT ="/api/auth/otpReset/{uniqueID}";
        const val RESET_PASSWORD ="api/auth/passwordReset";
        const val EDIT_PROFILE ="/api/users/:id";
        const val GET_ALL_CATEGORIES ="/api/category";
        const val WISHLIST_DATA ="api/wishlist/find";
        const val ADD_WISHLIST ="api/wishlist/add";
        const val BANNERS_DATA ="api/banner";
        const val GET_PRODUCTS ="api/products";
        const val GET_PRODUCT_BY_ID ="api/products/find/{id}";
        const val ADD_TO_CART ="api/carts/add";
        const val GET_CART ="api/carts/find";
        const val DELETE_CART ="/api/carts/{productId}";
        const val GET_WALLET ="api/wallet/getWallet";
        const val GET_NEWS ="/api/news";

        var TOKENER: String? = ""

    }
}