package com.teamx.equiz.ui.fragments.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.delete_wishlist.DeleteWishListData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.cards.modelcards.CardsModel
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.StripeModel
import com.teamx.equiz.ui.fragments.topup.data.TopUpModelData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _cardsListResponse = MutableLiveData<Resource<CardsModel>>()
    val cardsListResponse: LiveData<Resource<CardsModel>>
        get() = _cardsListResponse

    fun cardsList() {
        viewModelScope.launch {
            _cardsListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.cardsList().let {
                        if (it.isSuccessful) {
                            _cardsListResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 401) {
                            _cardsListResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _cardsListResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _cardsListResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _cardsListResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _cardsListResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _cardsListResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _setDefaultCardResponse = MutableLiveData<Resource<DeleteWishListData>>()
    val setDefaultCardResponse: LiveData<Resource<DeleteWishListData>>
        get() = _setDefaultCardResponse

    fun setDefaultCard(param: JsonObject) {
        viewModelScope.launch {
            _setDefaultCardResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.setDefaultCard(param).let {
                        if (it.isSuccessful) {
                            _setDefaultCardResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 401) {
                            _setDefaultCardResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _setDefaultCardResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _setDefaultCardResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _setDefaultCardResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _setDefaultCardResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _setDefaultCardResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}