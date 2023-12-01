package com.teamx.equiz.ui.fragments.dashboard


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentDashboardBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGameInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGamesAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnerInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnersAdapter
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    QuizesInterface, TopWinnerInterface, AllGameInterface {

    override val layoutId: Int
        get() = R.layout.fragment_dashboard
    override val viewModel: Class<DashboardViewModel>
        get() = DashboardViewModel::class.java
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

        mViewDataBinding.btnback.setOnClickListener {
            val activity = requireActivity() as MainActivity
            activity.openDrawer()
        }

        mViewDataBinding.textView155.setOnClickListener {
            findNavController().navigate(
                R.id.quizesFragment, arguments
            )
        }
        mViewDataBinding.seeAllGames.setOnClickListener {
            findNavController().navigate(
                R.id.gamesFragment, arguments
            )
        }

        initializeCategoriesAdapter()
        initializeGameAdapter()
        initializeWinnerAdapter()
    }

    private lateinit var strArrayList: ArrayList<String>
    private lateinit var gameStrArrayList: ArrayList<String>
    private lateinit var winStrArrayList: ArrayList<String>
    private lateinit var quizesTitleAdapter: QuizesTitleAdapter
    private lateinit var gameAdapter: AllGamesAdapter
    private lateinit var topWinner: TopWinnersAdapter
    private lateinit var quizesAdapter: QuizesAdapter
    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()

        strArrayList.add("")
        strArrayList.add("")
        strArrayList.add("")
        strArrayList.add("")

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recQuizes.layoutManager = layoutManager2

        quizesAdapter = QuizesAdapter(strArrayList, this)
        mViewDataBinding.recQuizes.adapter = quizesAdapter
    }

    private fun initializeGameAdapter() {
        gameStrArrayList = ArrayList()

        for (i in GamesUID2.values()) {

            gameStrArrayList.add(i.name)
        }


        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.allGamesRecycler.layoutManager = layoutManager1

        gameAdapter = AllGamesAdapter(gameStrArrayList, this)
        mViewDataBinding.allGamesRecycler.adapter = gameAdapter


    }

    private fun initializeWinnerAdapter() {
        winStrArrayList = ArrayList()

        winStrArrayList.add("")
        winStrArrayList.add("")
        winStrArrayList.add("")
        winStrArrayList.add("")

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.winnerRecycler.layoutManager = layoutManager1

        topWinner = TopWinnersAdapter(winStrArrayList, this)
        mViewDataBinding.winnerRecycler.adapter = topWinner


    }

    override fun quizTitle() {

    }

    override fun quizeItem() {
        findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment)
    }

    override fun onClickGame(position: Int) {
        Log.d("123", "onClickGame: ")
        when (gameStrArrayList[position]) {

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag,arguments)


            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag,arguments)

            }

            GamesUID2.BreakTheBlock.name -> {
                findNavController().navigate(R.id.breakTheBlockGameFrag,arguments)

            }

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

            GamesUID2.SpinTheWheel.name -> {
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
                findNavController().navigate(R.id.reverseRPSFrag,arguments)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag,arguments)

            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag,arguments)

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

    override fun onWinnerClick(position: Int) {
        Log.d("123", "onWinnerClick: ")
    }
}


enum class GamesUID2 {
    AdditionAddiction, BirdWatching, BreakTheBlock, ColorDeception, Tetris, Concentration, CardCalculation, Flick, FollowTheLeader, SpinTheWheel, HighLow, MakeTen, Matching, MissingPiece, Operations, QuickEye, RainFall, RapidSorting, ReverseRps, Simplicity, TapTheColor, TouchTheNum, TouchTheNumPlus, UnfollowTheLeader, WeatherCast
}