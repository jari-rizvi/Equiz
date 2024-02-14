package com.teamx.equiz.ui.fragments.wallet


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentWalletBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.play.integrity.internal.t

import java.text.SimpleDateFormat;
import java.util.Calendar
import java.util.Date;
import java.util.Locale;

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel>(),DialogHelperClass.Companion.DialogDateCallBack {

    override val layoutId: Int
        get() = com.teamx.equiz.R.layout.fragment_wallet
    override val viewModel: Class<WalletViewModel>
        get() = WalletViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var walletAdapter: WalletAdapter
    lateinit var walletArrayList: ArrayList<Transaction>
    private lateinit var options: NavOptions
    var cal = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = com.teamx.equiz.R.anim.enter_from_left
                exit = com.teamx.equiz.R.anim.exit_to_left
                popEnter = com.teamx.equiz.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.equiz.R.anim.nav_default_pop_exit_anim
            }
        }
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewDataBinding.textView9.setOnClickListener {
            findNavController().navigate(
                com.teamx.equiz.R.id.action_walletFragment_to_referralFragment,
                arguments,
                options
            )
        }

        mViewDataBinding.btnTopUp.setOnClickListener {
            findNavController().navigate(
                com.teamx.equiz.R.id.action_walletFragment_to_topupFragment,
                arguments,
                options
            )

        }


        mViewDataBinding.btnFilter.setOnClickListener {
            DialogHelperClass.DatePickerDialog(requireContext(),this,true)

        }


        mViewModel.getWallet()

        if (!mViewModel.getwalletResponse.hasActiveObservers()) {
            mViewModel.getwalletResponse.observe(requireActivity()) {
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
                            walletArrayList.clear()
                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.root.visibility = View.VISIBLE

                            mViewDataBinding.shimmerLayout.stopShimmer()
                            mViewDataBinding.textView10.text = data.data.toString() + " Points"

                            data.transactions.forEach {
                                walletArrayList.add(it)
                                mViewDataBinding.textView11.text =
                                    it.points.toString() + " Expires at " + it.expiresAt.substringBefore(
                                        "T"
                                    )

                            }

                            walletAdapter.notifyDataSetChanged()


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
                    mViewModel.getwalletResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        walletRecyclerview()


        mViewDataBinding.swiperefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mViewDataBinding.swiperefresh.isRefreshing = false
            RearrangeData()
        })

    }


    private fun RearrangeData() {
        mViewModel.getWallet()
    }


    private fun walletRecyclerview() {
        walletArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.walletrecycler.layoutManager = linearLayoutManager

        walletAdapter = WalletAdapter(walletArrayList)
        mViewDataBinding.walletrecycler.adapter = walletAdapter

    }

    override fun startDate(sDate : String) {
        Log.d("TAG", "endDate: ")

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            }

        DatePickerDialog(
            requireContext(), dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun endDate(eDate: String) {
        Log.d("TAG", "endDate: ")

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                updateDateInView()
            }

        DatePickerDialog(
            requireContext(), dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }



}