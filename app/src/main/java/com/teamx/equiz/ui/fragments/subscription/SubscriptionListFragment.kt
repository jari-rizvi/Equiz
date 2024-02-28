package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.view.CardInputListener
import com.stripe.android.view.CardInputWidget
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.databinding.FragmentSubscriptionListBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetAddressFragment
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetListener
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetStripeListener
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomStripeFragment
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class SubscriptionListFragment : BaseFragment<FragmentSubscriptionListBinding, SubscriptionViewModel>(){

    override val layoutId: Int
        get() = R.layout.fragment_subscription_list
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel



    private lateinit var options: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


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
        }


    }


}