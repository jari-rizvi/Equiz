package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.view.CardInputWidget
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetStripeListener
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomStripeFragment
import com.teamx.equiz.ui.fragments.coupons.CouponsAdapter
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Attribute
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Plan
import com.teamx.equiz.ui.fragments.subscription.catPlansData.Data
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import kotlin.properties.Delegates

@AndroidEntryPoint
class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding, SubscriptionViewModel>(),
    BottomSheetStripeListener, OnCatPlanListener {

    override val layoutId: Int
        get() = R.layout.fragment_subscription
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var bottomStripeFragment: BottomStripeFragment
    private var bottomSheetStripeListener: BottomSheetStripeListener? = null
    lateinit var cardParams: PaymentMethodCreateParams


    private lateinit var options: NavOptions
    private lateinit var stripe: Stripe

     var planid = ""
    var reoccur by Delegates.notNull<Boolean>()
//     var reoccurValue =true
    lateinit var catAdapter: CatPlansAdapter
    lateinit var subCatAdapter: SubCatPlanAdapter

    lateinit var catArrayList: ArrayList<Plan>

    lateinit var subCatArrayList: ArrayList<Attribute>


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

        sharedViewModel.setActiveUser("")

        catPlansRecyclerview()
        SubCatPlansRecyclerview()


//        val cardInputWidget: CardInputWidget = view.findViewById(R.id.cardInputWidget)
//        val btnPay: Button = view.findViewById(R.id.btnPay)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }


        if (isAdded) {
            paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        }


        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }



        val id = arguments?.getString("subscription")
        val title = arguments?.getString("title")


        mViewDataBinding.textView.text = "${title + " Plan"}"


        if (id != null) {
            mViewModel.getCatPlanbyId(id)
        }

        if (!mViewModel.getCatPlanbyIdResponse.hasActiveObservers()) {
            mViewModel.getCatPlanbyIdResponse.observe(requireActivity()) {
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

                            catArrayList.clear()
                            subCatArrayList.clear()

                           /* data.plans.forEach {
                                catArrayList.add(it)
                            }
*/
                            data.attributes.forEach {
                                subCatArrayList.add(it)
                            }

                            data.plans.filter { it.isActive }.forEach {
                                catArrayList.add(it)
                            }

                            catAdapter.notifyDataSetChanged()
                            subCatAdapter.notifyDataSetChanged()

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
                    mViewModel.getCatPlanbyIdResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

//        mViewDataBinding.btnBuy.setOnClickListener {
//
//
//            bottomSheetBehavior = BottomSheetBehavior.from(mViewDataBinding.bottomsheet.bottomSheetStripe)
//            bottomSheetBehavior.addBottomSheetCallback(object :
//                BottomSheetBehavior.BottomSheetCallback() {
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//                }
//
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_EXPANDED -> MainActivity.bottomNav?.visibility =
//                            View.GONE
//
//                        BottomSheetBehavior.STATE_COLLAPSED -> MainActivity.bottomNav?.visibility =
//                            View.VISIBLE
//
//                        else -> "Persistent Bottom Sheet"
//                    }
//                }
//            })
//
//            val state =
//                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED
//                else BottomSheetBehavior.STATE_EXPANDED
//            bottomSheetBehavior.state = state
//
//        }
//        mViewDataBinding.btnBuy.setOnClickListener {
//
//
//            if (!bottomStripeFragment.isAdded) {
//                bottomStripeFragment.show(
//                    parentFragmentManager,
//                    bottomStripeFragment.tag
//                )
//            }
//        }
        bottomStripeFragment = BottomStripeFragment()
        bottomStripeFragment.initInterface(this)



        PaymentConfiguration.init(
            requireContext(),
            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
        )

        stripe = Stripe(
            requireContext(),
            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
        )

        mViewDataBinding.btnBuy.setOnClickListener {
            if(planid.isNullOrEmpty()){
                mViewDataBinding.root.snackbar("Please Select Plan")
            }
           else{
            val params = JsonObject()
            try {
                params.addProperty("planId", planid)
                params.addProperty("reoccurred", mViewDataBinding.cbPolicy.isChecked)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mViewModel.buySubscription(params)

            if (!mViewModel.buySubscriptionResponse.hasActiveObservers()) {
                mViewModel.buySubscriptionResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.NOTVERIFY -> {
                            loadingDialog.dismiss()
                            if (isAdded) {
                                mViewDataBinding.root.snackbar(it.message!!)
                            }

                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->


                                showStripeSheet(data.subscriber.client_secret)


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
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.buySubscriptionResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }
            }
        }


        val route = bundle?.getString("routeSubs")
        if (route.equals("setting", true)) {

        } else {
            mViewDataBinding.unsub.visibility = View.VISIBLE
            mViewDataBinding.btnBuy.visibility = View.GONE
        }


        /*        mViewDataBinding.unsub.setOnClickListener {
                    DialogHelperClass.unsubUserDialog(requireContext(),
                        object : DialogHelperClass.Companion.DeleteUserDialogCallBack {
                            override fun onSignInClick1() {

                            }

                            override fun onSignUpClick1() {
                                mViewModel.unsub()
                                if (!mViewModel.unsubResponse.hasActiveObservers()) {
                                    mViewModel.unsubResponse.observe(requireActivity()) {
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

                                                    try {
        //                                mViewDataBinding.root.snackbar(data)
                                                        mViewDataBinding.root.snackbar("Subcription will end at the end of the Month")


                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
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
                                                if (isAdded) {
                                                    mViewDataBinding.root.snackbar(it.message!!)
                                                }
                                                Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                                            }
                                        }
                                    }
                                }


                            }

                        }).show()
                }*/


        /*    mViewModel.getPlan()

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
                                *//*    data.data.forEach {
                                    couponsArrayList.add(it)
                                }*//*

                            val divide = "${data.dataObject.amount / 100}" + "AED"

                            mViewDataBinding.textView61.text = divide.toString()


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
                    mViewModel.getPlanResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }*/


//        if (!mViewModel.subPlanResponse.hasActiveObservers()) {
//            mViewModel.subPlanResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.NOTVERIFY -> {
//                        loadingDialog.dismiss()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//
//
//                            if (cardParams != null) {
//                                val params =
//                                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
//                                        cardParams, "${data.subData.client_secret}"
//                                    )
//
//
//                                stripe.confirmPayment(requireActivity(), params)
//                                bottomStripeFragment.dismiss()
//                                mViewDataBinding.root.snackbar("Successfully Subscribed")
//                                findNavController().popBackStack()
//
//                                /*   object : ApiResultCallback<PaymentIntentResult> {
//                                        override fun onSuccess(result: PaymentIntentResult) {
//                                            val paymentIntent: PaymentIntent? = result.intent
//                                            // Handle the PaymentIntent object
//                                            if (paymentIntent?.status == StripeIntent.Status.Succeeded) {
//                                                // Payment succeeded, perform necessary actions
//                                            } else {
//                                                // Payment failed or was canceled, handle accordingly
//                                            }
//                                        }
//
//                                        override fun onError(e: Exception) {
//                                            // Handle errors
//                                        }
//                                    }*/
//                            }
//
//
//                        }
//                    }
//
//                    Resource.Status.AUTH -> {
//                        loadingDialog.dismiss()
//                        if (isAdded) {
//                            try {
//                                onToSignUpPage()
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        DialogHelperClass.errorDialog(
//                            requireContext(),
//                            it.message!!
//                        )
//                    }
//                }
//                if (isAdded) {
//                    mViewModel.subPlanResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
//                }
//            }
//        }

    }


    override fun onBtnPay(cardInputWidget: CardInputWidget) {
        cardParams = cardInputWidget.paymentMethodCreateParams!!



        if (cardParams != null) {

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val paymentMethod = Stripe(
                        requireContext(),
                        "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
                    ).createPaymentMethodSynchronous(cardParams)

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


    private lateinit var paymentSheet: PaymentSheet

    private var paymentMethodid = ""

    private var amount = ""

    private var selectedPaymentMethod = PaymentMethod.STRIPE_SAVED_PAYMENT
    private fun showStripeSheet(clientSecret: String) {
        PaymentConfiguration.init(
            requireActivity().applicationContext,
//            stripPublicKey
            "pk_test_51L1UVCGn3F7BuM88wH1PSuNgc9bX7tq0MkIMB2HU2BbScX3i7VgZw4V8nimfe1zUEF8uQ3Q6PFbzrMacvH5PfA7900PaBHO20E"
        )

        paymentSheet.presentWithPaymentIntent(
            clientSecret, PaymentSheet.Configuration(
                merchantDisplayName = "Emirates-Quiz",
//                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("placeOrderResponse", "onPaymentSheetResult: Canceled")
            }

            is PaymentSheetResult.Failed -> {
                Log.d("placeOrderResponse", "onPaymentSheetResult: Failed")
            }

            is PaymentSheetResult.Completed -> {
                findNavController().popBackStack()
                Log.d("placeOrderResponse", "onPaymentSheetResult: Completed")
//                DialogHelperClass.wallettDialog(requireActivity(), amount, this)
//                if (isAdded) {
//                    findNavController().navigate(R.id.action_checkOutFragment_to_orderPlacedFragment)
//                }
            }
        }
    }


    private fun catPlansRecyclerview() {
        catArrayList = ArrayList()

        val linearLayoutManager = GridLayoutManager(context, 2)
        mViewDataBinding.recCatPlans.layoutManager = linearLayoutManager

        catAdapter = CatPlansAdapter(catArrayList, this)
        mViewDataBinding.recCatPlans.adapter = catAdapter

    }

    private fun SubCatPlansRecyclerview() {
        subCatArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recCatPlans1.layoutManager = linearLayoutManager

        subCatAdapter = SubCatPlanAdapter(subCatArrayList)
        mViewDataBinding.recCatPlans1.adapter = subCatAdapter

    }

    enum class PaymentMethod {
        CASH_ON_DELIVERY,
        STRIPE_PAYMENT,
        STRIPE_SAVED_PAYMENT,
        PAYPAL
    }

    override fun onPlanClick(position: Int, PrePos: Int) {
        planid = catArrayList[position]._id
        reoccur = catArrayList[position].allowedReoccurring
        mViewDataBinding.cbPolicy.isChecked = false

        if(reoccur){
            mViewDataBinding.cbPolicy.visibility = View.VISIBLE
            mViewDataBinding.tvPolicy.visibility = View.VISIBLE
        }
        else{
            mViewDataBinding.cbPolicy.visibility = View.GONE
            mViewDataBinding.tvPolicy.visibility = View.GONE
            mViewDataBinding.cbPolicy.isChecked = true
        }


      /*  if(mViewDataBinding.cbPolicy.visibility == View.VISIBLE){
                reoccurValue = mViewDataBinding.cbPolicy.isChecked
        }
        else{
            reoccurValue = true
        }*/



        catArrayList.forEach {
            it.isChecked = false
        }


        catArrayList[position].isChecked = true
        mViewDataBinding.recCatPlans.adapter?.notifyDataSetChanged()


    }
}