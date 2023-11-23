package com.teamx.equiz.data.remote


import com.google.gson.JsonObject
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.data.models.addtocart.AddtoCartData
import com.teamx.equiz.data.models.addtowishlist.AddToWishlistData
import com.teamx.equiz.data.models.bannerData.BannerData
import com.teamx.equiz.data.models.categoriesData.GetAllCategoriesData
import com.teamx.equiz.data.models.coupons.CouponsData
import com.teamx.equiz.data.models.editProfile.EditProfileData
import com.teamx.equiz.data.models.forgotpassData.ForgotPassData
import com.teamx.equiz.data.models.getProductById.GetProductByIdData
import com.teamx.equiz.data.models.getProducts.GetProductData
import com.teamx.equiz.data.models.getcart.GetCartData
import com.teamx.equiz.data.models.getwalletData.GetWalletData
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.models.newsdaya.GetNewsData
import com.teamx.equiz.data.models.otpForgotData.OtpforgotData
import com.teamx.equiz.data.models.signupData.SignupData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.models.wishlistdata.WishlistData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    @GET(NetworkCallPoints.WISHLIST_DATA)
    suspend fun getWishlist(@Header("token") basicCredentials: String = "$TOKENER"): Response<WishlistData>

    @GET(NetworkCallPoints.BANNERS_DATA)
    suspend fun getBanners(@Header("token") basicCredentials: String = "$TOKENER"): Response<BannerData>

    @GET(NetworkCallPoints.GET_PRODUCTS)
    suspend fun getProducts(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetProductData>

    @GET(NetworkCallPoints.GET_CART)
    suspend fun getCart(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetCartData>

    @GET(NetworkCallPoints.GET_WALLET)
    suspend fun getWallet(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetWalletData>

    @GET(NetworkCallPoints.GET_NEWS)
    suspend fun getNews(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetNewsData>
 @GET(NetworkCallPoints.GET_COUPONS)
    suspend fun getCoupons(@Header("token") basicCredentials: String = "$TOKENER"): Response<CouponsData>

    @GET(NetworkCallPoints.GET_PRODUCT_BY_ID)
    suspend fun getProductById(
        @Path("id") Productid: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetProductByIdData>

    @GET(NetworkCallPoints.GET_ALL_CATEGORIES)
    suspend fun getCategories(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetAllCategoriesData>

    @POST(NetworkCallPoints.LOGIN_PHONE)
    suspend fun loginPhone(@Body params: JsonObject?): Response<LoginData>

    @POST(NetworkCallPoints.FOTGOTPASS_EMAIL)
    suspend fun forgotpass(@Body params: JsonObject?): Response<ForgotPassData>

    @POST(NetworkCallPoints.SIGNUP_EMAIL)
    suspend fun Signup(@Body params: JsonObject?): Response<SignupData>

    @POST(NetworkCallPoints.ADD_TO_CART)
    suspend fun AddToCart(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<AddtoCartData>

    @POST(NetworkCallPoints.ADD_WISHLIST)
    suspend fun AddToWishList(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<AddToWishlistData>

    @POST(NetworkCallPoints.RESET_PASSWORD)
    suspend fun resetPass(@Body params: JsonObject?): Response<SuccessData>

    @PUT(NetworkCallPoints.EDIT_PROFILE)
    suspend fun editProfile(
        @Body params: JsonObject?,
        @Header("Authorization") basicCredentials: String = "Bearer $TOKENER"
    ): Response<EditProfileData>


    @GET(NetworkCallPoints.OTP_VERIFY)
    suspend fun otpVerify(
        @Path("uniqueID") uniqueID: String
    ): Response<LoginData>

    @HTTP(method = "DELETE", path = NetworkCallPoints.DELETE_CART)
    suspend fun deleteCart(
        @Path("productId") productId: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SuccessData>


    @GET(NetworkCallPoints.OTP_VERIFY_FORGOT)
    suspend fun otpVerifyForgot(
        @Path("uniqueID") uniqueID: String
    ): Response<OtpforgotData>

}