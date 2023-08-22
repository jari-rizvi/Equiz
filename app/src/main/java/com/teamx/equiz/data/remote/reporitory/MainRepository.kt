package com.teamx.equiz.data.remote.reporitory

import com.teamx.equiz.data.local.db.AppDao
import com.teamx.equiz.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    localDataSource: AppDao
) {


    suspend fun getProducts() = apiService.getProducts()
    suspend fun loginEmail() = apiService.loginEmail()


}