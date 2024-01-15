package com.teamx.equiz.constants

class NetworkCallPoints {
    companion object{
        const val API_GET_ALL_PRODUCTS ="products"
        const val LOGIN_PHONE ="api/auth/login"
        const val FOTGOTPASS_EMAIL ="api/auth/forgetPassword"
        const val SIGNUP_EMAIL ="api/auth/register"
        const val OTP_VERIFY ="api/auth/verifyOtp"
        const val OTP_VERIFY_FORGOT ="api/auth/otpReset/{uniqueID}"
        const val RESEND_OTP ="/api/auth/resendOTP"
        const val ME = "api/users/find"
        const val UNSUB = "api/users/find"
        const val DELETE_USER = "api/users/remove"
        const val UPDATE_PROFILE = "api/users/update"


        const val QUIZ_TITLE = "api/quiz/tile"
        const val QUIZ_FIND = "api/quiz/find"
        const val QUIZ_RESULT = "api/quiz/point"


        const val RESET_PASSWORD = "api/auth/passwordReset"
        const val CHANGE_PASSWORD = "api/auth/changePassword"
        const val RESULT_GAME = "api/game/progress"
        const val GET_ALL_CATEGORIES = "api/category"
        const val WISHLIST_DATA = "api/wishlist/find"

        const val ADD_WISHLIST = "api/wishlist/add"
        const val DELETE_WISHLIST = "api/wishlist/delete"

        const val NOTIFY_FCM = "api/notification/add/"
        const val BANNERS_DATA = "api/news/newsbanner"
        const val BANNERS_DATA2 = "api/news/"

        const val GET_CHANCES = "/api/chance/getChance"


        const val GET_PRODUCTS = "api/products/"
        const val GET_PRODUCT_BY_ID = "api/products/find/{id}"
        const val ADD_TO_CART = "api/carts/add"
        const val GET_CART = "api/carts/find"

        const val DELETE_CART = "api/carts/{productId}"
        const val UPDATE_CART = "api/carts/update"


        const val GET_WALLET = "api/wallet/getWallet"
        const val GET_NEWS = "api/news/"
        const val GET_NEWS_BY_ID = "api/news/find/{id}"
        const val GET_COUPONS ="api/coupon/find"
        const val ORDERS_CHECKOUT ="api/orders/checkout"
        const val GET_ORDERS ="api/orders/find"
        const val GET_ORDERS2 ="api/orders/find/:userId"



        const val GET_ORDER_DETAILS ="api/orders/order/{id}"
        const val GET_TOP_WINNERS ="api/game/all"
        const val GET_QUIZ_TITLE ="api/quiz/tile"
        const val GET_NOTIFICATION ="api/notification/find"
        const val GET_PLAN ="api/payment/getPlan"
        const val SUB_PLAN ="api/payment/subscription"
        const val CREATE_ORDER ="api/orders/add"


        const val ADD_TOPUP ="api/wallet/addAmount"
        const val COLLECT_PRIZE ="api/raffle/myWinner"



        const val UPLOAD_IMGS = "api/users/upload"
        const val GET_ADDRESS_LIST = "api/users/findAddress"
        const val ADD_ADDRESS = "/api/users/addAddress"
        const val DELETE_ADDRESS ="api/users/removeAddress/{addressId}"
        const val GET_ADDRESS_BY_ID ="api/users/findAddessId/{id}"
        const val UPDATE_ADDRESS ="api/users/updateAddress"



        var TOKENER: String? = ""

    }
}