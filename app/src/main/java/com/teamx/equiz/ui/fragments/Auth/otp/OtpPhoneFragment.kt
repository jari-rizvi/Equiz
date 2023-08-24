package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentOtpPhoneBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class OtpPhoneFragment : BaseFragment<FragmentOtpPhoneBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp_phone
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


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


        mViewDataBinding.btnVerify.setOnClickListener {
            verifyotpForgot()
        }

    }


    fun verifyotpForgot() {

        val code = mViewDataBinding.pinView.text.toString()

            mViewModel.otpVerify(code)
            if (!mViewModel.otpVerifyResponse.hasActiveObservers()) {
                mViewModel.otpVerifyResponse.observe(requireActivity(), Observer {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            it.data?.let { data ->
                                findNavController().navigate(R.id.action_otpPhoneFragment_to_successFragment)


                            }
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.otpVerifyResponse.removeObservers(viewLifecycleOwner)
                    }
                })
            }

        }
    }
