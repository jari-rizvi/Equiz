package com.teamx.equiz.data.remote.reporitory

import com.google.gson.JsonObject
import com.teamx.equiz.data.local.db.AppDao
import com.teamx.equiz.data.remote.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    localDataSource: AppDao
) {


    suspend fun getWishlist() = apiService.getWishlist()
    suspend fun getBanners(isActive: Boolean) = apiService.getBanners(isActive)
    suspend fun getBannersDashboard(country: String) = apiService.getBannersDashboard(country)
    suspend fun getBannersEco(country: String) = apiService.getBannersEco(country)
    suspend fun getBanners(params: JsonObject) = apiService.getBanners(params/*isActive*/)
    suspend fun notifyFcm(params: JsonObject) = apiService.notifyFcm(params/*isActive*/)
    suspend fun getProducts() = apiService.getProducts()
    suspend fun getChances() = apiService.getChances()

    suspend fun getProducts(keyword: String) = apiService.getProducts(keyword = keyword)
    suspend fun getProductsCat(category: String) = apiService.getProductsCat(category = category)

    suspend fun getProducts(keyword: String, category: String) =
        apiService.getProducts(keyword = keyword, category = category)

    suspend fun getCart() = apiService.getCart()
    suspend fun getWallet() = apiService.getWallet()
    suspend fun getUpcomingNews(
        @Body params: JsonObject,
        @Query("upcoming") upcoming: Boolean
    ) = apiService.getUpcomingNews(params, upcoming)

    suspend fun getCurrentNews(
        @Body params: JsonObject,
        @Query("current") current: Boolean
    ) = apiService.getCurrentNews(params, current)

    suspend fun getRecentNews(@Body params: JsonObject, @Query("recent") recent: Boolean) = apiService.getRecentNews(params, recent)

    suspend fun getCoupons() = apiService.getCoupons()
    suspend fun stripeDataMethod(@Body params: JsonObject?) = apiService.stripeDataMethod(params)
    suspend fun getProductById(@Path("id") Productid: String) = apiService.getProductById(Productid)
    suspend fun getCategories() = apiService.getCategories()
    suspend fun loginPhone(@Body param: JsonObject) = apiService.loginPhone(param)
    suspend fun forgotpass(@Body param: JsonObject) = apiService.forgotpass(param)
    suspend fun Signup(@Body param: JsonObject) = apiService.Signup(param)
    suspend fun AddToCart(@Body param: JsonObject) = apiService.AddToCart(param)
    suspend fun AddToWishList(@Body param: JsonObject) = apiService.addToWishList(param)
    suspend fun deleteToWishList(@Body param: JsonObject) = apiService.deleteToWishList(param)
    suspend fun createOrder(@Body param: JsonObject) = apiService.createOrder(param)
    suspend fun addTopUp(@Body param: JsonObject) = apiService.addTopUp(param)
    suspend fun resetPass(@Body param: JsonObject) = apiService.resetPass(param)
    suspend fun changePass(@Body param: JsonObject) = apiService.changePass(param)
    suspend fun resultGame(@Body param: JsonObject) = apiService.resultGame(param)
    suspend fun otpVerify(@Body params: JsonObject?) = apiService.otpVerify(params)
    suspend fun getNewsById(@Path("id") id: String) = apiService.getNewsById(id)
    suspend fun deleteCart(@Path("deleteCart") deleteCart: String) =
        apiService.deleteCart(deleteCart)

    suspend fun updateCart(@Body updateCart: JsonObject) = apiService.updateCart(updateCart)

    suspend fun otpVerifyForgot(@Path("uniqueID") uniqueID: String) =
        apiService.otpVerifyForgot(uniqueID)

    suspend fun uploadReviewImg(
        @Part images: List<MultipartBody.Part>
    ) = apiService.uploadReviewImg(images)

    suspend fun updateProfile(
        @Body params: JsonObject,
    ) = apiService.updateProfile(params)

    suspend fun quizTitle(
        @Query("country") country: String,
        @Query("topic") topic: String?,
        @Query("type") type: String,
    ) = apiService.quizTitle(country, topic, type)

    suspend fun quizFind(id: String? ) = apiService.quizFind(id)
    suspend fun quizResult(@Body resultQuiz: JsonObject ) = apiService.quizResult(resultQuiz)

    suspend fun getOrders(
        @Query("orderStatus") orderStatus: String,
    ) = apiService.getOrders(orderStatus)

    suspend fun getNotifications() = apiService.getNotifications()
    suspend fun getPlan() = apiService.getPlan()
    suspend fun subPlan(@Body updateCart: JsonObject) = apiService.subPlan(updateCart)

    suspend fun getQuizTitle(
        @Query("country") country: String,
        @Query("topic") topic: String,
        @Query("type") type: String,
    ) = apiService.getQuizTitle(country, topic, type)

    suspend fun getOrderDetail(
        @Query("id") id: String,
    ) = apiService.getOrderDetail(id)

    suspend fun getTopWinners() = apiService.getTopWinners()

    suspend fun me() = apiService.me()
    suspend fun unsub() = apiService.unsub()
    suspend fun deleteUser() = apiService.deleteUser()
    suspend fun collectPrizeRaffal() = apiService.collectPrizeRaffal()
    suspend fun claimedPrizeRaffal(@Query("claimed") claimed: String) = apiService.claimedPrizeRaffal(claimed)


}