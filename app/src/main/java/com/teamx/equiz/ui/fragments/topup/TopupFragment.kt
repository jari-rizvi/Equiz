package com.teamx.equiz.ui.fragments.topup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.teamx.equiz.constants.AppConstants
import com.teamx.equiz.data.models.PaymentMethod
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentTopUpBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TopupFragment : BaseFragment<FragmentTopUpBinding, TopupViewModel>(), OnTopSellerListener,
    DialogHelperClass.Companion.DialogInviteAnotherCallBack {

    override val layoutId: Int
        get() = R.layout.fragment_top_up
    override val viewModel: Class<TopupViewModel>
        get() = TopupViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var paymentArrayList: ArrayList<PaymentMethod>

    var amount = "0"

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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        paymentAdapter()

        mViewDataBinding.pts1.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = true
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false
            amount = "100"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }

        mViewDataBinding.pts2.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = true
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false

            amount = "200"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }

        mViewDataBinding.pts3.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = true
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false
            amount = "300"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }

        mViewDataBinding.pts4.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = true
            mViewDataBinding.pts5.isChecked = false
            amount = "400"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }
        mViewDataBinding.pts5.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = true
            amount = "500"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }
        mViewDataBinding.pts6.setOnClickListener {
            mViewDataBinding.pts6.isChecked = true
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false
            amount = "600"
            priceAddTopUp = amount.toInt()
            mViewDataBinding.img.text = amount
        }

        mViewDataBinding.payNowBtn.setOnClickListener {
//            presentPaymentSheet(priceAddTopUp)
            if (mViewDataBinding.radioPaypal.isChecked
                || mViewDataBinding.radioVisa.isChecked
                || mViewDataBinding.radiomaster.isChecked
                || mViewDataBinding.radiogoogle.isChecked
            ) {
                /* if (mViewDataBinding.editText.text.toString().isNotEmpty()) {
                     priceAddTopUp = mViewDataBinding.editText.text.toString().toInt()
                     presentPaymentSheet(priceAddTopUp)
                 } else if (mViewDataBinding.editText.text.toString()
                         .isEmpty() && priceAddTopUp == 0
                 ) {
                     mViewDataBinding.pts6.isChecked = false
                     mViewDataBinding.pts1.isChecked = false
                     mViewDataBinding.pts2.isChecked = false
                     mViewDataBinding.pts3.isChecked = false
                     mViewDataBinding.pts4.isChecked = false
                     mViewDataBinding.pts5.isChecked = false
                     showToast("Please Add Amount")
                 } else if (mViewDataBinding.editText.text.toString()
                         .isEmpty() && (!mViewDataBinding.pts6.isChecked
                             && !mViewDataBinding.pts4.isChecked
                             && !mViewDataBinding.pts5.isChecked
                             && !mViewDataBinding.pts1.isChecked
                             && !mViewDataBinding.pts2.isChecked
                             && !mViewDataBinding.pts3.isChecked)
                 ) {
                     priceAddTopUp = 0
                     showToast("Please Add Amount")
                 } else {*/
                if (priceAddTopUp != 0) {
                    presentPaymentSheet(priceAddTopUp)
                } else {
                    showToast("Please Add Amount")
                }
                /* }*/
            } else {

                showToast("Please select payment method")
            }

        }

        mViewDataBinding.btnpaypal.setOnClickListener {
            mViewDataBinding.radioPaypal.isChecked = true
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = false
        }

        mViewDataBinding.btnvisa.setOnClickListener {
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = true
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = false
        }

        mViewDataBinding.btnmaster.setOnClickListener {
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = true
            mViewDataBinding.radiogoogle.isChecked = false
        }

        mViewDataBinding.btnGoogle.setOnClickListener {
            mViewDataBinding.radioPaypal.isChecked = false
            mViewDataBinding.radioVisa.isChecked = false
            mViewDataBinding.radiomaster.isChecked = false
            mViewDataBinding.radiogoogle.isChecked = true
        }


        //
        initStripe()

        //

        mViewDataBinding.editText.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }


        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = false
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false
            if (mViewDataBinding.editText.text.toString().isEmpty()) {

                priceAddTopUp = 0
                mViewDataBinding.img.text = "0".toString()
            } else {
                mViewDataBinding.img.text = mViewDataBinding.editText.text.toString()
                priceAddTopUp = mViewDataBinding.editText.text.toString().toInt()
            }
        }


        override fun afterTextChanged(s: Editable) {

        }
    }


    private fun paymentAdapter() {
        paymentArrayList = ArrayList()
//        if (PrefHelper.getInstance(requireContext()).payment.equals(1)) {
//            paymentArrayList.add(
//                PaymentMethod(
//                    1,
//                    R.drawable.icon_master,
//                    getString(R.string.debit_card)
//                )
//            )
//            paymentArrayList.add(PaymentMethod(paymentId = 2, R.drawable.icon_cash, "Cash", true))
//        } else {
//            paymentArrayList.add(
//                PaymentMethod(
//                    1,
//                    R.drawable.icon_master,
//                    getString(R.string.debit_card),
//                    true
//                )
//            )
//            paymentArrayList.add(PaymentMethod(2, R.drawable.icon_cash, "Cash"))
//        }

        var payTypeVal = "1"
        val payTypeVal2 = "PrefHelper.getInstance(requireContext()).paymentMathod"

//        dataStoreProvider.paymentType.asLiveData().observe(viewLifecycleOwner) {
//            it?.let {
//                payTypeVal = it
//            }
//        }

        if (payTypeVal2 == "2") {
            paymentArrayList.add(
                PaymentMethod(
                    0, R.drawable.icon_master, getString(R.string.debit_card)
                )
            )
//            paymentArrayList.add(
//                PaymentMethod(
//                    paymentId = 1, R.drawable.paypal, "Paypal"
//                )
//            )
            paymentArrayList.add(
                PaymentMethod(
                    paymentId = 2, R.drawable.icon_cash, "Pay on Arrival", true
                )
            )
        } else if (payTypeVal2 == "1") {
            paymentArrayList.add(
                PaymentMethod(
                    0, R.drawable.icon_master, getString(R.string.debit_card)
                )
            )
//            paymentArrayList.add(
//                PaymentMethod(
//                    paymentId = 1, R.drawable.paypal, "Paypal", true
//                )
//            )
            paymentArrayList.add(
                PaymentMethod(
                    paymentId = 2, R.drawable.icon_cash, "Pay on Arrival"
                )
            )
        } else {
            paymentArrayList.add(
                PaymentMethod(
                    0, R.drawable.icon_master, getString(R.string.debit_card), true
                )
            )
//            paymentArrayList.add(
//                PaymentMethod(
//                    paymentId = 1, R.drawable.paypal, "Paypal"
//                )
//            )
            paymentArrayList.add(
                PaymentMethod(
                    paymentId = 2, R.drawable.icon_cash, "Pay on Arrival"
                )
            )
        }

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.paymentMethodRecyclerview.layoutManager = linearLayoutManager

        paymentAdapter = PaymentAdapter(paymentArrayList, this)
        mViewDataBinding.paymentMethodRecyclerview.adapter = paymentAdapter

    }

    override fun onTopSellerClick(position: Int, name: String) {
    }

    override fun onTopSellerSelectClick(position: Int, name: String) {
    }

    override fun InviteClicked() {

        findNavController().navigate(R.id.dashboardFragment, arguments, options)

    }


    ///////////////

    lateinit var paymentIntentClientSecret: String
    lateinit var stripPublicKey: String
    var selectionStr = ""
    var googlePayLauncher: GooglePayLauncher? = null
    private lateinit var paymentsClient: PaymentsClient
    lateinit var paymentSheet: PaymentSheet


    var priceAddTopUp = 0
    private fun initStripe() {


        /*mViewDataBinding.btnmaster.setOnClickListener {

        }*/

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


    }

    private fun presentPaymentSheet(shopId: Int) {
        val params = JsonObject()
        params.addProperty("amount", shopId)
        params.addProperty("topup", true)
        mViewModel.addTop(params)

        if (!mViewModel.addTopResponse.hasActiveObservers()) {

            mViewModel.addTopResponse.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let {
                            paymentIntentClientSecret = it.checkout ?: ""
                            stripPublicKey =
                                "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                            if (selectionStr.contains("google", true)) {
                                PrefHelper.getInstance(requireContext())
                                    .savaStripe("pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E")

                                presentGooglePaySheetIN(stripPublicKey, paymentIntentClientSecret)

                            } else {


                                paymentIntentClientSecret = it.checkout ?: ""
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
                                    merchantDisplayName = "Emirates-Quiz",
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
                            mViewModel.addTopResponse.removeObservers(viewLifecycleOwner)
                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
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
                AppConstants.showSnackBar("Cancel", mViewDataBinding.root)
            }

            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                Timber.tag("Error").d("hello there")
                AppConstants.showSnackBar("Error", mViewDataBinding.root)
            }

            is PaymentSheetResult.Completed -> {

                DialogHelperClass.topUpDialog(
                    requireContext(), this, true,priceAddTopUp.toString()
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

                DialogHelperClass.topUpDialog(
                    requireContext(), this, true,priceAddTopUp.toString()
                )

            }

            GooglePayLauncher.Result.Canceled -> {
                // User canceled the operation
                Timber.tag("Cancel").d("hello there")
                print("Canceled")
                AppConstants.showSnackBar("Cancel", mViewDataBinding.root)
                // User canceled the operation
            }

            is GooglePayLauncher.Result.Failed -> {
                // Operation failed; inspect `result.error` for the exception
                print("Error: ")
                Timber.tag("Error").d("hello there")
                AppConstants.showSnackBar("Error", mViewDataBinding.root)
                // Operation failed; inspect `result.error` for the exception
            }
        }


    }

////////////

}