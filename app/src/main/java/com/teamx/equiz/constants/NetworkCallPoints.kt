package com.teamx.equiz.constants

class NetworkCallPoints {
    companion object{
        const val API_GET_ALL_PRODUCTS ="/products";
        const val LOGIN_PHONE ="/api/auth/phoneLogin";
        const val FOTGOTPASS_EMAIL ="/api/auth/forgetPassword";
        const val SIGNUP_EMAIL ="/api/auth/register";
        const val OTP_VERIFY ="/api/auth/verifyOtp/{uniqueID}";
        const val OTP_VERIFY_FORGOT ="/api/auth/otpReset/{uniqueID}";
        const val RSET_PASSWORD ="api/auth/passwordReset";
    }
}