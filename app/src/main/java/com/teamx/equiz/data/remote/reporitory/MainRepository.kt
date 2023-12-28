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
    suspend fun getBanners() = apiService.getBanners(/*isActive*/)
    suspend fun getProducts() = apiService.getProducts()

    suspend fun getProducts(keyword: String) = apiService.getProducts(keyword=keyword)
    suspend fun getProductsCat(category: String) = apiService.getProductsCat(category=category)

    suspend fun getProducts(keyword: String, category: String) =
        apiService.getProducts(keyword = keyword, category = category)

    suspend fun getCart() = apiService.getCart()
    suspend fun getWallet() = apiService.getWallet()
    suspend fun getUpcomingNews(
        @Query("upcoming") upcoming: Boolean
    ) = apiService.getUpcomingNews(upcoming)

    suspend fun getCurrentNews(
        @Query("current") current: Boolean
    ) = apiService.getCurrentNews(current)

    suspend fun getRecentNews(
        @Query("recent") recent: Boolean
    ) = apiService.getRecentNews(recent)

    suspend fun getCoupons() = apiService.getCoupons()
    suspend fun stripeDataMethod(@Body params: JsonObject?) = apiService.stripeDataMethod(params)
    suspend fun getProductById(@Path("id") Productid: String) = apiService.getProductById(Productid)
    suspend fun getCategories() = apiService.getCategories()
    suspend fun loginPhone(@Body param: JsonObject) = apiService.loginPhone(param)
    suspend fun forgotpass(@Body param: JsonObject) = apiService.forgotpass(param)
    suspend fun Signup(@Body param: JsonObject) = apiService.Signup(param)
    suspend fun AddToCart(@Body param: JsonObject) = apiService.AddToCart(param)
    suspend fun AddToWishList(@Body param: JsonObject) = apiService.AddToWishList(param)
    suspend fun createOrder(@Body param: JsonObject) = apiService.createOrder(param)
    suspend fun resetPass(@Body param: JsonObject) = apiService.resetPass(param)
    suspend fun resultGame(@Body param: JsonObject) = apiService.resultGame(param)
    suspend fun otpVerify(@Path("uniqueID") uniqueID: String) = apiService.otpVerify(uniqueID)
    suspend fun getNewsById(@Path("id") id: String) = apiService.getNewsById(id)
    suspend fun deleteCart(@Path("deleteCart") deleteCart: String) =
        apiService.deleteCart(deleteCart)

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

    suspend fun quizFind(
        @Query("country") country: String,
        @Query("topic") topic: String?,
        @Query("type") type: String?,
    ) = apiService.quizFind(country, topic, type)

    suspend fun getOrders(
        @Query("orderStatus") orderStatus: String,
    ) = apiService.getOrders(orderStatus)

    suspend fun getNotifications() = apiService.getNotifications()
    suspend fun getPlan() = apiService.getPlan()

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


}