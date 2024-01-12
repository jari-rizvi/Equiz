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
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoginPhoneBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

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
    var country: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        addClientCountry()

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
            findNavController().navigate(R.id.action_logInFragment_to_forgotPassFragment2,arguments,options)
        }

        mViewDataBinding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signupPhoneFragment,arguments,options)
        }

        mViewDataBinding.btnLogin.setOnClickListener {
            isValidate()
        }

        askNotificationPermission()
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
                            if (isAdded) {
                                mViewDataBinding.root.snackbar(it.message!!)
                            }

                            var bundle = arguments

                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle?.putString("phone", userPhone)
                            Handler().postDelayed({
                                findNavController().navigate(
                                    R.id.action_logInFragment_to_otpPhoneFragment,
                                    bundle,
                                    options
                                )
                            }, 1000)

//                            onToOtpPage()
                        }
                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                lifecycleScope.launch(Dispatchers.IO) {
                                    dataStoreProvider.saveUserToken(data.token)
                                    NetworkCallPoints.TOKENER = data.token
                                }
                                findNavController().navigate(R.id.action_logInFragment_to_dashboardFragment,arguments,options)
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

    fun isValidate(): Boolean {
        if (mViewDataBinding.etEMail.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
             }
            return false
        }

        if (mViewDataBinding.etPass.text.toString().trim().isEmpty()) {
              if(isAdded){
            mViewDataBinding.root.snackbar(getString(R.string.enter_your_password))
             }
            return false
        }
        if (mViewDataBinding.etPass.text.toString().trim().length < 8) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(R.string.password_8_character))
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
        mViewModel.viewModelScope.launch(Dispatchers.IO) {
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
    }
}