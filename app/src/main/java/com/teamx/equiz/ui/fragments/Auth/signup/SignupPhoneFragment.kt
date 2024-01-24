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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.regex.Pattern

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
    var country: String = ""


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

            findNavController().navigate(R.id.action_signupPhoneFragment_to_logInFragment,arguments,options)

        }

        mViewModel.viewModelScope.launch(Dispatchers.IO) {
            addClientCountry()
        }


        mViewDataBinding.btnSignup.setOnClickListener {
            isValidate()
        }


        mViewModel.viewModelScope.launch(Dispatchers.IO) {
            addClientCountry()
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

            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString("phone", userPhone)


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
                        Resource.Status.AUTH -> { loadingDialog.dismiss()
                             if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        }
                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                findNavController().navigate(R.id.action_signupPhoneFragment_to_otpPhoneFragment,bundle,options)
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

    fun isPasswordValid(password: String): Boolean {
        val specialCharacterPattern: Pattern = Pattern.compile("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+")
        val containsSpecialCharacter = specialCharacterPattern.matcher(password).find()

        val isLengthValid = password.trim().length >= 8

        return isLengthValid && containsSpecialCharacter
    }

    fun isValidate(): Boolean {
        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
             }
            return false
        }
        if (mViewDataBinding.etName.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_name))
             }
            return false
        }

        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_pasword))
             }
            return false
        }
        if (!isPasswordValid(mViewDataBinding.etPass.text.toString())) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(R.string.password_8_character_and_special))
            }
            return false
        }
        ApiCall()
        return true
    }


    private fun addClientCountry() {
        mViewModel.viewModelScope.launch(Dispatchers.IO) {  try {


            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://ipwho.is/")
                .build()

            val response = client.newCall(request).execute()
            var responseCode = 0;
            if (response.code.also { responseCode = it } == 200) {
                // Get response
                val jsonData: String = response!!.body!!.string()

                // Transform reponse to JSon Object
                val json = JSONObject(jsonData)

                // Use the JSon Object
                var ip = json.getString("ip")
                var country2 = json.getString("country")

                country = country2
            }
            Log.d("123123", "addClientCountry: ${response}")
        }

        catch (e:Exception){
            e.printStackTrace()
        }
        }
    }


}