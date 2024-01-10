package com.teamx.equiz.ui.fragments.collectPrice

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentRewardsBinding
import com.teamx.equiz.ui.fragments.chances.adapter.ChancesAdapter
import com.teamx.equiz.ui.fragments.collectPrice.adapter.RewardsAdapter
import com.teamx.equiz.ui.fragments.collectPrice.data.Raffle
import com.teamx.equiz.ui.fragments.collectPrice.data.WinnerData
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition

@AndroidEntryPoint
class RewardsFragment : BaseFragment<FragmentRewardsBinding, CollectPriceViewModel>(),
    RewardsAdapter.OnRewardClick {

    override val layoutId: Int
        get() = R.layout.fragment_rewards
    override val viewModel: Class<CollectPriceViewModel>
        get() = CollectPriceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var rewardsAdapter: RewardsAdapter
    lateinit var rewardsModelData: ArrayList<Raffle>

    @RequiresApi(Build.VERSION_CODES.O)
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

            mViewDataBinding.btnback.setOnClickListener {
                popUpStack()
            }
            rewardsRec()

            mViewModel.claimedPrize("true")
            if (!mViewModel.claimedPrizeResponse.hasActiveObservers()) {
                mViewModel.claimedPrizeResponse.observe(requireActivity()) {
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

                                data.winnerData.forEach {
                                    rewardsModelData.add(it.raffle)
                                }

                                rewardsAdapter.notifyDataSetChanged()


                            }
                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
                            onToSignUpPage()
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            if (isAdded) {
                                mViewDataBinding.root.snackbar(it.message!!)
                            }
                            Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                        }
                    }
                }
            }
        }


    }

    private fun rewardsRec() {
        rewardsModelData = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recRewards.layoutManager = linearLayoutManager

        rewardsAdapter = RewardsAdapter(rewardsModelData, this)
        mViewDataBinding.recRewards.adapter = rewardsAdapter

    }

    override fun onRewardClick(position: Int) {
        var img = rewardsModelData[position].prize.image

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putString("img", img)
        findNavController().navigate(
            R.id.rewardShareFragment, arguments, options
        )

    }

}