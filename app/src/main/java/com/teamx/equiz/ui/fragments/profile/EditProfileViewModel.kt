package com.teamx.equiz.ui.fragments.profile



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.editProfile.EditProfileData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _editProfileResponse = MutableLiveData<Resource<EditProfileData>>()
    val editProfileResponse: LiveData<Resource<EditProfileData>>
        get() = _editProfileResponse

    fun editProfile(param: JsonObject) {
        viewModelScope.launch {
            _editProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.editProfile(param).let {
                        if (it.isSuccessful) {
                            _editProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _editProfileResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _editProfileResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _editProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _editProfileResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _editProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _editProfileResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}