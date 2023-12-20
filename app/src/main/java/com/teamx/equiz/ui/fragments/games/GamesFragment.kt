package com.teamx.equiz.ui.fragments.games


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentGamesBinding
import com.teamx.equiz.ui.fragments.dashboard.DashboardFragment.Companion.returnImg
import com.teamx.equiz.ui.fragments.dashboard.GamesModel
import com.teamx.equiz.ui.fragments.dashboard.GamesUID2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding, GamesViewModel>(), AllGameInterface {

    override val layoutId: Int
        get() = R.layout.fragment_games
    override val viewModel: Class<GamesViewModel>
        get() = GamesViewModel::class.java
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

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        initializeGameAdapter()
    }

    private lateinit var gameStrArrayList: ArrayList<GamesModel>
    private lateinit var gameAdapter: AllGamesAdapter
    private fun initializeGameAdapter() {
        gameStrArrayList = ArrayList()

//        for (i in GamesUID2.values()) {
//
//            gameStrArrayList.add(i.name)
//        }

        GamesUID2.values().forEachIndexed { index, gamesUID2 ->
            gameStrArrayList.add(GamesModel(gamesUID2.name, returnImg(gamesUID2.name)))
        }

        val linearLayoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

        mViewDataBinding.allGamesRecycler.layoutManager = linearLayoutManager

        gameAdapter = AllGamesAdapter(gameStrArrayList, this)
        mViewDataBinding.allGamesRecycler.adapter = gameAdapter


    }

    override fun onClickGame(position: Int) {
        Log.d("123123", "onClickGame: ")

        when (gameStrArrayList[position].name) {

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag,arguments)


            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag,arguments)

            }

//            GamesUID2.BreakTheBlock.name -> {
//                findNavController().navigate(R.id.breakTheBlockGameFrag,arguments)
//
//            }

            GamesUID2.ColorDeception.name -> {
                findNavController().navigate(R.id.colorOfDecepGameFrag,arguments)

            }

            GamesUID2.Tetris.name -> {
                findNavController().navigate(R.id.tetrisGameFrag,arguments)

            }

            GamesUID2.Concentration.name -> {

                findNavController().navigate(R.id.concentrationGameFrag,arguments)
            }

            GamesUID2.CardCalculation.name -> {
                findNavController().navigate(R.id.cardCalculationGameFrag,arguments)

            }

            GamesUID2.Flick.name -> {
                findNavController().navigate(R.id.flickGameFrag,arguments)

            }

            GamesUID2.FollowTheLeader.name -> {
                findNavController().navigate(R.id.followTheLeaderGameFrag,arguments)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                findNavController().navigate(R.id.unfollowTheLeaderGameFrag,arguments)

            }

            GamesUID2.GuessTheFlag.name -> {
//                findNavController().navigate(R.id,arguments)

            }

            GamesUID2.HighLow.name -> {
                findNavController().navigate(R.id.highLowGameFrag,arguments)

            }

            GamesUID2.MakeTen.name -> {
                findNavController().navigate(R.id.make10GameFrag,arguments)

            }

            GamesUID2.MissingPiece.name -> {
                findNavController().navigate(R.id.missingPieceGameFrag,arguments)

            }





            GamesUID2.QuickEye.name -> {
                findNavController().navigate(R.id.quickEyeGameFrag,arguments)

            }

            GamesUID2.RainFall.name -> {
                findNavController().navigate(R.id.rainFallGameFrag,arguments)

            }

            GamesUID2.RapidSorting.name -> {
                findNavController().navigate(R.id.rapidSortingGameFrag,arguments)

            }

            GamesUID2.ReverseRps.name -> {
                findNavController().navigate(R.id.reverseRPSFrag, arguments)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag, arguments)

            }

            GamesUID2.SpinningBlock.name -> {
                findNavController().navigate(R.id.spinningBlockGameFrag, arguments)

            }

            GamesUID2.ShapeDeception.name -> {
                findNavController().navigate(R.id.shapeDeceptionGameFrag, arguments)
            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag, arguments)

            }

            GamesUID2.TouchTheNum.name -> {
                findNavController().navigate(R.id.touchTheNumGameFrag,arguments)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag,arguments)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag,arguments)

            }


        }


    }

}