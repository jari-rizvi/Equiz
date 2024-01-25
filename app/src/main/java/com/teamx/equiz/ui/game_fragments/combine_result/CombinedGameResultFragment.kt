package com.teamx.equiz.ui.game_fragments.combine_result

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.CombineResultGameFragmentBinding
import com.teamx.equiz.ui.game_fragments.game_random.RandomGameFragsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinedGameResultFragment :
    BaseFragment<CombineResultGameFragmentBinding, RandomGameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.combine_result_game_fragment
    override val viewModel: Class<RandomGameFragsViewModel>
        get() = RandomGameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.userStatsFragment, arguments, options)
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        mViewDataBinding.btnback.setOnClickListener {
            findNavController().navigate(R.id.userStatsFragment, arguments, options)
        }

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle.getString("gameName")

        val rightAnswer = bundle!!.getInt("rightAnswer", 0)
        val total = bundle!!.getInt("total", 0)

        Log.d("123123", "onViewCreated: $gameName ")
        Log.d("123123", "onViewCreated: $rightAnswer ")
        Log.d("123123", "onViewCreated: $total ")
        sharedViewModel.gameNameRight.add(rightAnswer.toDouble())
        sharedViewModel.gameNameTotal.add(total.toDouble())
        sharedViewModel.gameName.add(gameName.toString())


        Log.d("123123", "onViewCreated:$gameName ")


        val roundName = sharedViewModel.roundInteger.toString()

        Log.d("345345", "onViewCreated: $roundName")


        mViewDataBinding.simpleProgressBar.secondaryProgress =
            ((sharedViewModel.gameNameRight.get(0) / sharedViewModel.gameNameTotal.get(0)) * 100).toInt()
        mViewDataBinding.game1per.text =
            "${((sharedViewModel.gameNameRight.get(0) / sharedViewModel.gameNameTotal.get(0)) * 100).toInt()}%"
        mViewDataBinding.game1tv.text = "${sharedViewModel.gameName.get(0)}"

        mViewDataBinding.simpleProgressBar1.secondaryProgress =
            ((sharedViewModel.gameNameRight.get(1) / sharedViewModel.gameNameTotal.get(1)) * 100).toInt()
        mViewDataBinding.game2per.text =
            "${((sharedViewModel.gameNameRight.get(1) / sharedViewModel.gameNameTotal.get(1)) * 100).toInt()}%"
        mViewDataBinding.game2tv.text = "${sharedViewModel.gameName.get(1)}"
        if (sharedViewModel.gameName.size > 2) {
            mViewDataBinding.simpleProgressBar2.secondaryProgress =
                ((sharedViewModel.gameNameRight.get(2) / sharedViewModel.gameNameTotal.get(2)) * 100).toInt()
            mViewDataBinding.game3per.text =
                "${((sharedViewModel.gameNameRight.get(2) / sharedViewModel.gameNameTotal.get(2)) * 100).toInt()}%"
            mViewDataBinding.game3tv.text = "${sharedViewModel.gameName.get(2)}"
        }



        sharedViewModel.roundInteger = 0
        sharedViewModel.gameName = arrayListOf()
        sharedViewModel.gameNameRight = arrayListOf()
        sharedViewModel.gameNameTotal = arrayListOf()
        sharedViewModel.gamesRightScore = 0
        sharedViewModel.gamesTotalScore = 0

    }
}