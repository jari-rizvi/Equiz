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
class PaymentMethodsFragment :
    BaseFragment<FragmentPaymentMethodsBinding, PaymentMethodsViewModel>(),
    DialogHelperClass.Companion.OrderCompleteCallBack,DialogHelperClass.Companion.DialogLessAmountCallBack {

    override val layoutId: Int
        get() = R.layout.fragment_payment_methods
    override val viewModel: Class<PaymentMethodsViewModel>
        get() = PaymentMethodsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    var orderId: String? = ""
    var points: String? = ""
    var w_points: String? = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        w_points = PrefHelper.getInstance(requireContext()).setWalletAmount.toString()

        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        orderId = bundle.getString("order_id")
        points = bundle.getString("points")

        Log.d("pointspoints", "points: $points")

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        /* mViewDataBinding.btnmaster.setOnClickListener {
             presentPaymentSheet("657c49d67cf8073363b445af")
         } */


        mViewDataBinding.paynoW.setOnClickListener {
            if (mViewDataBinding.radioWallet.isChecked || mViewDataBinding.radioPaypal.isChecked || mViewDataBinding.radioVisa.isChecked || mViewDataBinding.radiomaster.isChecked || mViewDataBinding.radiogoogle.isChecked) {

                Log.d("TAG", "onViewCreated1212: $points")
                Log.d("TAG", "onViewCreated1212: $w_points")
                if(points!! > w_points!!){
                    DialogHelperClass.lessPointsDialog(requireContext(),this@PaymentMethodsFragment,true)
                }
                else{
                    presentPaymentSheet("$orderId",paymentOption.toString())
                }

            } else {
                showToast("Please select payment method")
            }
        }

        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }



        if (isAdded) {
            PaymentConfiguration.init(
                this.requireContext(),
                "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
            )
            presentInit()

        }


        ///

        mViewDataBinding.btnpaypal.setOnClickListener {
            mViewDataBinding.radioPaypal.isChecked = true
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = false
            mViewDataBinding.radioWallet.isChecked = false
            paymentOption = "paypal"
        }
        mViewDataBinding.wallet.setOnClickListener {
            mViewDataBinding.radioWallet.isChecked = true
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = false
            paymentOption = "wallet"
        }

        mViewDataBinding.btnvisa.setOnClickListener {
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = true
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = false
            paymentOption = "stripe"
        }

        mViewDataBinding.btnmaster.setOnClickListener {
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = true
            mViewDataBinding.radiogoogle.isChecked = false
            paymentOption = "stripe"
        }

        mViewDataBinding.btnGoogle.setOnClickListener {
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = true
            paymentOption = "stripe"
        }

        ///


    }

    lateinit var paymentIntentClientSecret: String
    lateinit var stripPublicKey: String
    var selectionStr = ""
    var googlePayLauncher: GooglePayLauncher? = null
    private lateinit var paymentsClient: PaymentsClient
    lateinit var paymentSheet: PaymentSheet
    var paymentOption: String? = ""
    private fun presentPaymentSheet(orderId: String,paymentOptions: String) {
        val params = JsonObject()
        params.addProperty("id", orderId)
        params.addProperty("paymentMethod", "$paymentOptions")
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


                            if (!paymentOptions.equals("wallet",true)){
                                paymentIntentClientSecret = it.checkout.data.client_secret ?: ""

                                Log.d("21123", "presentPaymentSheet:$paymentIntentClientSecret ")
                                stripPublicKey =
                                    "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                                if (selectionStr.contains("google", true)) {
                                    PrefHelper.getInstance(requireContext())
                                        .savaStripe("pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E")

                                    presentGooglePaySheetIN(stripPublicKey, paymentIntentClientSecret)

                                } else {


                                    paymentIntentClientSecret = it.checkout.data.client_secret ?: ""
                                    stripPublicKey =
                                        "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
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
                                        merchantDisplayName = "EQuiz",
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
                            }else{
                                DialogHelperClass.orderCompleteDialog(
                                    requireContext(), this, true, "$orderId".toString()
                                )

                            }

                            mViewModel.stripeData.removeObservers(viewLifecycleOwner)
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

                DialogHelperClass.orderCompleteDialog(
                    requireContext(), this, true, "$orderId".toString()
                )

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
            mViewDataBinding.btnGoogle.visibility = View.GONE
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
            mViewDataBinding.btnGoogle.visibility = View.VISIBLE
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

                DialogHelperClass.orderCompleteDialog(
                    requireContext(), this, true, "$orderId"
                )

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
                showSnackBar("Error", mViewDataBinding.root)
                // Operation failed; inspect `result.error` for the exception
            }
        }


    }

    override fun InviteClicked() {
        findNavController().navigate(R.id.dashboardFragment, arguments, options)
    }

    override fun Topup() {
        findNavController().navigate(R.id.topupFragment, arguments, options)
    }

}