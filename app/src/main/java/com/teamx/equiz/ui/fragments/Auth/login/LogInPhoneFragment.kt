package com.teamx.equiz.ui.fragments.Auth.login


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoginPhoneBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class LogInPhoneFragment : BaseFragment<FragmentLoginPhoneBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login_phone
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private var userPhone: String? = null
    private var password: String? = null
    private lateinit var options: NavOptions
    private lateinit var fcmToken: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        FirebaseApp.initializeApp(requireContext())
        Firebase.initialize(requireContext())

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            fcmToken = task.result

            Log.d("TokeeennnFcm", "onViewCreated: $fcmToken")

        })


        mViewDataBinding.btnForgot.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgotPassFragment2)
        }

        mViewDataBinding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signupPhoneFragment)
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            isValidate()
        }


    }

    private fun initialization() {
        userPhone = mViewDataBinding.etEMail.text.toString().trim()
        password = mViewDataBinding.etPass.text.toString().trim()
    }

    fun ApiCall() {

        initialization()

        if (!userPhone!!.isEmpty() || !password!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("phone", userPhone)
                params.addProperty("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mViewModel.loginPhone(params)

            if (!mViewModel.loginResponse.hasActiveObservers()) {
                mViewModel.loginResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                lifecycleScope.launch(Dispatchers.IO) {
                                    dataStoreProvider.saveUserToken(data.token)
                                }
                                findNavController().navigate(R.id.action_logInFragment_to_dashboardFragment)
                            }
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.loginResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }

        }
    }

    fun isValidate(): Boolean {
        if (mViewDataBinding.etEMail.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
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