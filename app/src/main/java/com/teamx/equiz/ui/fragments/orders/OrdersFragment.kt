package com.teamx.equiz.ui.fragments.orders


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentOrdersBinding
import com.teamx.equiz.databinding.FragmentProfileBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_orders
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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


        setupViewPager()
        setupTabLayout()

    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(requireActivity(), 3)
        mViewDataBinding.viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            mViewDataBinding.tabLayout, mViewDataBinding.viewPager
        ) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "Delivered"
                }

                1 -> {
                    tab.text = "Processing"
                }

                2 -> {
                    tab.text = "Cancelled"
                }
            }


        }.attach()
    }
}