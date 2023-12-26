package com.teamx.equiz.ui.fragments.dashboard


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Keep
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentDashboardBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGameInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGamesAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnerInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnersAdapter
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
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->

                            mViewDataBinding.tvCoins.text = data.data.toString()
                        }
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
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
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            data.game.forEach {
                                winnerArrayList.add(it)
                            }

                            winnerAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
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
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
                        quizArrayList.clear()
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            data.data.forEach {
                                quizArrayList.add(it)
                            }
                            quizAdapter.notifyDataSetChanged()
                        }

                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
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
    private lateinit var gameStrArrayList: ArrayList<GamesModel>
    private lateinit var winStrArrayList: ArrayList<String>
    private lateinit var quizesTitleAdapter: QuizesTitleAdapter
    private lateinit var gameAdapter: AllGamesAdapter
    private lateinit var topWinner: TopWinnersAdapter
    private lateinit var quizesAdapter: QuizesAdapter
    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()

        strArrayList.add(TitleData("World", true))
        strArrayList.add(TitleData("National", false))
        strArrayList.add(TitleData("City", false))

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter

        mViewModel.getquizTitile("", "", "")

    }

    private fun initializeGameAdapter() {
        gameStrArrayList = ArrayList()



        GamesUID2.values().forEachIndexed { index, gamesUID2 ->
            gameStrArrayList.add(GamesModel(returnGameName(gamesUID2.name), returnImg(gamesUID2.name)))
        }


        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.allGamesRecycler.layoutManager = layoutManager1

        gameAdapter = AllGamesAdapter(gameStrArrayList, this)
        mViewDataBinding.allGamesRecycler.adapter = gameAdapter


    }


    companion object{
        fun returnImg(index: String): Int {
            when (index) {
                GamesUID2.AdditionAddiction.name -> {
                    return R.drawable.addition_icon
                }
                GamesUID2.BirdWatching.name -> {
                    return R.drawable.bird_favicon
                }
                GamesUID2.ColorDeception.name -> {
                    return R.drawable.colorofdeception_icon
                }
                GamesUID2.Tetris.name -> {
                    return R.drawable.tetris_icon
                }
                GamesUID2.Concentration.name -> {
                    return R.drawable.concentration_icon
                }
                GamesUID2.CardCalculation.name -> {
                    return R.drawable.cardscalculations_icon
                }
                GamesUID2.Flick.name -> {
                    return R.drawable.flick_icon
                }
                GamesUID2.FollowTheLeader.name -> {
                    return R.drawable.follow_the_leder
                }
                GamesUID2.GuessTheFlag.name -> {
                    return R.drawable.spinthewheel_icon
                }
                GamesUID2.HighLow.name -> {
                    return R.drawable.highorlow_icon
                }
                GamesUID2.MakeTen.name -> {
                    return R.drawable.maketen_icon
                }
                GamesUID2.Matching.name -> {
                    return R.drawable.matching_icon
                }
                GamesUID2.MissingPiece.name -> {
                    return R.drawable.missingpieces_icon
                }
                GamesUID2.Operations.name -> {
                    return R.drawable.operations_icon
                }
                GamesUID2.QuickEye.name -> {
                    return R.drawable.quickeye_icon
                }
                GamesUID2.RainFall.name -> {
                    return R.drawable.rainfall_icon
                }
                GamesUID2.RapidSorting.name -> {
                    return R.drawable.rapid_sorting_icon
                }
                GamesUID2.ReverseRps.name -> {
                    return R.drawable.reverserps_icon
                }
                GamesUID2.Simplicity.name -> {
                    return R.drawable.simplicity_icon
                }
                GamesUID2.SpinningBlock.name -> {
                    return R.drawable.simplicity_icon
                }
                GamesUID2.ShapeDeception.name -> {
                    return R.drawable.simplicity_icon
                }
                GamesUID2.TapTheColor.name -> {
                    return R.drawable.tapthecolor_icon
                }
                GamesUID2.TouchTheNum.name -> {
                    return R.drawable.touchthenumber_icon
                }

                GamesUID2.UnfollowTheLeader.name -> {
                    return R.drawable.favicon
                }
                GamesUID2.WeatherCast.name -> {
                    return R.drawable.weathercast_icon
                }
                else -> return 0
            }
        }
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
                "Un Follow The Leader"

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
                "Touch The Num"

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
    override fun quizTitle(position: Int) {
        mViewModel.getquizTitile("", "", "")

    }

    override fun quizeItem(position: Int) {
        findNavController().navigate(R.id.playQuizFragment,arguments)
    }

    override fun onClickGame(position: Int) {
        Log.d("123", "onClickGame: ")
        Log.d("123123", "onClickGame: ")
        var strname = gameStrArrayList[position].name
        Log.d("123123", "onClickGame:$strname ")
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putString("gameName", strname)
        findNavController().navigate(R.id.startUpGameFrag, bundle)
      /*  when (gameStrArrayList[position].name) {

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag, arguments)


            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag, arguments)

            }

//            GamesUID2.BreakTheBlock.name -> {
//                findNavController().navigate(R.id.breakTheBlockGameFrag, arguments)
//
//            }

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

            GamesUID2.GuessTheFlag.name -> {
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
                findNavController().navigate(R.id.touchTheNumGameFrag, arguments)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag, arguments)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag, arguments)

            }


        }*/
    }

    override fun onWinnerClick(position: Int) {
        Log.d("123", "onWinnerClick: ")
    }
}

@Keep
data class GamesModel(
    val name: String, val image: Int
)


enum class GamesUID2 {
    AdditionAddiction, BirdWatching,  ColorDeception, Tetris, Concentration, CardCalculation, Flick, FollowTheLeader, GuessTheFlag, HighLow,  MakeTen, Matching, MissingPiece, Operations, QuickEye, RainFall, RapidSorting, ReverseRps, SpinningBlock,Simplicity, ShapeDeception, TapTheColor, TouchTheNum,TouchTheNumPlus,  UnfollowTheLeader, WeatherCast
}