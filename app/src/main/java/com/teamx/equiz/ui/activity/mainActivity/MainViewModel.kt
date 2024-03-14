package com.teamx.equiz.ui.activity.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.activity.mainActivity.activeusermodel.ModelActiveUser
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _activeUserResponse = MutableLiveData<Resource<ModelActiveUser>>()
    val activeUserResponse: LiveData<Resource<ModelActiveUser>>
        get() = _activeUserResponse

    fun activeUser(param: JsonObject) {
        viewModelScope.launch {
            _activeUserResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.activeUser(param).let {
                        if (it.isSuccessful) {
                            _activeUserResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 302) {
                            _activeUserResponse.postValue(Resource.notVerify("", null))
                        } else if (it.code() == 401) {
                            _activeUserResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400 || it.code() == 401) {
//                            _activeUserResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _activeUserResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _activeUserResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _activeUserResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _activeUserResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
