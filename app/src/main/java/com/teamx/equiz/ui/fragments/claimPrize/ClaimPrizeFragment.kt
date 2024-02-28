package com.teamx.equiz.ui.fragments.claimPrize


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
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
import com.teamx.equiz.databinding.FragmentClaimprizeBinding
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetAddressFragment
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetListener
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetStripeListener
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomStripeFragment
import com.teamx.equiz.ui.fragments.subscription.SubscriptionViewModel
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class ClaimPrizeFragment : BaseFragment<FragmentClaimprizeBinding, ClaimPrizeViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_claimprize
    override val viewModel: Class<ClaimPrizeViewModel>
        get() = ClaimPrizeViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var stripe: Stripe

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



        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }

        val winnerid =  bundle.getString("winnerid").toString()

        Log.d("TAG", "onViewCreated: $winnerid")


        mViewDataBinding.radioChances.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = false
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioChances.isChecked = true
            mViewDataBinding.raffles.visibility = View.VISIBLE

        }

        mViewDataBinding.radioTangible.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = true
            mViewDataBinding.radioWallet.isChecked = false
            mViewDataBinding.radioChances.isChecked = false
            mViewDataBinding.raffles.visibility = View.GONE
        }
        mViewDataBinding.radioWallet.setOnClickListener {
            mViewDataBinding.radioTangible.isChecked = false
            mViewDataBinding.radioWallet.isChecked = true
            mViewDataBinding.radioChances.isChecked = false
            mViewDataBinding.raffles.visibility = View.GONE
        }


        mViewDataBinding.btnSocialFb.setOnClickListener {
            navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host_fragment
            )
            navController.navigate(
                R.id.chattFragment, null, options
            )
        }


    }


}