package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
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
import com.teamx.equiz.databinding.FragmentOtpEmailBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class OtpEmailFragment : BaseFragment<FragmentOtpEmailBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp_email
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

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
//            params.addProperty("phone", "")

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
                            PrefHelper.getInstance(requireContext()).saveUerId(it.data.user._id)

                            var bundle = arguments

                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle.putString("token_id",data.token)
                            NetworkCallPoints.TOKENER = data.token
                            findNavController().navigate(
                                R.id.action_otpEmailFragment_to_successFragment,
                                bundle,
                                options
                            )

                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                         if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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