package com.teamx.equiz.ui.fragments.ecommerce.productProfile


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getProductById.GetProductByIdData
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _productbyidResponse = MutableLiveData<Resource<GetProductByIdData>>()
    val productbyidResponse: LiveData<Resource<GetProductByIdData>>
        get() = _productbyidResponse

    fun productById(Productid: String) {
        viewModelScope.launch {
            _productbyidResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d( "starta")

                    mainRepository.getProductById(Productid).let {
                        if (it.isSuccessful) {
                            _productbyidResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d( it.body()!!.toString())
                        }  else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d( "secoonnddd")

//                            _productbyidResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _productbyidResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _productbyidResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d( "third")

                        }
                    }
                } catch (e: Exception) {
                    _productbyidResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _productbyidResponse.postValue(Resource.error("No internet connection", null))
        }
    }



}