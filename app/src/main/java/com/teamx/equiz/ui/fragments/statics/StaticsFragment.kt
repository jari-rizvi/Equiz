package com.teamx.equiz.ui.fragments.statics

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.staticsData.StaticsData
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoaderBoardBinding
import com.teamx.equiz.databinding.FragmentStaticsBinding
import com.teamx.equiz.ui.fragments.loaderboard.adapter.LoaderMultiViewAdapter
import com.teamx.equiz.ui.fragments.loaderboard.adapter.OnUserClickListner
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StaticsFragment : BaseFragment<FragmentStaticsBinding, StaticsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_statics
    override val viewModel: Class<StaticsViewModel>
        get() = StaticsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var staticsData: StaticsData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }



        if (!mViewModel.getUserStaticsResponse.hasActiveObservers()) {
            mViewModel.getUserStatics()
            changeObserver(0)
        }






        mViewDataBinding.tabLayout2.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Get the selected tab position
                // Perform actions based on the selected tab


                Log.d("staticsData", "onTabSelected: ${staticsData}")
                tab?.position?.let { updateLayout(staticsData,it) }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }


    fun changeObserver(index: Int) {
        mViewModel.getUserStaticsResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
//                        loadingDialog.show()
                } /* Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }*/
                Resource.Status.NOTVERIFY -> {
                    loadingDialog.dismiss()
                }

                Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                    it.data?.let { data ->


                        staticsData = data
                        updateLayout(staticsData,index)
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
//                        loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(
                        requireContext(),
                        it.message!!
                    )
                }
            }

        }
    }

    fun updateLayout(staticsData: StaticsData, index:Int){
        var wins = 0.0
        var losses = 0.0
        var percentage = 0
        var highScore = 0.0

        if (index == 0) {
            wins = staticsData.todayAccumulator.wins?:0.0
            losses = staticsData.todayAccumulator.losses?:0.0
            percentage = (wins / (wins + losses) * 100).toInt()
            highScore = staticsData.todayAccumulator.total?:0.0

        } else if (index == 1) {
            wins = staticsData.totalAccumulator.wins?:0.0
            losses = staticsData.totalAccumulator.losses?:0.0
            percentage = (wins / (wins + losses) * 100).toInt()
            highScore = staticsData.totalAccumulator.total?:0.0
        }


        mViewDataBinding.simpleProgressBar.progress = percentage
//        mViewDataBinding.percentTxt.text = "${"${percentage}".substring(0, 2)}%"
        mViewDataBinding.percentTxt.text = "$percentage%"

        mViewDataBinding.textView78.text = "Wins\n${wins}"
        mViewDataBinding.textView77.text = "Losses\n${losses}"
        mViewDataBinding.textView7787.text = "High Score\n${highScore}"
    }

}