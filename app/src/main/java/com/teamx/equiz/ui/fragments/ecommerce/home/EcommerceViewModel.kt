package com.teamx.equiz.ui.fragments.ecommerce.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.categoriesData.GetAllCategoriesData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
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


    private val _getcategoriesResponse = MutableLiveData<Resource<GetAllCategoriesData>>()
    val getcategoriesResponse: LiveData<Resource<GetAllCategoriesData>>
        get() = _getcategoriesResponse
    fun getCategories() {
        viewModelScope.launch {
            _getcategoriesResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d( "starta")

                    mainRepository.getCategories().let {
                        if (it.isSuccessful) {
                            _getcategoriesResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d( it.body()!!.toString())
                        }  else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d( "secoonnddd")

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
                            Timber.tag("87878787887").d( "third")

                        }
                    }
                } catch (e: Exception) {
                    _getcategoriesResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getcategoriesResponse.postValue(Resource.error("No internet connection", null))
        }
    }





}