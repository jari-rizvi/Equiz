package com.teamx.equiz.ui.fragments.topup

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.PaymentMethod
import com.teamx.equiz.databinding.FragmentTopUpBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        paymentAdapter()

        mViewDataBinding.pts1.setOnClickListener {
            mViewDataBinding.pts6.isChecked = false
            mViewDataBinding.pts1.isChecked = true
            mViewDataBinding.pts2.isChecked = false
            mViewDataBinding.pts3.isChecked = false
            mViewDataBinding.pts4.isChecked = false
            mViewDataBinding.pts5.isChecked = false

            amount = "100"
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
            mViewDataBinding.img.text = amount
        }

        mViewDataBinding.textView445.setOnClickListener {
            DialogHelperClass.topUpDialog(
                requireContext(), this, true
            )
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

    }

}