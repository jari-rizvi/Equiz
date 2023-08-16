package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentOtpPhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpPhoneFragment : BaseFragment<FragmentOtpPhoneBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp_phone
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var pinView: String? = null
    private var phone: String? = ""
    private var fromSignup = false

    private lateinit var options: NavOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}