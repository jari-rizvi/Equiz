package com.teamx.equiz.ui.fragments.Auth.login


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
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
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoginEmailBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.regex.Pattern


@AndroidEntryPoint
class LogInEmailFragment : BaseFragment<FragmentLoginEmailBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login_email
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private var userEmail: String? = null
    private var password: String? = null
    private lateinit var options: NavOptions
    private lateinit var fcmToken: String
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




        FirebaseApp.initializeApp(requireContext())
        Firebase.initialize(requireContext())
        mViewModel.viewModelScope.launch(Dispatchers.IO) {
            addClientCountry()
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            fcmToken = task.result

            Log.d("TokeeennnFcm", "onViewCreated: $fcmToken")

        })
        mViewDataBinding.btnSignup.setOnClickListener {
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle?.putString("country", country)
            findNavController().navigate(
                R.id.action_logInEmailFragment_to_signupEmailFragment,
                bundle,
                options
            )
        }

        mViewDataBinding.btnForgot.setOnClickListener {
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle?.putString("country", country)
            findNavController().navigate(
                R.id.action_logInEmailFragment_to_forgotPassFragment2,
                bundle,
                options
            )
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            isValidate()
        }


        askNotificationPermission()
    }


    private fun initialization() {
        userEmail = mViewDataBinding.etEMail.text.toString().trim()
        password = mViewDataBinding.etPass.text.toString().trim()
    }

    fun ApiCall() {

        initialization()

        if (!userEmail!!.isEmpty() || !password!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("email", userEmail)
                params.addProperty("password", password)
                params.addProperty("fcmToken", fcmToken)
                params.addProperty("country", country)
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

                        Resource.Status.NOTVERIFY -> {
                            loadingDialog.dismiss()
                              if(isAdded){
                            mViewDataBinding.root.snackbar(it.message!!)
                             }

                            Handler().postDelayed({
                                var bundle = arguments
                                if (bundle == null) {
                                    bundle = Bundle()
                                }
                                bundle?.putString("country", country)
                                findNavController().navigate(
                                    R.id.action_logInEmailFragment_to_otpEmailFragment,
                                    bundle,
                                    options
                                )
                            }, 1000)

                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                lifecycleScope.launch(Dispatchers.IO) {
                                    dataStoreProvider.saveUserToken(data.token)
                                    TOKENER = data.token
                                }

                                PrefHelper.getInstance(requireContext()).saveUerId(it.data.user._id)
                                PrefHelper.getInstance(requireContext()).savePremium(it.data.user.isPremium)

                                var bundle = arguments
                                if (bundle == null) {
                                    bundle = Bundle()
                                }
                                bundle?.putString("country", country)


                                findNavController().navigate(
                                    R.id.action_logInEmailFragment_to_dashboardFragment,
                                    bundle,
                                    options
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
                        mViewModel.loginResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }

        }
    }

    fun isPasswordValid(password: String): Boolean {
        // Check if the password contains at least one special character
        val specialCharacterPattern: Pattern = Pattern.compile("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+")
        val containsSpecialCharacter = specialCharacterPattern.matcher(password).find()

        // Check if the password length is at least 8 characters
        val isLengthValid = password.trim().length >= 8

        return isLengthValid && containsSpecialCharacter
    }

    fun isValidate(): Boolean {

        if (mViewDataBinding.etEMail.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_email))
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


    var tokenFcmString = ""
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {


                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("123123", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result
                    tokenFcmString = task.result
                    val params = JsonObject()
//                    params.addProperty("title", token)
//                    params.addProperty("body", token)

//                    mViewModel.notifyFcms(params)
                    // Log and toast
//                val msg = getString(R.string.about_us, token)
                    Timber.tag("123123").d(token.toString())
                    Timber.tag("123123").d(token.toString())
//                Log.d("TAG", msg)
                })
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                // Directly ask for t
                //
                //
                // he permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            Firebase.initialize(requireContext())
            FirebaseApp.initializeApp(requireContext())
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("123123", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token

                val token = task.result
                tokenFcmString = task.result

                val params = JsonObject()

//                params.addProperty("title", token)
//                params.addProperty("body", token)
//
//                mViewModel.notifyFcms(params)

                // Log and toast
//                val msg = getString(R.string.about_us, token)
                Log.d("123123111", token.toString())
                Log.d("123123111", token.toString())
//                Log.d("TAG", msg)
            })

        } else {
//             Inform user that that your app will not show notifications.
        }


    }

   private fun addClientCountry() {
       mViewModel.viewModelScope.launch(Dispatchers.IO) {    try {


               val client = OkHttpClient()
               val request = Request.Builder()
                   .url("https://ipwho.is/")
                   .build()
               val response = client.newCall(request).execute()
               var responseCode = 0;
               if (response.code.also { responseCode = it } == 200) {
                   // Get response
                   val jsonData: String = response!!.body!!.string()
                   Log.d("TAG", "addClientCountry: $jsonData")

                   // Transform reponse to JSon Object
                   val json = JSONObject(jsonData)

                   // Use the JSon Object
                   var ip = json.getString("ip")
                   var country2 = json.getString("country")

                   country = country2

                   PrefHelper.getInstance(requireContext()).setCountry(country2)
               }
               Log.d("123123", "addClientCountry: ${response}")

       } catch (e:Exception){
           e.printStackTrace()
       } }
    }


}