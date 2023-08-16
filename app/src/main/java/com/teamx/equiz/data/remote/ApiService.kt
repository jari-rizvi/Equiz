package com.teamx.equiz.data.remote


import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.models.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(NetworkCallPoints.API_GET_ALL_PRODUCTS)
    suspend fun getProducts(): Response<List<ProductModel>>
}