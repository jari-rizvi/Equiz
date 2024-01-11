package com.teamx.equiz.ui.fragments.games


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
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


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
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

        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
        }


        initializeGameAdapter()
    }

    private lateinit var gameStrArrayList: ArrayList<GamesModel>
    private lateinit var gameAdapter: AllGamesAdapter
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initializeGameAdapter() {
        gameStrArrayList = ArrayList()

//        for (i in GamesUID2.values()) {
//
//            gameStrArrayList.add(i.name)
//        }

        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.Flick.name}")
        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.Tetris.name}")
        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.RapidSorting.name}")
        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.SpinningBlock.name}")
        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.MakeTen.name}")
        Log.d("initializeGameAdapter", "initializeGameAdapter: ${GamesUID2.HighLow.name}")

        GamesUID2.values().forEachIndexed { index, gamesUID2 ->

            if (index != 3 || index != 6) {
                gameStrArrayList.add(
                    GamesModel(
                        returnGameName(gamesUID2.name), returnImg(gamesUID2.name)
                    )
                )
            }
        }

        gameStrArrayList.removeIf { it.name == "Flick" || it.name == "Tetris" || it.name == "High Low" || it.name == "Make Ten" || it.name == "Rapid Sorting" || it.name == "Spinning Block"}



        val linearLayoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)

        mViewDataBinding.allGamesRecycler.layoutManager = linearLayoutManager

        gameAdapter = AllGamesAdapter(gameStrArrayList, this)
        mViewDataBinding.allGamesRecycler.adapter = gameAdapter


    }

    private fun returnGameName(enumNumberEnum: String): String {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {
                "Addition Addiction"

            }

            GamesUID2.BirdWatching.name -> {
                "Bird Watching"

            }

            GamesUID2.Matching.name -> {
                "Matching"

            }

            GamesUID2.Operations.name -> {

                "Operations"
            }

            GamesUID2.ColorDeception.name -> {
                "ColorDeception"

            }

            GamesUID2.Tetris.name -> {
                "Tetris"

            }


            GamesUID2.CardCalculation.name -> {
                "Card Calculation"

            }

            GamesUID2.Concentration.name -> {
                "Concentration"

            }

            GamesUID2.Flick.name -> {
                "Flick"

            }

            GamesUID2.FollowTheLeader.name -> {
                "Follow The Leader"

            }

            GamesUID2.UnfollowTheLeader.name -> {
                "Unfollow The Leader"

            }

            GamesUID2.GuessTheFlag.name -> {
                "Guess The Flag"
            }

            GamesUID2.HighLow.name -> {
                "High Low"

            }

            GamesUID2.MakeTen.name -> {
                "Make Ten"

            }

            GamesUID2.MissingPiece.name -> {
                "Missing Piece"

            }


            GamesUID2.QuickEye.name -> {
                "Quick Eye"

            }

            GamesUID2.RainFall.name -> {
                "Rain Fall"

            }

            GamesUID2.RapidSorting.name -> {
                "Rapid Sorting"

            }

            GamesUID2.ReverseRps.name -> {
                "Reverse RPS"

            }

            GamesUID2.Simplicity.name -> {
                "Simplicity"

            }

            GamesUID2.SpinningBlock.name -> {
                "Spinning Block"

            }

            GamesUID2.ShapeDeception.name -> {
                "Shape Deception"
            }

            GamesUID2.TapTheColor.name -> {
                "Tap The Color"

            }

            GamesUID2.TouchTheNum.name -> {
                "Touch The Number"
            }

            GamesUID2.TouchTheNumPlus.name -> {
                "Touch The Number Plus"

            }

            GamesUID2.WeatherCast.name -> {
                "Weather Cast"

            }


            else -> {
                "Weather Cast"
            }
        }


    }

    override fun onClickGame(position: Int) {
        Log.d("123123", "onClickGame: ")
        var strname = GamesUID2.values()[position].name
        Log.d("123123", "onClickGame:$strname ")
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("gameName", strname)
        bundle.putString("route", "game")
        if (strname.equals("Tetris", true)) {

            findNavController().navigate(R.id.tetrisGameFrag, bundle, options)
        } else {

            findNavController().navigate(R.id.startUpGameFrag, bundle, options)
        }

        /*when (strname) {

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag, arguments?.apply {
                    putString("gameName",strname)
                })


            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag,arguments,options)

            }

//            GamesUID2.BreakTheBlock.name -> {
//                findNavController().navigate(R.id.breakTheBlockGameFrag,arguments,options)
//
//            }

            GamesUID2.ColorDeception.name -> {
                findNavController().navigate(R.id.colorOfDecepGameFrag,arguments,options)

            }

            GamesUID2.Tetris.name -> {
                findNavController().navigate(R.id.tetrisGameFrag,arguments,options)

            }

            GamesUID2.Concentration.name -> {

                findNavController().navigate(R.id.concentrationGameFrag,arguments,options)
            }

            GamesUID2.CardCalculation.name -> {
                findNavController().navigate(R.id.cardCalculationGameFrag,arguments,options)

            }

            GamesUID2.Flick.name -> {
                findNavController().navigate(R.id.flickGameFrag,arguments,options)

            }

            GamesUID2.FollowTheLeader.name -> {
                findNavController().navigate(R.id.followTheLeaderGameFrag,arguments,options)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                findNavController().navigate(R.id.unfollowTheLeaderGameFrag,arguments,options)

            }

            GamesUID2.GuessTheFlag.name -> {
//                findNavController().navigate(R.id,arguments,options)

            }

            GamesUID2.HighLow.name -> {
                findNavController().navigate(R.id.highLowGameFrag,arguments,options)

            }

            GamesUID2.MakeTen.name -> {
                findNavController().navigate(R.id.make10GameFrag,arguments,options)

            }

            GamesUID2.MissingPiece.name -> {
                findNavController().navigate(R.id.missingPieceGameFrag,arguments,options)

            }





            GamesUID2.QuickEye.name -> {
                findNavController().navigate(R.id.quickEyeGameFrag,arguments,options)

            }

            GamesUID2.RainFall.name -> {
                findNavController().navigate(R.id.rainFallGameFrag,arguments,options)

            }

            GamesUID2.RapidSorting.name -> {
                findNavController().navigate(R.id.rapidSortingGameFrag,arguments,options)

            }

            GamesUID2.ReverseRps.name -> {
                findNavController().navigate(R.id.reverseRPSFrag, arguments,options)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag, arguments,options)

            }

            GamesUID2.SpinningBlock.name -> {
                findNavController().navigate(R.id.spinningBlockGameFrag, arguments,options)

            }

            GamesUID2.ShapeDeception.name -> {
                findNavController().navigate(R.id.shapeDeceptionGameFrag, arguments,options)
            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag, arguments,options)

            }

            GamesUID2.TouchTheNum.name -> {
                findNavController().navigate(R.id.touchTheNumGameFrag,arguments,options)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag,arguments,options)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag,arguments,options)

            }


        }*/


    }

}