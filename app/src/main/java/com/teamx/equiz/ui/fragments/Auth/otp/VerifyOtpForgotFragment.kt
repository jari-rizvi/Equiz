package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentOtpEmailBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyOtpForgotFragment : BaseFragment<FragmentOtpEmailBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp_email
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    private  var userCred : String? = null

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
        mViewDataBinding.btnVerify.setOnClickListener {
            verifyotpForgot()
        }
    }


    private fun initialization() {
        val bundle = arguments
        if (bundle != null) {
            userCred = bundle.getString("credentials").toString()

        }
    }

    fun verifyotpForgot() {

        initialization()
        val code = mViewDataBinding.pinView.text.toString()

        mViewModel.otpVerifyForgot(code)
        if (!mViewModel.otpVerifyForgotResponse.hasActiveObservers()) {
            mViewModel.otpVerifyForgotResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            val bundle = Bundle()
                            bundle.putString("newuniquId",it.data.newUniqueID)

                            findNavController().navigate(R.id.action_verifyOtpForgotFragment_to_createNewPassFragment2,bundle)

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.otpVerifyForgotResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }

    }

}