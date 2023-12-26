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
import com.teamx.equiz.data.models.getorderData.GetOrdersData
import com.teamx.equiz.data.models.getwalletData.GetWalletData
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.models.modelUploadImages.ModelUploadImage
import com.teamx.equiz.data.models.newsData.NewsData
import com.teamx.equiz.data.models.newsbyId.GetNewsByIdData
import com.teamx.equiz.data.models.notificationData.NotificationData
import com.teamx.equiz.data.models.orderDetailData.OrderDetailData
import com.teamx.equiz.data.models.otpForgotData.OtpforgotData
import com.teamx.equiz.data.models.quizTitleData.QuizTitleData
import com.teamx.equiz.data.models.signupData.SignupData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.models.topWinnerData.TopWinnerData
import com.teamx.equiz.data.models.wishlistdata.WishlistData
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.StripeModel
import com.teamx.equiz.ui.fragments.singlequize.model.SingleQuizData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getUpcomingNews(
        @Query("upcoming") upcoming: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @GET(NetworkCallPoints.GET_NEWS_BY_ID)
    suspend fun getNewsById(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<GetNewsByIdData>

    @GET(NetworkCallPoints.GET_NEWS)
    suspend fun getCurrentNews(
        @Query("current") current: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @GET(NetworkCallPoints.GET_NEWS)
    suspend fun getRecentNews(
        @Query("recent") recent: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @GET(NetworkCallPoints.GET_COUPONS)
    suspend fun getCoupons(@Header("token") basicCredentials: String = "$TOKENER"): Response<CouponsData>


    @POST(NetworkCallPoints.ORDERS_CHECKOUT)
    suspend fun stripeDataMethod(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<StripeModel>

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


    @POST(NetworkCallPoints.RESULT_GAME)
    suspend fun resultGame(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SuccessData>

    @Multipart
    @POST(NetworkCallPoints.UPLOAD_IMGS)
    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ModelUploadImage>

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

    @GET(NetworkCallPoints.ME)
    suspend fun me(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<MeModel>

    @PUT(NetworkCallPoints.UPDATE_PROFILE)
    suspend fun updateProfile(
        @Body params: JsonObject,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<EditProfileData>


    @GET(NetworkCallPoints.QUIZ_TITLE)
    suspend fun quizTitle(
        @Query("country") country: String,
        @Query("topic") topic: String?,
        @Query("type") type: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<QuizTitleData>

    @GET(NetworkCallPoints.QUIZ_FIND)
    suspend fun quizFind(
        @Query("country") country: String,
        @Query("topic") topic: String?,
        @Query("type") type: String?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SingleQuizData>


    @GET(NetworkCallPoints.GET_ORDERS)
    suspend fun getOrders(
        @Query("orderStatus") orderStatus: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetOrdersData>

    @GET(NetworkCallPoints.GET_NOTIFICATION)
    suspend fun getNotifications(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NotificationData>

    @GET(NetworkCallPoints.GET_QUIZ_TITLE)
    suspend fun getQuizTitle(
        @Query("country") country: String,
        @Query("topic") topic: String,
        @Query("type") type: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<QuizTitleData>


    @GET(NetworkCallPoints.GET_ORDER_DETAILS)
    suspend fun getOrderDetail(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<OrderDetailData>

    @GET(NetworkCallPoints.GET_TOP_WINNERS)
    suspend fun getTopWinners(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<TopWinnerData>

}