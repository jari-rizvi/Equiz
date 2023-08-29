package com.teamx.equiz.ui.fragments.notifications


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentNotificationsBinding
import com.teamx.equiz.databinding.FragmentOrdersBinding
import com.teamx.equiz.databinding.FragmentProfileBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.orders.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_notifications
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
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

        setupViewPager()
        setupTabLayout()

    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(requireActivity(), 3)
        mViewDataBinding.viewPagerNotification.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            mViewDataBinding.tabLayout, mViewDataBinding.viewPagerNotification
        ) { tab, position ->

            when (position) {
                0 -> {
                    
                    tab.text = "All"
                }

                1 -> {
                    tab.text = "Unread"
                }

                2 -> {
                    tab.text = "Read"
                }
            }


        }.attach()
    }
}