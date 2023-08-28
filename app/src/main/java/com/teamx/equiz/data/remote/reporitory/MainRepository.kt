package com.teamx.equiz.data.remote.reporitory

import com.google.gson.JsonObject
import com.teamx.equiz.data.local.db.AppDao
import com.teamx.equiz.data.remote.ApiService
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    localDataSource: AppDao
) {


    suspend fun getProducts() = apiService.getProducts()
    suspend fun loginPhone(@Body param: JsonObject) = apiService.loginPhone(param)
    suspend fun forgotpass(@Body param: JsonObject) = apiService.forgotpass(param)
    suspend fun Signup(@Body param: JsonObject) = apiService.Signup(param)
    suspend fun resetPass(@Body param: JsonObject) = apiService.resetPass(param)
    suspend fun editProfile(@Body param: JsonObject) = apiService.editProfile(param)
    suspend fun otpVerify(@Path("uniqueID") uniqueID: String) = apiService.otpVerify(uniqueID)
    suspend fun otpVerifyForgot(@Path("uniqueID") uniqueID: String) = apiService.otpVerifyForgot(uniqueID)


}