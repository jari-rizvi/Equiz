package com.teamx.equiz.ui.fragments.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getorderData.GetOrdersData
import com.teamx.equiz.data.models.notificationData.NotificationData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificaitonsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _getNotificationsResponse = MutableLiveData<Resource<NotificationData>>()
    val getNotificationsResponse: LiveData<Resource<NotificationData>>
        get() = _getNotificationsResponse

    fun getNotifications() {
        viewModelScope.launch {
            _getNotificationsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getNotifications().let {
                        if (it.isSuccessful) {
                            _getNotificationsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } /*else if (it.code() == 401) {
                            _getNotificationsResponse.postValue(Resource.unAuth("", null))
                        }*/ else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getNotificationsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getNotificationsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getNotificationsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getNotificationsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getNotificationsResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }

}