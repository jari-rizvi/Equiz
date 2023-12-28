package com.teamx.equiz.ui.fragments.Auth.signup


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoginEmailBinding
import com.teamx.equiz.databinding.FragmentSignupPhoneBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import androidx.activity.addCallback
@AndroidEntryPoint
class SignupPhoneFragment : BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_signup_phone
    override val viewModel: Class<SignupViewModel>
        get() = SignupViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    private var userPhone: String? = null
    private var userName: String? = null
    private var password: String? = null

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

        mViewDataBinding.btnLogin.setOnClickListener {

            findNavController().navigate(R.id.action_signupPhoneFragment_to_logInFragment)


        }

        mViewDataBinding.btnSignup.setOnClickListener {
            isValidate()
        }

    }

    private fun initialization() {
        userPhone = mViewDataBinding.etPhone.text.toString().trim()
        userName = mViewDataBinding.etName.text.toString().trim()
        password = mViewDataBinding.etPass.text.toString().trim()
    }

    fun ApiCall() {

        initialization()

        if (!userPhone!!.isEmpty() || !password!!.isEmpty() || !userName!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("name", userName)
                params.addProperty("phone", userPhone)
                params.addProperty("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            mViewModel.signup(params)

            if (!mViewModel.signupResponse.hasActiveObservers()) {
                mViewModel.signupResponse.observe(requireActivity()) {
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

                                findNavController().navigate(R.id.action_signupPhoneFragment_to_otpPhoneFragment)
                                Log.d("TAG", "otpVale: ${it.data.otp}")


                            }
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.signupResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }

        }
    }

    fun isValidate(): Boolean {
        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
            return false
        }
        if (mViewDataBinding.etName.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_name))
            return false
        }

        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_password))
            return false
        }
        if (mViewDataBinding.etPass.text.toString().trim().length < 8) {
            mViewDataBinding.root.snackbar(getString(R.string.password_8_character))
            return false
        }
        ApiCall()
        return true
    }
}