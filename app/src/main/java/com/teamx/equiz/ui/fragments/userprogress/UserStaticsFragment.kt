package com.teamx.equiz.ui.fragments.userprogress

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
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
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.models.topWinnerData.TopWinnerData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentTopUpBinding
import com.teamx.equiz.databinding.FragmentUserProgressBinding
import com.teamx.equiz.databinding.FragmentUserStaticsBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener
import com.teamx.equiz.ui.fragments.loaderboard.LoaderBoardViewModel
import com.teamx.equiz.ui.fragments.topup.TopupViewModel
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserStaticsFragment : BaseFragment<FragmentUserStaticsBinding, LoaderBoardViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_user_statics
    override val viewModel: Class<LoaderBoardViewModel>
        get() = LoaderBoardViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel
    private lateinit var options: NavOptions
    var id: String = ""
    var GameModel: Game? = null

    @SuppressLint("SetTextI18n")
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

        sharedViewModel.setActiveUser("")

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }




        id = PrefHelper.getInstance(requireContext()).setUserId.toString()
        if (id.isNullOrEmpty()) {
            id = " "
        }



    }


}