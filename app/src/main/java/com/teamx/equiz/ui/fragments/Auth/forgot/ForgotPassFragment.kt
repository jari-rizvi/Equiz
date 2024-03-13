package com.teamx.equiz.ui.fragments.Auth.forgot


import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentForgotPassBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.regex.Pattern
import androidx.activity.addCallback
@AndroidEntryPoint
class ForgotPassFragment : BaseFragment<FragmentForgotPassBinding, ForgotPassViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_forgot_pass
    override val viewModel: Class<ForgotPassViewModel>
        get() = ForgotPassViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    private var UserCredentials: String? = null
    val bundle1 = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        sharedViewModel.setActiveUser("")

        mViewDataBinding.btnSendCode.setOnClickListener {
            isValidate()
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun initialization() {
        UserCredentials = mViewDataBinding.etCred.text.toString().trim()
    }

    fun ApiCall() {
        initialization()

        if (!UserCredentials!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("email", UserCredentials)
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            val params1 = JsonObject()
            try {
                params1.addProperty("phone", UserCredentials)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val bundle = Bundle()
            bundle1.putString("credentials", UserCredentials)


   /*         if (isValidEmail(userEmail.toString())) {
                mViewModel.forgotPassEmail(params)
            } else {
                mViewModel.forgotPassPhone(params1)
            }*/


            if (isValidEmail(UserCredentials.toString())) {
                mViewModel.forgotPassEmail(params)
                if (!mViewModel.forgotPassResponse.hasActiveObservers()) {
                    mViewModel.forgotPassResponse.observe(requireActivity()) {
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

                                    bundle1?.putString("email", UserCredentials)
                                    bundle1.putString("credentials", UserCredentials)
                                    findNavController().navigate(
                                        R.id.action_forgotPassFragment2_to_verifyOtpForgotFragment2,
                                        bundle1
                                    )
                                }
                            }
                            Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                            mViewModel.forgotPassResponse.removeObservers(viewLifecycleOwner)
                        }
                    }
                }
            } else {
                mViewModel.forgotPassPhone(params1)
                if (!mViewModel.forgotPassResponse.hasActiveObservers()) {
                    mViewModel.forgotPassResponse.observe(requireActivity()) {
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
                                    bundle1?.putString("phone", UserCredentials)
                                    bundle1.putString("credentials", UserCredentials)

                                    findNavController().navigate(R.id.action_forgotPassFragment2_to_verifyOtpForgotFragment2,bundle1,options)
                                }
                            }
                            Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                            mViewModel.forgotPassResponse.removeObservers(viewLifecycleOwner)
                        }
                    }
                }
            }


        }
    }

    private fun isValidate(): Boolean {
        if (mViewDataBinding.etCred.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_email_or_number))
             }
            return false
        }

        ApiCall()
        return true
    }


}