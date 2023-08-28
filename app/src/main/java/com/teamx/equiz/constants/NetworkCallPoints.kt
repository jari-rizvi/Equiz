package com.teamx.equiz.constants

class NetworkCallPoints {
    companion object{
        const val API_GET_ALL_PRODUCTS ="/products";
        const val LOGIN_PHONE ="/api/auth/phoneLogin";
        const val FOTGOTPASS_EMAIL ="/api/auth/forgetPassword";
        const val SIGNUP_EMAIL ="/api/auth/register";
        const val OTP_VERIFY ="/api/auth/verifyOtp/{uniqueID}";
        const val OTP_VERIFY_FORGOT ="/api/auth/otpReset/{uniqueID}";
        const val RESET_PASSWORD ="api/auth/passwordReset";
        const val EDIT_PROFILE ="/api/users/:id";

        var TOKENER: String? = ""

    }
}