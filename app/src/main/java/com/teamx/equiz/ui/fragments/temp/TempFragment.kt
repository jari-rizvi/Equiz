package com.teamx.equiz.ui.fragments.temp

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TempFragment : BaseFragment<ActivitySplashBinding, TempViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_splash
    override val viewModel: Class<TempViewModel>
        get() = TempViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {

    }

}