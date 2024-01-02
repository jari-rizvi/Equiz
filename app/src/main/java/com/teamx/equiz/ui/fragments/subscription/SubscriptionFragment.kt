package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stripe.android.model.PaymentMethodCreateParams
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.CardParams
import com.stripe.android.model.PaymentMethod
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


        mViewDataBinding.btnBuy.setOnClickListener {
            bottomSheetBehavior =
                BottomSheetBehavior.from(mViewDataBinding.bottomSheetLayout.bottomSheetStripe)

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

            val state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED
                else BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.state = state


        }

        //new work
        mViewDataBinding.bottomSheetLayout.buttonCreatePaymentMethod.setOnClickListener {

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
                        onToSignUpPage()
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

}