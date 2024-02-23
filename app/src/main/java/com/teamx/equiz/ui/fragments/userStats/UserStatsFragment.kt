package com.teamx.equiz.ui.fragments.userStats


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.databinding.FragmentUserStatsBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

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

        if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                "null",
                true
            )
        ) {
            DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
            return
        }



        Log.d("UserStatsFragment", "setupViewPager: onViewCreated")

        setupViewPager()


    }

    override fun onResume() {
        super.onResume()
        Log.d("UserStatsFragment", "setupViewPager: onResume")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    private fun setupViewPager() {
        val adapter = UserStatsViewPagerAdapter(requireActivity(), 4)

        mViewDataBinding.viewPager.adapter = adapter
//        mViewDataBinding.viewPager.isUserInputEnabled = false;
        mViewDataBinding.viewPager.setCurrentItem(sharedViewModel.stateOfGameFrags,false)
        Log.d("345345", "setupViewPager:${sharedViewModel.stateOfGameFrags} ")
        setupTabLayout()

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
                    Log.d("345345", "setupTabLayout: ")
                    tab.text = "Games"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.game_selector
                    )

                }
            }


        }.attach()

        mViewDataBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                sharedViewModel.stateOfGameFrags = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

//        mViewDataBinding.tabLayout.setScrollPosition(sharedViewModel.stateOfGameFrags, 0f, true)
        val tab = mViewDataBinding.tabLayout.getTabAt(sharedViewModel.stateOfGameFrags)
        tab?.select()

//        mViewDataBinding.tabLayout.setScrollPosition(sharedViewModel.stateOfGameFrags,0f,true);
//        mViewDataBinding.tabLayout.selectTab(tab)

    }
}