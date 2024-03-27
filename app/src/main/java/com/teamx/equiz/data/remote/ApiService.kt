package com.teamx.equiz.data.remote


import com.google.gson.JsonObject
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.data.models.ResendOtpData
import com.teamx.equiz.data.models.addressbyid.GetAddressById
import com.teamx.equiz.data.models.addtocart.AddtoCartData
import com.teamx.equiz.data.models.addtowishlist.AddToWishlistData
import com.teamx.equiz.data.models.bankData.BankData
import com.teamx.equiz.data.models.bannerData.bannews.BanNews
import com.teamx.equiz.data.models.bannerData.news_banner.NewsBanner
import com.teamx.equiz.data.models.coupons.CouponsData
import com.teamx.equiz.data.models.delete_wishlist.DeleteWishListData
import com.teamx.equiz.data.models.editProfile.EditProfileData
import com.teamx.equiz.data.models.exchangeRate.ExchangeRateData
import com.teamx.equiz.data.models.forgotpassData.ForgotPassData
import com.teamx.equiz.data.models.getPlan.GerPlanData
import com.teamx.equiz.data.models.getProductById.GetProductById
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
import com.teamx.equiz.data.models.notificationSettingsData.NotificationSettingsData
import com.teamx.equiz.data.models.orderDetailData.OrderDetailData
import com.teamx.equiz.data.models.otpForgotData.OtpforgotData
import com.teamx.equiz.data.models.quizTitleData.QuizTitleData
import com.teamx.equiz.data.models.quizbyidd.GetQuizById
import com.teamx.equiz.data.models.scratchData.ScratchImgData
import com.teamx.equiz.data.models.signupData.SignupData
import com.teamx.equiz.data.models.staticsData.StaticsData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.models.sucessData.gamesuccess.GameObj
import com.teamx.equiz.data.models.topWinnerData.TopWinnerData
import com.teamx.equiz.data.models.uploadImgDoc.UploadImgDoc
import com.teamx.equiz.data.models.wishlistdata.WishlistData
import com.teamx.equiz.ui.activity.mainActivity.activeusermodel.ModelActiveUser
import com.teamx.equiz.ui.fragments.Auth.datanotify.DataFCMModel
import com.teamx.equiz.ui.fragments.address.dataclasses.AddressOrderCreate
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.GetAddressListData
import com.teamx.equiz.ui.fragments.cards.modelcards.CardsModel
import com.teamx.equiz.ui.fragments.chances.data.ChancesModelData
import com.teamx.equiz.ui.fragments.claimPrize.model.ClaimPrizeModel
import com.teamx.equiz.ui.fragments.collectPrice.data.CollectDataModel
import com.teamx.equiz.ui.fragments.ecommerce.data.CategoryEcomData
import com.teamx.equiz.ui.fragments.ecommerce.home.datanews.NewsImagesDataModel
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother.StripeCheckoutModelData
import com.teamx.equiz.ui.fragments.profile.data.DELETEUSERModel
import com.teamx.equiz.ui.fragments.quizresult.data.QuizResultDataModel
import com.teamx.equiz.ui.fragments.subscription.buySubscription.BuySubscription
import com.teamx.equiz.ui.fragments.subscription.catPlanById.CatPlanById
import com.teamx.equiz.ui.fragments.subscription.catPlansData.CatPlanData
import com.teamx.equiz.ui.fragments.subscription.data.SubData
import com.teamx.equiz.ui.fragments.topup.data.TopUpModelData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(NetworkCallPoints.WISHLIST_DATA)
    suspend fun getWishlist(@Header("token") basicCredentials: String = "$TOKENER"): Response<WishlistData>

    @GET(NetworkCallPoints.BANNERS_DATA)
    suspend fun getBanners(
        @Query("isActive") isActive: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NewsBanner>

    @GET(NetworkCallPoints.BANNERS_DATA)
    suspend fun getBannersDashboard(
        @Query("country") country: String = "",
        @Query("type") type: String = "home",
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NewsImagesDataModel>

    @GET(NetworkCallPoints.BANNERS_DATA)
    suspend fun getBannersEco(
        @Query("country") country: String = "",
        @Query("type") type: String = "shop",
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NewsImagesDataModel>

    @POST(NetworkCallPoints.BANNERS_DATA2)
    suspend fun getBanners(
        @Body params: JsonObject,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<BanNews>

    @POST(NetworkCallPoints.NOTIFY_FCM)
    suspend fun notifyFcm(
        @Body params: JsonObject,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<DataFCMModel>


    @GET(NetworkCallPoints.GET_PRODUCTS)
    suspend fun getProducts(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetProductData>


    @GET(NetworkCallPoints.GET_CHANCES)
    suspend fun getChances(@Header("token") basicCredentials: String = "$TOKENER"): Response<ChancesModelData>

    @GET(NetworkCallPoints.GET_PRODUCTS)
    suspend fun getProducts(
        @Query("keyword") keyword: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetProductData>

    @GET(NetworkCallPoints.GET_PRODUCTS)
    suspend fun getProductsCat(
        @Query("category") category: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetProductData>

    @GET(NetworkCallPoints.GET_PRODUCTS)
    suspend fun getProducts(
        @Query("category") category: String?,
        @Query("keyword") keyword: String?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetProductData>


    @GET(NetworkCallPoints.GET_CART)
    suspend fun getCart(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetCartData>

    @GET(NetworkCallPoints.APPLU_COUON)
    suspend fun applyCoupon(
        @Query("code") code: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetCartData>

    @GET(NetworkCallPoints.GET_WALLET)
    suspend fun getWallet(@Header("token") basicCredentials: String = "$TOKENER"): Response<GetWalletData>

    @POST(NetworkCallPoints.GET_NEWS)
    suspend fun getUpcomingNews(
        @Body params: JsonObject,
        @Query("upcoming") upcoming: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @GET(NetworkCallPoints.GET_NEWS_BY_ID)
    suspend fun getNewsById(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<GetNewsByIdData>

    @POST(NetworkCallPoints.GET_NEWS)
    suspend fun getCurrentNews(
        @Body params: JsonObject,
        @Query("current") current: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @POST(NetworkCallPoints.GET_NEWS)
    suspend fun getRecentNews(
        @Body params: JsonObject,
        @Query("recent") recent: Boolean,
        @Header("token") basicCredentials: String = "$TOKENER",
    ): Response<NewsData>

    @GET(NetworkCallPoints.GET_COUPONS)
    suspend fun getCoupons(@Header("token") basicCredentials: String = "$TOKENER"): Response<CouponsData>


    @POST(NetworkCallPoints.ORDERS_CHECKOUT)
    suspend fun stripeDataMethod(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<StripeCheckoutModelData>

    @GET(NetworkCallPoints.GET_PRODUCT_BY_ID)
    suspend fun getProductById(
        @Path("id") Productid: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetProductById>

    @GET(NetworkCallPoints.GET_ALL_CATEGORIES)
    suspend fun getCategories(@Header("token") basicCredentials: String = "$TOKENER"): Response<CategoryEcomData>

    @POST(NetworkCallPoints.LOGIN_PHONE)
    suspend fun loginPhone(@Body params: JsonObject?): Response<LoginData>

    @POST(NetworkCallPoints.FOTGOTPASS_EMAIL)
    suspend fun forgotpass(@Body params: JsonObject?): Response<ForgotPassData>

    @POST(NetworkCallPoints.RESEND_OTP)
    suspend fun resendOtp(@Body params: JsonObject?): Response<ResendOtpData>

    @POST(NetworkCallPoints.SIGNUP_EMAIL)
    suspend fun Signup(@Body params: JsonObject?): Response<SignupData>

    @POST(NetworkCallPoints.ADD_TO_CART)
    suspend fun AddToCart(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<AddtoCartData>

    @POST(NetworkCallPoints.ADD_WISHLIST)
    suspend fun addToWishList(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<AddToWishlistData>

    @PUT(NetworkCallPoints.ACTIVE_USER)
    suspend fun activeUser(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ModelActiveUser>


    //    @HTTP(method = "DELETE",path=NetworkCallPoints.DELETE_WISHLIST)
//    @DELETE(NetworkCallPoints.DELETE_WISHLIST)
    @HTTP(method = "DELETE", path = NetworkCallPoints.DELETE_WISHLIST, hasBody = true)
    suspend fun deleteToWishList(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<DeleteWishListData>

    @POST(NetworkCallPoints.CREATE_ORDER)
    suspend fun createOrder(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<AddressOrderCreate>


    @POST(NetworkCallPoints.ADD_TOPUP)
    suspend fun addTopUp(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<TopUpModelData>

    @GET(NetworkCallPoints.CARDS_LIST)
    suspend fun cardsList(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<CardsModel>

    @PUT(NetworkCallPoints.DEFAULT_CARD)
    suspend fun setDefaultCard(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<DeleteWishListData>

    @POST(NetworkCallPoints.RESET_PASSWORD)
    suspend fun resetPass(@Body params: JsonObject?): Response<SuccessData>

    @PUT(NetworkCallPoints.CHANGE_PASSWORD)
    suspend fun changePass(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SuccessData>


    @PUT(NetworkCallPoints.RESULT_GAME)
    suspend fun resultGame(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GameObj>

    @Multipart
    @POST(NetworkCallPoints.UPLOAD_IMGS)
    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ModelUploadImage>

    @Multipart
    @POST(NetworkCallPoints.UPLOAD_DOC_IMG)
    suspend fun uploadDocImg(
        @Part images: List<MultipartBody.Part>,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<UploadImgDoc>

    @POST(NetworkCallPoints.OTP_VERIFY)
    suspend fun otpVerify(
        @Body params: JsonObject?
    ): Response<LoginData>

    @HTTP(method = "DELETE", path = NetworkCallPoints.DELETE_CART)
    suspend fun deleteCart(
        @Path("productId") productId: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SuccessData>

    @PUT(NetworkCallPoints.UPDATE_CART)
    suspend fun updateCart(
        @Body params: JsonObject?,
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

    @HTTP(method = "DELETE", path = NetworkCallPoints.UNSUB, hasBody = true)
    suspend fun unsub(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SuccessData>

    @GET(NetworkCallPoints.DELETE_USER)
    suspend fun deleteUser(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<DELETEUSERModel>

    @GET(NetworkCallPoints.COLLECT_PRIZE)
    suspend fun collectPrizeRaffal(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<CollectDataModel>


    @GET(NetworkCallPoints.COLLECT_PRIZE)
    suspend fun claimedPrizeRaffal(
        @Query("claimed") claimed: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<CollectDataModel>

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
//        @Query("") type: String?,
        @Path("id") id: String?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetQuizById>

    @GET(NetworkCallPoints.CLAIM_PRIZE)
    suspend fun claimPrize(
        @Path("id") id: String?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ClaimPrizeModel>

    @POST(NetworkCallPoints.QUIZ_RESULT)
    suspend fun quizResult(
        @Body params: JsonObject,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<QuizResultDataModel>


    @GET(NetworkCallPoints.GET_ORDERS2)
    suspend fun getOrders(
        @Query("orderStatus") orderStatus: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetOrdersData>

    @GET(NetworkCallPoints.GET_NOTIFICATION)
    suspend fun getNotifications(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NotificationData>

    @GET(NetworkCallPoints.GET_PLAN)
    suspend fun getPlan(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GerPlanData>

    @POST(NetworkCallPoints.SUB_PLAN)
    suspend fun subPlan(
        @Body params: JsonObject,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<SubData>

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
        @Path("userId") userId: String = "",
        /*   @Header("token") basicCredentials: String = "$TOKENER"*/
    ): Response<TopWinnerData>


    @GET(NetworkCallPoints.GET_ADDRESS_LIST)
    suspend fun getAddressList(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetAddressListData>

    @POST(NetworkCallPoints.ADD_ADDRESS)
    suspend fun addAddress(
        @Body params: JsonObject?, @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetAddressListData>

    @HTTP(method = "DELETE", path = NetworkCallPoints.DELETE_ADDRESS)
    suspend fun deleteAddress(
        @Path("addressId") uniqueID: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetAddressListData>

    @GET(NetworkCallPoints.GET_ADDRESS_BY_ID)
    suspend fun GetAddressById(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetAddressById>


    @PUT(NetworkCallPoints.UPDATE_ADDRESS)
    suspend fun updateAddress(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<GetAddressById>

    @PUT(NetworkCallPoints.UPDATE_NOTIFICATION)
    suspend fun updateNotificationSetting(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NotificationSettingsData>

    @PUT(NetworkCallPoints.BUY_SUBSCRIPTION)
    suspend fun buySubscription(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<BuySubscription>


    @GET(NetworkCallPoints.GET_NOTIFICATION_SETTING)
    suspend fun getNotificationSetting(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<NotificationSettingsData>

    @GET(NetworkCallPoints.GET_STATICS_DATA)
    suspend fun getUserStatics(
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<StaticsData>


    @GET(NetworkCallPoints.GET_WALLET_TRANSACTION)
    suspend fun getTransData(
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ResponseBody>

    @GET(NetworkCallPoints.GET_CAT_PLANS)
    suspend fun getCatPlans(
        @Query("archive") archive: Boolean?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<CatPlanData>

    @GET(NetworkCallPoints.GET_PLAN_CAT_BY_ID)
    suspend fun getCatPlansById(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<CatPlanById>


    @GET(NetworkCallPoints.SCRATCH_IMAGE)
    suspend fun GetScratchImg(
        @Path("id") id: String,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ScratchImgData>

    @PUT(NetworkCallPoints.SUBMIT_CLAIM)
    suspend fun submitClaim(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ScratchImgData>


    @POST(NetworkCallPoints.CANCEL_ORDER)
    suspend fun cancelProductOrder(
        @Path("id") id: String?,
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<OrderDetailData>

    @POST(NetworkCallPoints.BANK_DETAILS)
    suspend fun bankDetails(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<BankData>

    @POST(NetworkCallPoints.EXCHANGE_RATE)
    suspend fun exchangeRate(
        @Body params: JsonObject?,
        @Header("token") basicCredentials: String = "$TOKENER"
    ): Response<ExchangeRateData>


}