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
                R.id.action_dashboardFragment_to_ecommerceFragment, null
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

        gameStrArrayList.add("")
        gameStrArrayList.add("")
        gameStrArrayList.add("")
        gameStrArrayList.add("")

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
    }

    override fun onWinnerClick(position: Int) {
        Log.d("123", "onWinnerClick: ")
    }
}