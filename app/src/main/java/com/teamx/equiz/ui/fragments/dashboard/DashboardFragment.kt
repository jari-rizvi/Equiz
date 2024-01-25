package com.teamx.equiz.ui.fragments.dashboard


import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentDashboardBinding
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGameInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.AllGamesAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.ImageSliderAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnerInterface
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnersAdapter
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.ui.fragments.quizes.TitleData
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.DialogHelperClass.Companion.returnGameName
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import timber.log.Timber
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    QuizesInterface, TopWinnerInterface, AllGameInterface {

    override val layoutId: Int
        get() = R.layout.fragment_dashboard
    override val viewModel: Class<DashboardViewModel>
        get() = DashboardViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var timer: Timer
    private val delayInMillis: Long = 2000
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var options: NavOptions

    lateinit var winnerAdapter: TopWinnersAdapter
    lateinit var winnerArrayList: ArrayList<Game>


    lateinit var quizAdapter: QuizesAdapter
    lateinit var quizArrayList: ArrayList<Data>
    var id: String = ""

    @RequiresApi(Build.VERSION_CODES.N)
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

        mViewDataBinding.btnback.setOnClickListener {
//            val activity = requireActivity() as MainActivity
//            activity.openDrawer()
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle.putString("userId", userId)

            findNavController().navigate(R.id.settingsFragment, bundle, options)
        }
        mViewDataBinding.tvCoins.setOnClickListener {
            if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                    "null",
                    true
                )
            ) {
                DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
                return@setOnClickListener
            }
            findNavController().navigate(
                R.id.topupFragment, arguments, options
            )
        }

        mViewDataBinding.textView155.setOnClickListener {
            if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                    "null",
                    true
                )
            ) {
                DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
                return@setOnClickListener
            }
            findNavController().navigate(
                R.id.quizesFragment, arguments, options
            )
        }
        mViewDataBinding.seeAllGames.setOnClickListener {
            findNavController().navigate(
                R.id.gamesFragment, arguments, options
            )
        }

        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("id").toString()
            Timber.tag("TAG").d(id.toString())
        }


        mViewModel.getWallet(this)

        if (!mViewModel.getwalletResponse.hasActiveObservers()) {
            mViewModel.getwalletResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->

                            mViewDataBinding.tvCoins.text = data.data.toString()
                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        onToSignUpPage()
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
                        )
                    }
                }
                if (isAdded) {
//                    mViewModel.getwalletResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
                }
            }
        }


        id = PrefHelper.getInstance(requireContext()).setUserId.toString()

        if (id.isNullOrEmpty()){
            id = " "
        }
            mViewModel.getTopWinners(id, this)


        if (!mViewModel.getBannerResponse.hasActiveObservers()) {
            mViewModel.getBannerResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
//                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        imageList2.clear()
                        it.data?.let { data ->
//                            data.game.forEach {
//                                winnerArrayList.add(it)
//                            }
                            data.newsData.forEach {
                                imageList2.add(it.image)
                            }
//                            winnerAdapter.notifyDataSetChanged()


                        }
                        mViewDataBinding.viewPager.adapter?.notifyDataSetChanged()

                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        onToSignUpPage()
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
                        )
                    }
                }
                if (isAdded) {
//                    mViewModel.getBannerResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
                }
            }
        }
        if (!mViewModel.getTopWinnersResponse.hasActiveObservers()) {
            mViewModel.getTopWinnersResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            data.game.forEach {
                                winnerArrayList.add(it)
                            }

                            winnerAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        onToSignUpPage()
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
                        )
                    }
                }
                if (isAdded) {
//                    mViewModel.getTopWinnersResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
                }
            }
        }




        mViewModel.getquizTitileResponse.observe(requireActivity()) {
            when (it.status) {
                Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                }

                Resource.Status.NOTVERIFY -> {
                    loadingDialog.dismiss()
                }

                Resource.Status.SUCCESS -> {
                    quizArrayList.clear()
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                    mViewDataBinding.mainLayout.visibility = View.VISIBLE
                    mViewDataBinding.recQuizes.visibility = View.VISIBLE

                    it.data?.let { data ->
                        data.data.forEach {
                            quizArrayList.add(it)
                        }
                        quizAdapter.notifyDataSetChanged()
                    }

                }

                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                    onToSignUpPage()
                }

                Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                    mViewDataBinding.recQuizes.visibility = View.GONE
                    /*  DialogHelperClass.errorDialog(
                          requireContext(), it.message!!
                      )*/
                    if (isAdded) {
                        mViewDataBinding.root.snackbar(it.message!!)
                    }
                }
            }

        }

        FirebaseApp.initializeApp(requireContext())
        Firebase.initialize(requireContext())


//        getMeApi()
        addNewBanners()

        initializeCategoriesAdapter()
        initializeGameAdapter()
        initializeWinnerAdapter()
        initializeQuizesAdapter()
        addingSliderAdapter()

        mViewDataBinding.swiperefresh.setOnRefreshListener(OnRefreshListener {
            mViewDataBinding.swiperefresh.isRefreshing = false
            RearrangeData()
        })

    }

    private fun RearrangeData() {
        mViewModel.getWallet(this)
        mViewModel.getTopWinners(id, this)
        mViewModel.getquizTitile("World", "", "", this)
        addNewBanners()
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

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        var country = bundle?.getString("country")


        if (country.isNullOrEmpty()) {
            country = PrefHelper.getInstance(requireContext()).getCountry

        }
        val splitter = country.toString().split(" ")

        if (splitter.size > 1) {
            var tempString = ""
            splitter.forEach {
                tempString += it.first().toString()
            }
            country = tempString
        } else {
            if (country.isNullOrEmpty()) {
                country = "Regional"
            }

        }




        strArrayList.add(TitleData("World", true))
        strArrayList.add(TitleData(country.toString(), false))
        strArrayList.add(TitleData("Premium", false))

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter

        mViewModel.getquizTitile("World", "", "", this)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initializeGameAdapter() {
        gameStrArrayList = ArrayList()

        GamesUID2.values().forEachIndexed { index, gamesUID2 ->
            gameStrArrayList.add(
                GamesModel(
                    returnGameName(gamesUID2.name),
                    returnImg(gamesUID2.name)
                )
            )
        }

//        gameStrArrayList.removeIf { it.name == "Flick" || it.name == "Tetris" || it.name == "High Low" || it.name == "Make Ten" || it.name == "Rapid Sorting" || it.name == "Spinning Block"}
        gameStrArrayList.removeIf {it.name == "Tetris" }

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.allGamesRecycler.layoutManager = layoutManager1

        gameAdapter = AllGamesAdapter(gameStrArrayList, this)
        mViewDataBinding.allGamesRecycler.adapter = gameAdapter


    }


    companion object {
        fun returnGameName(enumNumberEnum: String): String {


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

                /*GamesUID2.Tetris.name -> {
                    "Tetris"

                }*/


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
                    "UnFollow The Leader"

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

               /* GamesUID2.Tetris.name -> {
                    return R.drawable.tetris_icon
                }*/

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
                    return R.drawable.guestheflag_icon
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
                    return R.drawable.spinthewheel_icon
                }

                GamesUID2.ShapeDeception.name -> {
                    return R.drawable.shapedeception_icon
                }

                GamesUID2.TapTheColor.name -> {
                    return R.drawable.tapthecolor_icon
                }

                GamesUID2.TouchTheNum.name -> {
                    return R.drawable.touchthenumber_icon
                }

                GamesUID2.TouchTheNumPlus.name -> {
                    return R.drawable.touchthenumbers_icon
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




    override fun quizTitle(position: Int, previousNumber: Int) {
        val tick = strArrayList.get(position).value

        val topic = if (tick.equals("World", true)) {
            ""
        } else {
            ""
        }
        mViewModel.getquizTitile("$tick", "$topic", "", this)
//        strArrayList.forEach{
//            if (it.isSelected)
//            it.isSelected = false
//        }

        strArrayList.get(previousNumber).isSelected = false
        strArrayList.get(position).isSelected = true
        mViewDataBinding.recCategories.adapter?.notifyItemChanged(previousNumber)
        mViewDataBinding.recCategories.adapter?.notifyItemChanged(position)

    }

    override fun quizeItem(position: Int) {
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        bundle.putString("quiz_id", "${quizArrayList.get(position)._id}")
        bundle.putString("routeQuiz", "dash")


        findNavController().navigate(R.id.playQuizFragment, bundle, options)
    }

    override fun onClickGame(position: Int) {
        Log.d("123", "onClickGame: ")
        Log.d("123123", "onClickGame: ")
        var strname = GamesUID2.values()[position].name
        Log.d("123123", "onClickGame:$strname ")
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle?.putString("gameName", strname)
        bundle?.putString("route", "dash")
        if (strname.equals("Tetris", true)) {

            findNavController().navigate(R.id.tetrisGameFrag, bundle, options)
        } else {

            findNavController().navigate(R.id.startUpGameFrag, bundle, options)
        }
        /*  when (gameStrArrayList[position].name) {

              GamesUID2.AdditionAddiction.name -> {

                  findNavController().navigate(R.id.additionAddictionGameFrag, arguments,options)


              }

              GamesUID2.BirdWatching.name -> {
                  findNavController().navigate(R.id.birdWatchingGameFrag, arguments,options)

              }

  //            GamesUID2.BreakTheBlock.name -> {
  //                findNavController().navigate(R.id.breakTheBlockGameFrag, arguments,options)
  //
  //            }

              GamesUID2.ColorDeception.name -> {
                  findNavController().navigate(R.id.colorOfDecepGameFrag, arguments,options)

              }

              GamesUID2.Tetris.name -> {
                  findNavController().navigate(R.id.tetrisGameFrag, arguments,options)

              }

              GamesUID2.Concentration.name -> {

                  findNavController().navigate(R.id.concentrationGameFrag, arguments,options)
              }

              GamesUID2.CardCalculation.name -> {
                  findNavController().navigate(R.id.cardCalculationGameFrag, arguments,options)

              }

              GamesUID2.Flick.name -> {
                  findNavController().navigate(R.id.flickGameFrag, arguments,options)

              }

              GamesUID2.FollowTheLeader.name -> {
                  findNavController().navigate(R.id.followTheLeaderGameFrag, arguments,options)

              }

              GamesUID2.UnfollowTheLeader.name -> {
                  findNavController().navigate(R.id.unfollowTheLeaderGameFrag, arguments,options)

              }

              GamesUID2.GuessTheFlag.name -> {
  //                findNavController().navigate(R.id,arguments,options)

              }

              GamesUID2.HighLow.name -> {
                  findNavController().navigate(R.id.highLowGameFrag, arguments,options)

              }

              GamesUID2.MakeTen.name -> {
                  findNavController().navigate(R.id.make10GameFrag, arguments,options)

              }

              GamesUID2.MissingPiece.name -> {
                  findNavController().navigate(R.id.missingPieceGameFrag, arguments,options)

              }


              GamesUID2.QuickEye.name -> {
                  findNavController().navigate(R.id.quickEyeGameFrag, arguments,options)

              }

              GamesUID2.RainFall.name -> {
                  findNavController().navigate(R.id.rainFallGameFrag, arguments,options)

              }

              GamesUID2.RapidSorting.name -> {
                  findNavController().navigate(R.id.rapidSortingGameFrag, arguments,options)

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
                  findNavController().navigate(R.id.touchTheNumGameFrag, arguments,options)

              }

              GamesUID2.TouchTheNumPlus.name -> {
                  findNavController().navigate(R.id.touchTheNumPlusGameFrag, arguments,options)

              }

              GamesUID2.WeatherCast.name -> {
                  findNavController().navigate(R.id.weatherCastGameFrag, arguments,options)

              }


          }*/
    }

    override fun onWinnerClick(position: Int) {
        Log.d("123", "onWinnerClick: ")
    }


/////////////

    private val imageList2 = arrayListOf<String>()
    private fun addingSliderAdapter() {


        val imageList = listOf(
            R.drawable.argentina_32,
            R.drawable.brazil_31,
            R.drawable.pakistan_02,
            R.drawable.uae_04,
            R.drawable.usa_03,
            R.drawable.uk_05
            // Add more images as needed
        )

        val adapter = ImageSliderAdapter(imageList2)
        mViewDataBinding.viewPager.adapter = adapter

        addDots(imageList.size)
        mViewDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })


        // Initialize the timer
        timer = Timer()
        var a = mViewDataBinding.viewPager
        // Schedule the automatic sliding task
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val currentItem = a.currentItem
                    val itemCount = adapter.itemCount

                    // If the current item is the last one, set the item to the first
                    a.currentItem = if (currentItem < itemCount - 1) currentItem + 1 else 0
                }
            }
        }, 0, delayInMillis)


    }


    private fun addDots(count: Int) {
        for (i in 0 until count) {
            val dot = ImageView(requireContext())
            dot.setImageResource(R.drawable.dot_unselected)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            mViewDataBinding.dotsContainer.addView(dot, params)
        }
        updateDots(0)
    }

    private fun updateDots(selectedPosition: Int) {
        val childCount = mViewDataBinding.dotsContainer.childCount
        for (i in 0 until childCount) {
            val dot = mViewDataBinding.dotsContainer.getChildAt(i) as ImageView
            if (i == selectedPosition) {
                dot.setImageResource(R.drawable.dot_selected_dash)
            } else {
                dot.setImageResource(R.drawable.dot_unselected)
            }
        }
    }

    var userId = ""
    fun getMeApi() {
        mViewModel.me()
        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
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

                            try {

                                userId = data.user._id

                                val params = JsonObject()
                                try {
                                    params.addProperty("userId", "$userId")
                                    mViewModel.getBanners(params)


                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
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

    fun addNewBanners() {
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        var country = bundle?.getString("country")


        if (country.isNullOrEmpty()) {
            country = PrefHelper.getInstance(requireContext()).getCountry
            mViewModel.getBanners2(country.toString())
        } else {
            mViewModel.getBanners2("$country")

        }
        if (!mViewModel.getBannerResponse2.hasActiveObservers()) {
            mViewModel.getBannerResponse2.observe(requireActivity()) {
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

                            try {

                                imageList2.clear()
                                it.data?.let { data ->

                                    data.data.forEach {
                                        imageList2.add(it.image)
                                    }


                                }
                                mViewDataBinding.viewPager.adapter?.notifyDataSetChanged()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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

                    }
                }
            }
        }
    }


//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//
//
//                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                    if (!task.isSuccessful) {
//                        Log.w("123123", "Fetching FCM registration token failed", task.exception)
//                        return@OnCompleteListener
//                    }
//
//                    // Get new FCM registration token
//                    val token = task.result
//                    val params = JsonObject()
//                    params.addProperty("fcmToken", token)
//
//                    mViewModel.notifyFcms(params)
//                    // Log and toast
////                val msg = getString(R.string.about_us, token)
//                    Timber.tag("123123").d(token.toString())
//                    Timber.tag("123123").d(token.toString())
////                Log.d("TAG", msg)
//                })
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//
//            } else {
//                // Directly ask for t
//                //
//                //
//                // he permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }
}

@Keep
data class GamesModel(
    val name: String, val image: Int
)

@Keep
enum class GamesUID2 {
    AdditionAddiction, BirdWatching, ColorDeception /*Tetris*/, Concentration, CardCalculation, Flick, FollowTheLeader, GuessTheFlag, HighLow, MakeTen, Matching, MissingPiece, Operations, QuickEye, RainFall, RapidSorting, ReverseRps, SpinningBlock, Simplicity, ShapeDeception, TapTheColor, TouchTheNum, TouchTheNumPlus, UnfollowTheLeader, WeatherCast
}