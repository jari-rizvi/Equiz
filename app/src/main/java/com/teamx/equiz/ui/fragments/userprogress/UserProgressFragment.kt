package com.teamx.equiz.ui.fragments.userprogress

import android.animation.ObjectAnimator
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
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentTopUpBinding
import com.teamx.equiz.databinding.FragmentUserProgressBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener
import com.teamx.equiz.ui.fragments.loaderboard.LoaderBoardViewModel
import com.teamx.equiz.ui.fragments.topup.TopupViewModel
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserProgressFragment : BaseFragment<FragmentUserProgressBinding, LoaderBoardViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_user_progress
    override val viewModel: Class<LoaderBoardViewModel>
        get() = LoaderBoardViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel
    private lateinit var options: NavOptions
    var id: String = ""

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


        id = PrefHelper.getInstance(requireContext()).setUserId.toString()
        if (id.isNullOrEmpty()) {
            id = " "
        }

        mViewModel.getTopWinners(id)

        if (!mViewModel.getTopWinnersResponse.hasActiveObservers()) {
            mViewModel.getTopWinnersResponse.observe(requireActivity()) {
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
                            mViewDataBinding.simpleProgressBar.progress = data.game[0].speed
                            mViewDataBinding.textView31.text = data.game[0].speed.toString() + " %"
                            mViewDataBinding.simpleProgressBar.max = 100

                            val progressAnimator = ObjectAnimator.ofInt(data.game[0].speed, mViewDataBinding.simpleProgressBar.progress, 0, 100)
                            progressAnimator.duration = 1000

                            // Start the animation
                            progressAnimator.start()

                            mViewDataBinding.simpleProgressBar1.progress = data.game[0].judgment
                            mViewDataBinding.textView311.text =
                                data.game[0].judgment.toString() + " %"

                            mViewDataBinding.simpleProgressBar3.progress = data.game[0].accuracy
                            mViewDataBinding.textView313.text =
                                data.game[0].accuracy.toString() + " %"

                            mViewDataBinding.simpleProgressBar4.secondaryProgress = data.game[0].observation



                            mViewDataBinding.textView314.text =
                                data.game[0].observation.toString() + " %"

                            mViewDataBinding.simpleProgressBar2.progress = data.game[0].memory
                            mViewDataBinding.textView315.text =
                                data.game[0].memory.toString() + " %"

                            mViewDataBinding.simpleProgressBar5.progress = data.game[0].calculation
                            mViewDataBinding.textView312.text =
                                data.game[0].calculation.toString() + " %"


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
                            requireContext(), it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getTopWinnersResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

    }


}