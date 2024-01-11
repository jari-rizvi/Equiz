package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
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

    lateinit var phoneNumber: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }

        phoneNumber = bundle.getString("phone").toString()

        Log.d("phoneNumber", "verifyotpForgot: ${phoneNumber}")

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

        val params = JsonObject()
        try {
            params.addProperty("uniqueID", code)
            params.addProperty("phone", phoneNumber)

        } catch (e: JSONException) {
            e.printStackTrace()
        }


        mViewModel.otpVerify(params)
        if (!mViewModel.otpVerifyResponse.hasActiveObservers()) {
            mViewModel.otpVerifyResponse.observe(requireActivity(), Observer {
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

                            var bundle = arguments

                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle!!.putString("token_id", data.token)
                            NetworkCallPoints.TOKENER = data.token
                            findNavController().navigate(
                                R.id.action_otpPhoneFragment_to_successFragment,
                                bundle,
                                options
                            )


                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
//                            onToSignUpPage()
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
