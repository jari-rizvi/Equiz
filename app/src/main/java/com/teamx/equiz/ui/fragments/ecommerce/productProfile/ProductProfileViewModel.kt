package com.teamx.equiz.ui.fragments.ecommerce.productProfile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.addtocart.AddtoCartData
import com.teamx.equiz.data.models.addtowishlist.AddToWishlistData
import com.teamx.equiz.data.models.delete_wishlist.DeleteWishListData
import com.teamx.equiz.data.models.getProductById.GetProductByIdData
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
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getProductById(Productid).let {
                        if (it.isSuccessful) {
                            _productbyidResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _productbyidResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

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
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _productbyidResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _productbyidResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _addtocartResponse = MutableLiveData<Resource<AddtoCartData>>()
    val addtocartResponse: LiveData<Resource<AddtoCartData>>
        get() = _addtocartResponse

    fun addtocart(param: JsonObject) {
        viewModelScope.launch {
            _addtocartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.AddToCart(param).let {
                        if (it.isSuccessful) {
                            _addtocartResponse.postValue(Resource.success(it.body()!!))
                        }   else if (it.code() == 401) {
                            _addtocartResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _addtocartResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addtocartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _addtocartResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addtocartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addtocartResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _addtowishlistResponse = MutableLiveData<Resource<AddToWishlistData>>()
    val addtowishlistResponse: LiveData<Resource<AddToWishlistData>>
        get() = _addtowishlistResponse

    fun addtowishlist(param: JsonObject) {
        viewModelScope.launch {
            _addtowishlistResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.AddToWishList(param).let {
                        if (it.isSuccessful) {
                            _addtowishlistResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 401) {
                            _addtowishlistResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _addtowishlistResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addtowishlistResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _addtowishlistResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addtowishlistResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addtowishlistResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _deleteToWishlistResponse = MutableLiveData<Resource<DeleteWishListData>>()
    val deleteToWishlistResponse: LiveData<Resource<DeleteWishListData>>
        get() = _deleteToWishlistResponse

    fun deleteToWishlist(param: JsonObject) {
        viewModelScope.launch {
            _deleteToWishlistResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.deleteToWishList(param).let {
                        if (it.isSuccessful) {
                            _deleteToWishlistResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _deleteToWishlistResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _deleteToWishlistResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _deleteToWishlistResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _deleteToWishlistResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _deleteToWishlistResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _deleteToWishlistResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }


}