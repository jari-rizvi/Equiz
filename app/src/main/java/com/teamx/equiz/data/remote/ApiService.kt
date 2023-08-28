package com.teamx.equiz.data.remote


import com.google.gson.JsonObject
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.data.models.editProfile.EditProfileData
import com.teamx.equiz.data.models.forgotpassData.ForgotPassData
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.models.otpForgotData.OtpforgotData
import com.teamx.equiz.data.models.signupData.SignupData
import com.teamx.equiz.data.models.sucessData.SuccessData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET(NetworkCallPoints.API_GET_ALL_PRODUCTS)
    suspend fun getProducts(): Response<List<ProductModel>>

    @POST(NetworkCallPoints.LOGIN_PHONE)
    suspend fun loginPhone(@Body params: JsonObject?): Response<LoginData>

    @POST(NetworkCallPoints.FOTGOTPASS_EMAIL)
    suspend fun forgotpass(@Body params: JsonObject?): Response<ForgotPassData>

    @POST(NetworkCallPoints.SIGNUP_EMAIL)
    suspend fun Signup(@Body params: JsonObject?): Response<SignupData>

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


    @GET(NetworkCallPoints.OTP_VERIFY_FORGOT)
    suspend fun otpVerifyForgot(
        @Path("uniqueID") uniqueID: String
    ): Response<OtpforgotData>

}