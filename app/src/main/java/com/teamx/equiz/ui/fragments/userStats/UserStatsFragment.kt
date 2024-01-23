package com.teamx.equiz.ui.fragments.userStats


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
import androidx.core.content.ContextCompat
import com.teamx.equiz.databinding.FragmentUserStatsBinding
import com.teamx.equiz.ui.fragments.orders.ViewPagerAdapter

@AndroidEntryPoint
class UserStatsFragment : BaseFragment<FragmentUserStatsBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_user_stats
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

        setupViewPager()
        setupTabLayout()

    }

    private fun setupViewPager() {
        val adapter = UserStatsViewPagerAdapter(requireActivity(), 4)
        mViewDataBinding.viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            mViewDataBinding.tabLayout, mViewDataBinding.viewPager
        ) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.homee_selector
                    )
                }

                1 -> {
                    tab.text = "Ranking"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ranking_selector
                    )
                }

                2 -> {
                    tab.text = "Statics"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.stats_selector
                    )

                }

                3 -> {
                    tab.text = "Games"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.game_selector
                    )

                }
            }


        }.attach()
    }
}