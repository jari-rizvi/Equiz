package com.teamx.equiz.ui.fragments.Auth.otp

import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import androidx.activity.addCallback
import com.google.gson.JsonObject
import org.json.JSONException
import java.util.regex.Pattern

@AndroidEntryPoint
class VerifyOtpForgotFragment : BaseFragment<FragmentOtpEmailBinding, OtpViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_otp_email
    override val viewModel: Class<OtpViewModel>
        get() = OtpViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    private var userCred: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var cred: String? = null

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

        var bundle1 = arguments

        if (bundle1 == null) {
            bundle1 = Bundle()
        }

        email = bundle1.getString("email")
        phone = bundle1.getString("phone")
        cred = bundle1.getString("credentials")

        Log.d("TAG", "onViewCreated: $email")
        Log.d("TAG", "onViewCreated: $phone")
        Log.d("TAG", "onViewCreated: $cred")

        mViewDataBinding.btnResend.setOnClickListener {

            if (!cred!!.isEmpty()) {

                val params = JsonObject()
                try {
                    params.addProperty("email", email)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }


                val params1 = JsonObject()
                try {
                    params1.addProperty("phone", phone)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }



                if (isValidEmail(cred.toString())) {
                    mViewModel.resendOtp(params)
                    if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
                        mViewModel.resendOtpResponse.observe(requireActivity()) {
                            when (it.status) {
                                Resource.Status.LOADING -> {
                                    loadingDialog.show()
                                }

                                Resource.Status.NOTVERIFY -> {
                                    loadingDialog.dismiss()
                                }

                                Resource.Status.SUCCESS -> {
                                    loadingDialog.dismiss()

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
                                mViewModel.resendOtpResponse.removeObservers(viewLifecycleOwner)
                            }
                        }
                    }
                } else {
                    mViewModel.resendOtp(params1)
                    if (!mViewModel.resendOtpResponse.hasActiveObservers()) {
                        mViewModel.resendOtpResponse.observe(requireActivity()) {
                            when (it.status) {
                                Resource.Status.LOADING -> {
                                    loadingDialog.show()
                                }

                                Resource.Status.NOTVERIFY -> {
                                    loadingDialog.dismiss()
                                }

                                Resource.Status.SUCCESS -> {
                                    loadingDialog.dismiss()

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
                                mViewModel.resendOtpResponse.removeObservers(viewLifecycleOwner)
                            }
                        }
                    }
                }


            }



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

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
//                        onToSignUpPage()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            val bundle = Bundle()
                            bundle.putString("newuniquId", it.data.newUniqueID)

                            findNavController().navigate(
                                R.id.action_verifyOtpForgotFragment_to_createNewPassFragment2,
                                bundle,
                                options
                            )

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

    private fun isValidEmail(email: String?): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

}