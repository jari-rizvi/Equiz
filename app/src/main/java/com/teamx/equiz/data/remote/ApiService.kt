package com.teamx.equiz.data.remote


import com.google.gson.JsonObject
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.data.models.loginData.LoginData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(NetworkCallPoints.API_GET_ALL_PRODUCTS)
    suspend fun getProducts(): Response<List<ProductModel>>

    @POST(NetworkCallPoints.LOGIN_PHONE)
    suspend fun loginPhone(@Body params: JsonObject?): Response<LoginData>

}