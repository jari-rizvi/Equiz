package com.teamx.equiz.ui.fragments.loaderboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentLoaderBoardBinding
import com.teamx.equiz.ui.fragments.loaderboard.adapter.LoaderMultiViewAdapter
import com.teamx.equiz.ui.fragments.loaderboard.adapter.OnUserClickListner
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoaderBoardFragment : BaseFragment<FragmentLoaderBoardBinding, LoaderBoardViewModel>(),
    OnUserClickListner {

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

    var GameModel: Game? = null

    var id: String = ""

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

        /*    mViewDataBinding.constraintLayout10.setOnClickListener {
                findNavController().navigate(R.id.userProgressFragment, arguments, options)

            }*/

//        initializeCategoriesAdapter()


        id = PrefHelper.getInstance(requireContext()).setUserId.toString()
        if (id.isNullOrEmpty()) {
            id = " "
        }


        Log.d("TAG", "userrrrrrrrrrr9d: $id")

        mViewModel.getTopWinners(id)

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
//                                Picasso.get().load(data.game[0].image)
//                                    .into(mViewDataBinding.equizIcon)
//
//                                Picasso.get().load(data.game[1].image)
//                                    .into(mViewDataBinding.equizIcon5454)
//
//                                Picasso.get().load(data.game[2].image)
//                                    .into(mViewDataBinding.equizIcon54)

                                Glide.with(mViewDataBinding.equizIcon.context)
                                    .load(data.game[0].userId.image)
                                    .error(R.drawable.baseline_person)
                                    .into(mViewDataBinding.equizIcon)

//                                Picasso.get().load(data.game[0].userId.image).placeholder(R.drawable.baseline_person).error(R.drawable.baseline_person).resize(500, 500).into(mViewDataBinding.equizIcon)
//                                Picasso.get().load(data.game[1].userId.image).placeholder(R.drawable.baseline_person).error(R.drawable.baseline_person).resize(500, 500).into(mViewDataBinding.equizIcon5454)
//                                Picasso.get().load(data.game[2].userId.image).placeholder(R.drawable.baseline_person).error(R.drawable.baseline_person).resize(500, 500).into(mViewDataBinding.equizIcon54)

//
                                Glide.with(mViewDataBinding.equizIcon5454.context)
                                    .load(data.game[1].userId.image)
                                    .error(R.drawable.baseline_person)
                                    .into(mViewDataBinding.equizIcon5454)

                                Glide.with(mViewDataBinding.equizIcon54.context)
                                    .load(data.game[2].userId.image)
                                    .error(R.drawable.baseline_person)
                                    .into(mViewDataBinding.equizIcon54)


                                val formattedNumber00 =
                                    String.format("%.2f", data.game[0].userId.wallet)
                                mViewDataBinding.textView545.text = data.game[0].userId.name
                                mViewDataBinding.textView545455.text =
                                    formattedNumber00

                                Log.d("TAG", "1111111111: ${data.game}")

                                val formattedNumber1 =
                                    String.format("%.2f", data.game[1].userId.wallet)

                                mViewDataBinding.textView54545.text = data.game[1].userId.name
                                mViewDataBinding.textView545545455.text =
                                    formattedNumber1

                                mViewDataBinding.textView545545445.text = data.game[2].userId.name

                                val formattedNumber2 =
                                    String.format("%.2f", data.game[2].userId.wallet)


                                mViewDataBinding.textView5455454554545.text =
                                    formattedNumber2

                            } catch (e: Exception) {
                            }
                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
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

        loaderMultiViewAdapter = LoaderMultiViewAdapter(winnerArrayList, this)
        mViewDataBinding.recyLoaderBoard.adapter = loaderMultiViewAdapter


    }

    private fun checkOdd(): Boolean {
        isOdd = !isOdd
        return isOdd
    }

//    override fun onUserClick(position: Int) {
//        Log.d("TAG", "onUserClick: ")
//
//        GameModel?.let { it1 ->
//            DialogHelperClass.UserStatsDialog(requireContext(),
//                object : DialogHelperClass.Companion.ChickenDialogCallBack {
//                    override fun onCloseClick() {
//
//                    }
//
//
//                }, gamesModel = it1
//            ).show()
//        }
//
//    }

    override fun onUserClick(position: Int) {
        val gameModel = winnerArrayList[position]

        Log.d("TAG", "onUserClick:${gameModel}}")




        DialogHelperClass.UserStatsDialog(
            requireContext(),
            object : DialogHelperClass.Companion.ChickenDialogCallBack {
                override fun onCloseClick() {

                }


            }, gamesModel = gameModel
        ).show()

    }


}