package com.teamx.equiz.ui.fragments.dashboard


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.t
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.models.topWinnerData.UserRank
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentDashboardBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGameInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGamesAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnerInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnersAdapter
import com.teamx.equiz.ui.fragments.ecommerce.home.CategoriesAdapter
import com.teamx.equiz.ui.fragments.ecommerce.home.ProductAdapter
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.ui.fragments.quizes.TitleData
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.DialogHelperClass
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

    lateinit var winnerAdapter: TopWinnersAdapter
    lateinit var winnerArrayList: ArrayList<Game>


    lateinit var quizAdapter: QuizesAdapter
    lateinit var quizArrayList: ArrayList<Data>
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

        mViewModel.getWallet()

        if (!mViewModel.getwalletResponse.hasActiveObservers()) {
            mViewModel.getwalletResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            mViewDataBinding.tvCoins.text = data.data.toString()
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getwalletResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


        mViewModel.getTopWinners()

        if (!mViewModel.getTopWinnersResponse.hasActiveObservers()) {
            mViewModel.getTopWinnersResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            data.game.forEach {
                                winnerArrayList.add(it)
                            }

                            winnerAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getTopWinnersResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }



        if (!mViewModel.getquizTitileResponse.hasActiveObservers()) {
            mViewModel.getquizTitileResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            data.data.forEach {
                                quizArrayList.add(it)
                            }
                            quizAdapter.notifyDataSetChanged()
                        }

                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getquizTitileResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }




        initializeCategoriesAdapter()
        initializeGameAdapter()
        initializeWinnerAdapter()
        initializeQuizesAdapter()
    }

    private lateinit var strArrayList: ArrayList<TitleData>
    private lateinit var gameStrArrayList: ArrayList<String>
    private lateinit var winStrArrayList: ArrayList<String>
    private lateinit var quizesTitleAdapter: QuizesTitleAdapter
    private lateinit var gameAdapter: AllGamesAdapter
    private lateinit var topWinner: TopWinnersAdapter
    private lateinit var quizesAdapter: QuizesAdapter
    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()

        strArrayList.add(TitleData("World",true))
        strArrayList.add(TitleData("National",false))
        strArrayList.add(TitleData("City",false))

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter

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
        winnerArrayList = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.winnerRecycler.layoutManager = layoutManager1

        winnerAdapter = TopWinnersAdapter(winnerArrayList, this)
        mViewDataBinding.winnerRecycler.adapter = winnerAdapter


    }
    private fun initializeQuizesAdapter() {
        quizArrayList = ArrayList()

        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recQuizes.layoutManager = layoutManager2

        quizAdapter = QuizesAdapter(quizArrayList, this)
        mViewDataBinding.recQuizes.adapter = quizAdapter


    }

    override fun quizTitle(position : Int) {
        mViewModel.getquizTitile("World","","normal")

    }

    override fun quizeItem(position : Int) {
        findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment)
    }

    override fun onClickGame(position: Int) {
        Log.d("123", "onClickGame: ")
        when (gameStrArrayList[position]) {

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag, arguments)


            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag, arguments)

            }

            GamesUID2.BreakTheBlock.name -> {
                findNavController().navigate(R.id.breakTheBlockGameFrag, arguments)

            }

            GamesUID2.ColorDeception.name -> {
                findNavController().navigate(R.id.colorOfDecepGameFrag, arguments)

            }

            GamesUID2.Tetris.name -> {
                findNavController().navigate(R.id.tetrisGameFrag, arguments)

            }

            GamesUID2.Concentration.name -> {

                findNavController().navigate(R.id.concentrationGameFrag, arguments)
            }

            GamesUID2.CardCalculation.name -> {
                findNavController().navigate(R.id.cardCalculationGameFrag, arguments)

            }

            GamesUID2.Flick.name -> {
                findNavController().navigate(R.id.flickGameFrag, arguments)

            }

            GamesUID2.FollowTheLeader.name -> {
                findNavController().navigate(R.id.followTheLeaderGameFrag, arguments)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                findNavController().navigate(R.id.unfollowTheLeaderGameFrag, arguments)

            }

            GamesUID2.SpinTheWheel.name -> {
//                findNavController().navigate(R.id,arguments)

            }

            GamesUID2.HighLow.name -> {
                findNavController().navigate(R.id.highLowGameFrag, arguments)

            }

            GamesUID2.MakeTen.name -> {
                findNavController().navigate(R.id.make10GameFrag, arguments)

            }

            GamesUID2.MissingPiece.name -> {
                findNavController().navigate(R.id.missingPieceGameFrag, arguments)

            }


            GamesUID2.QuickEye.name -> {
                findNavController().navigate(R.id.quickEyeGameFrag, arguments)

            }

            GamesUID2.RainFall.name -> {
                findNavController().navigate(R.id.rainFallGameFrag, arguments)

            }

            GamesUID2.RapidSorting.name -> {
                findNavController().navigate(R.id.rapidSortingGameFrag, arguments)

            }

            GamesUID2.ReverseRps.name -> {
                findNavController().navigate(R.id.reverseRPSFrag, arguments)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag, arguments)

            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag, arguments)

            }

            GamesUID2.TouchTheNum.name -> {
                findNavController().navigate(R.id.touchTheNumGameFrag, arguments)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag, arguments)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag, arguments)

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