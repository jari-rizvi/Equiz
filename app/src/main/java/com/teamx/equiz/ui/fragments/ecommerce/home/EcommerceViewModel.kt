package com.teamx.equiz.ui.fragments.ecommerce.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.addtowishlist.AddToWishlistData
import com.teamx.equiz.data.models.bannerData.bannews.BanNews
import com.teamx.equiz.data.models.delete_wishlist.DeleteWishListData
import com.teamx.equiz.data.models.getProducts.GetProductData
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.ecommerce.data.CategoryEcomData
import com.teamx.equiz.ui.fragments.ecommerce.home.datanews.NewsImagesDataModel
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EcommerceViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _getcategoriesResponse = MutableLiveData<Resource<CategoryEcomData>>()
    val getcategoriesResponse: LiveData<Resource<CategoryEcomData>>
        get() = _getcategoriesResponse



    private val _getProductsResponse = MutableLiveData<Resource<GetProductData>>()
    val getProductsResponse: LiveData<Resource<GetProductData>>
        get() = _getProductsResponse

    fun getCategories() {
        viewModelScope.launch {
            _getcategoriesResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getCategories().let {
                        if (it.isSuccessful) {
                            _getcategoriesResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getcategoriesResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getcategoriesResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getcategoriesResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getcategoriesResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getcategoriesResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getcategoriesResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _getBannerResponse = MutableLiveData<Resource<BanNews>>()
    val getBannerResponse: LiveData<Resource<BanNews>>
        get() = _getBannerResponse


    fun getBanners(params: JsonObject) {
        viewModelScope.launch {
            _getBannerResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getBanners(params/*true*/).let {
                        if (it.isSuccessful) {
                            _getBannerResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getBannerResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getBannerResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getBannerResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getBannerResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getBannerResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getBannerResponse.postValue(Resource.error("No internet connection", null))
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
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _addtowishlistResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
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
//                            unAuthorizedCallback.onToSignUpPage()
                            _deleteToWishlistResponse.postValue(Resource.error(it.message(), null))
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







    fun getProducts(category: String = "", keyword: String = "") {
        viewModelScope.launch {
            _getProductsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    if (category.isNullOrEmpty() && keyword.isNullOrEmpty()) {


                        mainRepository.getProducts().let {
                            if (it.isSuccessful) {
                                _getProductsResponse.postValue(Resource.success(it.body()!!))
                                Timber.tag("87878787887").d(it.body()!!.toString())
                            } else if (it.code() == 401) {
                                _getProductsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                                Timber.tag("87878787887").d("secoonnddd")

//                            _getProductsResponse.postValue(Resource.error(it.message(), null))
                                val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        jsonObj.getString(
                                            "message"
                                        )
                                    )
                                )
                            } else {
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        "Some thing went wrong",
                                        null
                                    )
                                )
                                Timber.tag("87878787887").d("third")

                            }
                        }


                    } else if (category.isNotEmpty() && keyword.isNullOrEmpty()) {

                        mainRepository.getProductsCat(category).let {
                            if (it.isSuccessful) {
                                _getProductsResponse.postValue(Resource.success(it.body()!!))
                                Timber.tag("87878787887").d(it.body()!!.toString())
                            } else if (it.code() == 401) {
                                _getProductsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                                Timber.tag("87878787887").d("secoonnddd")

//                            _getProductsResponse.postValue(Resource.error(it.message(), null))
                                val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        jsonObj.getString(
                                            "message"
                                        )
                                    )
                                )
                            } else {
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        "Some thing went wrong",
                                        null
                                    )
                                )
                                Timber.tag("87878787887").d("third")

                            }
                        }
                    } else if (category.isNullOrEmpty() && keyword.isNotEmpty()) {

                        mainRepository.getProducts(keyword).let {
                            if (it.isSuccessful) {
                                _getProductsResponse.postValue(Resource.success(it.body()!!))
                                Timber.tag("87878787887").d(it.body()!!.toString())
                            } else if (it.code() == 401) {
                                _getProductsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                                Timber.tag("87878787887").d("secoonnddd")

//                            _getProductsResponse.postValue(Resource.error(it.message(), null))
                                val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        jsonObj.getString(
                                            "message"
                                        )
                                    )
                                )
                            } else {
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        "Some thing went wrong",
                                        null
                                    )
                                )
                                Timber.tag("87878787887").d("third")

                            }
                        }
                    } else {

                        mainRepository.getProducts(keyword, category).let {
                            if (it.isSuccessful) {
                                _getProductsResponse.postValue(Resource.success(it.body()!!))
                                Timber.tag("87878787887").d(it.body()!!.toString())
                            } else if (it.code() == 401) {
                                _getProductsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                                Timber.tag("87878787887").d("secoonnddd")

//                            _getProductsResponse.postValue(Resource.error(it.message(), null))
                                val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        jsonObj.getString(
                                            "message"
                                        )
                                    )
                                )
                            } else {
                                _getProductsResponse.postValue(
                                    Resource.error(
                                        "Some thing went wrong",
                                        null
                                    )
                                )
                                Timber.tag("87878787887").d("third")

                            }
                        }

                    }
                  /*  mainRepository.getProducts().let {
                        if (it.isSuccessful) {
                            _getProductsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _addressUpdateResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getProductsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj =
                                JSONObject(it.errorBody()!!.charStream().readText())
                            _getProductsResponse.postValue(
                                Resource.error(
                                    jsonObj.getString(
                                        "message"
                                    )
                                )
                            )
                        } else {
                            _getProductsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }*/
                } catch (e: Exception) {
                    _getProductsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getProductsResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _meResponse = MutableLiveData<Resource<MeModel>>()
    val meResponse: LiveData<Resource<MeModel>>
        get() = _meResponse
    fun me() {
        viewModelScope.launch {
            _meResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.me().let {
                        if (it.isSuccessful) {
                            _meResponse.postValue(Resource.success(it.body()!!))
                        }
                        else if (it.code() == 401) {
                            _meResponse.postValue(Resource.unAuth("", null))
                        }else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _meResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _meResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _meResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _getBannerResponse2 = MutableLiveData<Resource<NewsImagesDataModel>>()
    val getBannerResponse2: LiveData<Resource<NewsImagesDataModel>>
        get() = _getBannerResponse2


    fun getBanners2(country: String) {
        viewModelScope.launch {
            _getBannerResponse2.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getBannersEco(country/*true*/).let {
                        if (it.isSuccessful) {
                            _getBannerResponse2.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getBannerResponse2.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getBannerResponse2.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getBannerResponse2.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getBannerResponse2.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getBannerResponse2.postValue(Resource.error("${e.message}", null))
                }
            } else _getBannerResponse2.postValue(Resource.error("No internet connection", null))
        }
    }







}