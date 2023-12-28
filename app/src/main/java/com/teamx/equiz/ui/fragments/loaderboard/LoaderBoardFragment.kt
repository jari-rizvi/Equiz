package com.teamx.equiz.ui.fragments.loaderboard

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoaderBoardBinding
import com.teamx.equiz.ui.fragments.dashboard.adapter.TopWinnersAdapter
import com.teamx.equiz.ui.fragments.loaderboard.adapter.LoaderMultiViewAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
@AndroidEntryPoint
class LoaderBoardFragment : BaseFragment<FragmentLoaderBoardBinding, LoaderBoardViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_loader_board
    override val viewModel: Class<LoaderBoardViewModel>
        get() = LoaderBoardViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var loaderMultiViewAdapter: LoaderMultiViewAdapter

    lateinit var winnerArrayList: ArrayList<Game>

    private var isOdd = false
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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

//        initializeCategoriesAdapter()

        mViewModel.getTopWinners()

        if (!mViewModel.getTopWinnersResponse.hasActiveObservers()) {
            mViewModel.getTopWinnersResponse.observe(requireActivity()) {
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

                            data.game.forEach {
                                winnerArrayList.add(it)
                            }

                            loaderMultiViewAdapter.notifyDataSetChanged()

                            try {
                                Picasso.get().load(data.game[0].image)
                                    .into(mViewDataBinding.hatlyIcon)
                                Picasso.get().load(data.game[1].image)
                                    .into(mViewDataBinding.hatlyIcon5454)
                                Picasso.get().load(data.game[2].image)
                                    .into(mViewDataBinding.hatlyIcon54)
                                mViewDataBinding.textView545.text = data.game[0].name
                                mViewDataBinding.textView545455.text =
                                    data.game[0].wallet.toString()

                                mViewDataBinding.textView54545.text = data.game[1].name
                                mViewDataBinding.textView545545455.text =
                                    data.game[1].wallet.toString()

                                mViewDataBinding.textView545545445.text = data.game[2].name
                                mViewDataBinding.textView5455454554545.text =
                                    data.game[2].wallet.toString()

                            } catch (e: Exception) {
                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
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

        initializeWinnerAdapter()

    }


//       private fun initializeCategoriesAdapter() {
//           dummyArrayList = ArrayList()
//
//           dummyArrayList.add(checkOdd())
//           dummyArrayList.add(checkOdd())
//           dummyArrayList.add(checkOdd())
//           dummyArrayList.add(checkOdd())
//
//           val layoutManager1 = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//           mViewDataBinding.recyLoaderBoard.layoutManager = layoutManager1
//
//           loaderMultiViewAdapter = LoaderMultiViewAdapter(dummyArrayList)
//           mViewDataBinding.recyLoaderBoard.adapter = loaderMultiViewAdapter
//       }

    private fun initializeWinnerAdapter() {
        winnerArrayList = ArrayList()

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recyLoaderBoard.layoutManager = layoutManager1

        loaderMultiViewAdapter = LoaderMultiViewAdapter(winnerArrayList)
        mViewDataBinding.recyLoaderBoard.adapter = loaderMultiViewAdapter


    }

    private fun checkOdd(): Boolean {
        isOdd = !isOdd
        return isOdd
    }


}

