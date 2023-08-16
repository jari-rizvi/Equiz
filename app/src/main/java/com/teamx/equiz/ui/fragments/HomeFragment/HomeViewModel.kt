package com.teamx.equiz.ui.fragments.HomeFragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _getProducts = MutableLiveData<Resource<List<ProductModel>>>()
    val getMusics: LiveData<Resource<List<ProductModel>>>
        get() = _getProducts

    fun hitGetMusicAPI() {
        viewModelScope.launch {
            _getProducts.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getProducts().let {
                        if (it.isSuccessful) {
                            _getProducts.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400) {
                            _getProducts.postValue(Resource.error(it.message(), null))
                        } else {
                            _getProducts.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _getProducts.postValue(Resource.error("${e.message}", null))
                }
            } else _getProducts.postValue(Resource.error("No internet connection", null))
        }
    }

}