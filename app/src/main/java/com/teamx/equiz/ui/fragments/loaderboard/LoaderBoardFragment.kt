package com.teamx.equiz.ui.fragments.loaderboard

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentLoaderBoardBinding
import com.teamx.equiz.ui.fragments.loaderboard.adapter.LoaderMultiViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderBoardFragment : BaseFragment<FragmentLoaderBoardBinding, LoaderBoardViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_loader_board
    override val viewModel: Class<LoaderBoardViewModel>
        get() = LoaderBoardViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var loaderMultiViewAdapter : LoaderMultiViewAdapter
    private lateinit var dummyArrayList : ArrayList<Boolean>

    private var isOdd = false
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

        initializeCategoriesAdapter()

    }


    private fun initializeCategoriesAdapter() {
        dummyArrayList = ArrayList()

        dummyArrayList.add(checkOdd())
        dummyArrayList.add(checkOdd())
        dummyArrayList.add(checkOdd())
        dummyArrayList.add(checkOdd())

        val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recyLoaderBoard.layoutManager = layoutManager1

        loaderMultiViewAdapter = LoaderMultiViewAdapter(dummyArrayList)
        mViewDataBinding.recyLoaderBoard.adapter = loaderMultiViewAdapter
    }

    private fun checkOdd():Boolean{
        isOdd = !isOdd
        return isOdd
    }



}

