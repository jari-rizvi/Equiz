package com.teamx.equiz.ui.fragments.birthday


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.scratchData.ScratchImgData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.GetAddressListData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _scratchimgResponse = MutableLiveData<Resource<ScratchImgData>>()
    val scratchimgResponse: LiveData<Resource<ScratchImgData>>
        get() = _scratchimgResponse

    fun scratchimg(id: String) {
        viewModelScope.launch {
            _scratchimgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.GetScratchImg(id).let {
                        if (it.isSuccessful) {
                            _scratchimgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _scratchimgResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _scratchimgResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _scratchimgResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _scratchimgResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _scratchimgResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _scratchimgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _scratchimgResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}