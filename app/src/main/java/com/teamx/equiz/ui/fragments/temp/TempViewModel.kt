package com.teamx.equiz.ui.fragments.temp


import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TempViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

}