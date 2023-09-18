package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.databinding.FragmentPaymentMethodsBinding
import com.teamx.equiz.ui.fragments.ecommerce.home.EcommerceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMethodsFragment : BaseFragment<FragmentPaymentMethodsBinding, PaymentMethodsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_payment_methods
    override val viewModel: Class<PaymentMethodsViewModel>
        get() = PaymentMethodsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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




    }
}