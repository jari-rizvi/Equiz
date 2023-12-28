package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.googlepaylauncher.GooglePayEnvironment
import com.stripe.android.googlepaylauncher.GooglePayLauncher
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.AppConstants.showSnackBar
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentPaymentMethodsBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PaymentMethodsFragment : BaseFragment<FragmentPaymentMethodsBinding, PaymentMethodsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_payment_methods
    override val viewModel: Class<PaymentMethodsViewModel>
        get() = PaymentMethodsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        val orderId = bundle.getString("order_id")


        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

//        TOKENER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTJlNWEyYTE2YTU5OGNjYzRhNmIwZGUiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDI2NDQwODQsImV4cCI6MTcwMjczMDQ4NH0.bLVjA-x-AUf7jRKZT8bSxcGA-tEZIioIfZsTFQ7MzCM"

        mViewDataBinding.btnmaster.setOnClickListener {
            presentPaymentSheet("657c49d67cf8073363b445af")
        }

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }



        if (isAdded) {
            PaymentConfiguration.init(
                this.requireContext(),
                "pk_test_51O8NjlCdPClG3QS3SDNlxW3kc6gMOnOHEltPuemomvaxK4et8h2UIKG2VUg1TWK2tHrx6LcVcfCaLcbpPw9JCRti00bpYxyqll"
            )
            presentInit()

        }


    }

    lateinit var paymentIntentClientSecret: String
    lateinit var stripPublicKey: String
    var selectionStr = ""
    var googlePayLauncher: GooglePayLauncher? = null
    private lateinit var paymentsClient: PaymentsClient
    lateinit var paymentSheet: PaymentSheet

    private fun presentPaymentSheet(shopId: String) {
        val params = JsonObject()
        params.addProperty("id", shopId)
        params.addProperty("paymentMethod", "stripe")
        mViewModel.stripeDataMethod(params)

        if (!mViewModel.stripeData.hasActiveObservers()) {

            mViewModel.stripeData.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let {
                            paymentIntentClientSecret = it.client_secret ?: ""
                            stripPublicKey = "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                            if (selectionStr.contains("google", true)) {
                                PrefHelper.getInstance(requireContext())
                                    .savaStripe("pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E")

                                presentGooglePaySheetIN(stripPublicKey, paymentIntentClientSecret)

                            } else {


                                paymentIntentClientSecret = it.client_secret ?: ""
                                stripPublicKey = "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                                PaymentConfiguration.init(
                                    requireActivity().applicationContext,
                                    stripPublicKey
                                )
                                val googlePayConfiguration = PaymentSheet.GooglePayConfiguration(
                                    environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
                                    countryCode = "AE",
                                    currencyCode = "AED" // Required for Setup Intents, optional for Payment Intents
                                )
                                PrefHelper.getInstance(requireContext())
                                    .savaStripe("pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E")

                                val configuration = PaymentSheet.Configuration(
                                    merchantDisplayName = "getString(R.string.raseef_str)",
                                    allowsDelayedPaymentMethods = false,
                                    googlePay = googlePayConfiguration
                                )

//
                                //abhi comment karaha hu
                                paymentSheet.presentWithPaymentIntent(
                                    paymentIntentClientSecret, configuration
                                )



                                Log.d("TAG", "presentPaymentSheetstripPublicKey: $stripPublicKey")
                                Log.d(
                                    "TAG",
                                    "presentPaymentSheetstripPublicKey: $paymentIntentClientSecret"
                                )
                                Log.d("TAG", "presentPaymentSheet: ")


                            }
                            mViewModel.stripeData.removeObservers(viewLifecycleOwner)
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }

                    else -> {

                    }
                }
            }
        }


    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {


        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Timber.tag("Cancel").d("hello there")
                print("Canceled")
                showSnackBar("Cancel",mViewDataBinding.root)
            }

            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                Timber.tag("Error").d("hello there")
                showSnackBar("Error",mViewDataBinding.root)
            }

            is PaymentSheetResult.Completed -> {
//                print("Completed")
//                Timber.tag("Completed").d("hello there___${verifyCheckout?.amount}")
//                checkoutOrder(createCheckOutJson(verifyCheckout!!))
            }
        }
    }
//
    fun presentInit() {

        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST) // Use ENVIRONMENT_PRODUCTION in production
            .build()
        paymentsClient = Wallet.getPaymentsClient(this.requireActivity(), walletOptions)
        googlePayLauncher = GooglePayLauncher(
            fragment = this, config = GooglePayLauncher.Config(
                environment = GooglePayEnvironment.Test,
                merchantCountryCode = "US",
                merchantName = "Widget Store"
            ), readyCallback = ::onGooglePayReady, resultCallback = ::onGooglePayResult
        )
    }
//
    var boolGooglePay = false
    private fun onGooglePayReady(isReady: Boolean) {
        // implemented below
//        googlePayButton.isEnabled = isReady
        //
        boolGooglePay = isReady
        if (!isReady) {
            var counter = 0

//            paymentAdapter.arrayList = paymentAdapter.arrayList.filter {
//                counter++
//                it.paymentName != "GOOGLE PAY"
//            } as ArrayList<PaymentMethod>
//
////            showSnackBar("NotReady")
//
//            paymentAdapter.notifyItemChanged(counter)
//
//            mViewDataBinding.paymentMethodRecyclerview.adapter = paymentAdapter

        } else {
//            showSnackBar("Ready")
        }


    }
    private fun presentGooglePaySheetIN(pubKey: String, clientKey: String) {

        PaymentConfiguration.init(
            this.requireContext(), pubKey
        )
        googlePayLauncher?.presentForPaymentIntent(clientKey)

    }

    private fun onGooglePayResult(
        result: GooglePayLauncher.Result
    ) {


        when (result) {
            is GooglePayLauncher.Result.Completed -> {
                // Payment details successfully captured.
                // Send the paymentMethodId to your server to finalize payment.
//                val paymentMethodId = result.paymentMethod.id

//                val payMethodId = PaymentIntent().id
//                print("Completed")
//                Timber.tag("Completed").d("hello there___${verifyCheckout?.amount}")
//                checkoutOrder(createCheckOutJson(verifyCheckout!!))
            }

            GooglePayLauncher.Result.Canceled -> {
                // User canceled the operation
                Timber.tag("Cancel").d("hello there")
                print("Canceled")
                showSnackBar("Cancel", mViewDataBinding.root)
                // User canceled the operation
            }

            is GooglePayLauncher.Result.Failed -> {
                // Operation failed; inspect `result.error` for the exception
                print("Error: ")
                Timber.tag("Error").d("hello there")
                showSnackBar("Error",mViewDataBinding.root)
                // Operation failed; inspect `result.error` for the exception
            }
        }


    }

}