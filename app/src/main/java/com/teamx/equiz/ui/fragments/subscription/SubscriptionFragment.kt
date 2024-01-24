package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.view.CardInputWidget
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding, SubscriptionViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_subscription
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        val bottomSheetStripe: ConstraintLayout = view.findViewById(R.id.bottomSheetStripe)
        val cardInputWidget: CardInputWidget = view.findViewById(R.id.cardInputWidget)
        val btnPay: Button = view.findViewById(R.id.btnPay)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetStripe)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }


        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }



        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> MainActivity.bottomNav?.visibility =
                        View.GONE

                    BottomSheetBehavior.STATE_COLLAPSED -> MainActivity.bottomNav?.visibility =
                        View.VISIBLE

                    else -> "Persistent Bottom Sheet"
                }
            }
        })




        mViewDataBinding.btnBuy.setOnClickListener {

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED


//            val state =
//                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED
//                else BottomSheetBehavior.STATE_EXPANDED
//            bottomSheetBehavior.state = state


        }



        PaymentConfiguration.init(
            requireContext(),
            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
        )

        val stripe = Stripe(
            requireContext(),
            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
        )

        //new work
        btnPay.setOnClickListener {


            val cardParams = cardInputWidget.paymentMethodCreateParams



            if (cardParams != null) {

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val paymentMethod = Stripe(
                            requireContext(),
                            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                        )
                            .createPaymentMethodSynchronous(cardParams)

                        // Handle the PaymentMethod object
                        if (paymentMethod != null) {

                            val paymentMethodId = paymentMethod.id.orEmpty()

                            val params = JsonObject()
                            try {
                                params.addProperty("payment_methodId", paymentMethodId)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            mViewModel.subPlan(params)

                        } else {
                            // Handle null PaymentMethod
                        }
                    } catch (e: Exception) {
                        // Handle exceptions
                        e.printStackTrace()
                    }
                }


            } else {
                if (isAdded) {
                    mViewDataBinding.root.snackbar("Please add details")
                }
            }
        }

        mViewModel.getPlan()

        if (!mViewModel.getPlanResponse.hasActiveObservers()) {
            mViewModel.getPlanResponse.observe(requireActivity()) {
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
                            /*    data.data.forEach {
                                    couponsArrayList.add(it)
                                }*/

                            val divide = "${data.dataObject.amount / 100}"+"AED"

                            mViewDataBinding.textView61.text = divide.toString()


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
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getPlanResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }
        if (!mViewModel.subPlanResponse.hasActiveObservers()) {
            mViewModel.subPlanResponse.observe(requireActivity()) {
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

                            val cardParams =
                                cardInputWidget.paymentMethodCreateParams

                            if (cardParams != null) {
                                val params =
                                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                                        cardParams, "${data.subData.client_secret}"
                                    )


                                stripe.confirmPayment(requireActivity(), params)

                                /*   object : ApiResultCallback<PaymentIntentResult> {
                                        override fun onSuccess(result: PaymentIntentResult) {
                                            val paymentIntent: PaymentIntent? = result.intent
                                            // Handle the PaymentIntent object
                                            if (paymentIntent?.status == StripeIntent.Status.Succeeded) {
                                                // Payment succeeded, perform necessary actions
                                            } else {
                                                // Payment failed or was canceled, handle accordingly
                                            }
                                        }

                                        override fun onError(e: Exception) {
                                            // Handle errors
                                        }
                                    }*/
                            }


                        }
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
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.subPlanResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


    }


//    private fun createPaymentMethod() {
//        // Get card details from EditText widgets
//        val cardNumber = mViewDataBinding.bottomSheetLayout.editTextCardNumber.text.toString()
//        val expiryDate = mViewDataBinding.bottomSheetLayout.editTextExpiryDate.text.toString()
//        val cvc = mViewDataBinding.bottomSheetLayout.editTextCvc.text.toString()
//
//        // Create a Card instance with card details
//        val card = Card.create(
//            cardNumber,
//            expiryDate.substring(0, 2).toInt(),
//            expiryDate.substring(3).toInt(),
//            cvc
//        )
//
//        // Create a PaymentMethod instance using the Card
//        val paymentMethod = PaymentMethodCreateParams.create(
//            PaymentMethodCreateParams.create(
//                card?.toPaymentMethodParamsCard(),
//                null
//            )
//        )
//
//        // Optionally, attach the PaymentMethod to a customer
//        val customerId = "your_customer_id" // Replace with your actual customer ID
//        paymentMethod?.let {
//            it.attach(
//                PaymentMethod.BillingDetails.Builder()
//                    .setEmail("customer@example.com") // Replace with the customer's email
//                    .build(),
//                customerId
//            )
//        }
//
//        // Show loading indicator
//        mViewDataBinding.bottomSheetLayout.progressBar.visibility = ProgressBar.VISIBLE
//
//        // Perform the operation in a coroutine to avoid blocking the UI thread
//        GlobalScope.launch(Dispatchers.IO) {
//            // Simulate network delay (replace this with your actual network call)
//            Thread.sleep(2000)
//
//            // Get the PaymentMethod ID
//            val paymentMethodId = paymentMethod?.id
//
//            // Update UI on the main thread
//            launch(Dispatchers.Main) {
//                // Hide loading indicator
//                mViewDataBinding.bottomSheetLayout.progressBar.visibility = ProgressBar.INVISIBLE
//
//                // Update result TextView
//                if (paymentMethodId != null) {
//                    mViewDataBinding.bottomSheetLayout.textViewResult.text =
//                        "PaymentMethod ID: $paymentMethodId"
//                    mViewDataBinding.bottomSheetLayout.textViewResult.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            android.R.color.holo_green_dark
//                        )
//                    )
//                } else {
//                    mViewDataBinding.bottomSheetLayout.textViewResult.text =
//                        "PaymentMethod creation failed"
//                    mViewDataBinding.bottomSheetLayout.textViewResult.setTextColor(
//                        ContextCompat.getColor(
//                            requireContext(),
//                            android.R.color.holo_red_dark
//                        )
//                    )
//                }
//            }
//        }
//    }


//    fun subscribeSuccess(subData: SubsModel) {
//        subData?.client_secret?.let { clientSecret ->
//            STPPaymentHandler.getInstance().confirmPayment(
//                STPPaymentIntentParams.createWithClientSecret(clientSecret),
//                this
//            ) { status, paymentIntent, error ->
//                when (status) {
//                    STPPaymentHandlerStatus.Succeeded -> {
//                        println("Payment succeeded")
//                        runOnUiThread {
//                            val vc: PaySuccessView = AppRouter.instantiateViewController(
//                                storyboard = .ecommerce
//                            )
//                            vc.decide = .subs
//                                    route(to = vc, navigation = .modal)
//                        }
//                    }
//                    STPPaymentHandlerStatus.Failed -> {
//                        // Payment failed, handle the error
//                        val errorMessage = error?.localizedDescription ?: "Unknown error"
//                        println("Payment failed: $errorMessage")
//                        runOnUiThread {
//                            showToast(message = errorMessage)
//                        }
//                    }
//                    STPPaymentHandlerStatus.Canceled -> {
//                        // Payment canceled by the user
//                        println("Payment canceled")
//                        runOnUiThread {
//                            showToast(message = "Payment canceled")
//                        }
//                    }
//                    else -> {
//                        // Handle other cases if needed
//                        throw RuntimeException("Unhandled status")
//                    }
//                }
//            }
//        }
//    }


}