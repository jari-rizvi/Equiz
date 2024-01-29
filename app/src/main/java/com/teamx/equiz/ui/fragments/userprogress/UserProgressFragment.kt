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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        mViewDataBinding.cc.setOnClickListener {

            GameModel?.let { it1 ->
                DialogHelperClass.chickenDialog(requireContext(),
                    object : DialogHelperClass.Companion.ChickenDialogCallBack {
                        override fun onCloseClick() {

                        }


                    }, gamesModel = it1
                ).show()
            }
        }

        mViewDataBinding.cc1.setOnClickListener {
            GameModel?.let { it1 ->
                DialogHelperClass.UserStatsDialog(requireContext(),
                    object : DialogHelperClass.Companion.ChickenDialogCallBack {
                        override fun onCloseClick() {

                        }


                    }, gamesModel = it1
                ).show()
            }
        }


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
                            val speed =
                                data.game[0].speed.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgressBar.secondaryProgress = speed.toInt()
                            Log.d("TAG", "speedspeed: $speed")
                            val roundoffspeed = Math.round(speed)

                            mViewDataBinding.textView31.text = "$roundoffspeed %"

                            val judgment =
                                data.game[0].judgment.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgressBar1.secondaryProgress = judgment.toInt()
                            val roundoffjudgment = Math.round(judgment)

                            mViewDataBinding.textView311.text = "$roundoffjudgment %"

                            val accuracy =
                                data.game[0].accuracy.toDouble() / data.game[0].level.Range.toDouble() * 100
                            val roundoffAccuracy = Math.round(accuracy)
                            mViewDataBinding.simpleProgressBar3.secondaryProgress = accuracy.toInt()
                            mViewDataBinding.textView313.text = "$roundoffAccuracy %"

                            val observation =
                                data.game[0].observation.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgressBar4.secondaryProgress =
                                observation.toInt()
                            val roundoffobservation = Math.round(observation)
                            mViewDataBinding.textView314.text = "$roundoffobservation %"

                            val memory =
                                data.game[0].memory.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgressBar5.secondaryProgress = memory.toInt()
                            val roundoffmemory = Math.round(memory)
                            mViewDataBinding.textView315.text = "$roundoffmemory %"

                            val calculation =
                                data.game[0].accuracy.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgressBar2.secondaryProgress =
                                calculation.toInt()
                            val roundoffcalculation = Math.round(calculation)

                            mViewDataBinding.textView312.text = "$roundoffcalculation %"

                            mViewDataBinding.getRank.text = data.game[0].rank.toString()
                            Log.d("TAG", "onViewCreated12112: ${data.game[0].rank}")


                            val chickenProgress =
                                data.game[0].totalScore.toDouble() / data.game[0].level.Range.toDouble() * 100
                            mViewDataBinding.simpleProgress.secondaryProgress =
                                chickenProgress.toInt()

                            GameModel= data.game[0]

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