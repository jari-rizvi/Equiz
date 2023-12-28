package com.teamx.equiz.ui.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.SettingsFragmentLayoutBinding
import com.teamx.equiz.ui.fragments.quizes.TitleData
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentLayoutBinding, SettingsViewModel>() {

    override val layoutId: Int
        get() = R.layout.settings_fragment_layout
    override val viewModel: Class<SettingsViewModel>
        get() = SettingsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var strArrayList: ArrayList<Data>
    private lateinit var strTitleArrayList: ArrayList<TitleData>
    private lateinit var quizesTitleAdapter: QuizesTitleAdapter
    private lateinit var quizesAdapter: QuizesAdapter


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

//        TOKENER =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTFlNWZiOGM3NjU2MDdlNzE0NjNiZGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDE2ODg4ODAsImV4cCI6MTcwMTc3NTI4MH0.th7AmVunSuxLeq8XP5oe-JywCZGijWOAtrqPmImIKzM"

        initializeCategoriesAdapter()
//        mViewDataBinding.btnback.setOnClickListener {
//            findNavController().popBackStack()
//        }

//        if (!mViewModel.quizTitleResponse.hasActiveObservers()) {
//            mViewModel.quizTitle("World", null, "normal")
//            mViewModel.quizTitleResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.NOTVERIFY -> {
//                        loadingDialog.dismiss()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        it.data?.let { data ->
//                            strArrayList.clear()
//                            strArrayList.addAll(data.data)
//                            quizesAdapter.notifyDataSetChanged()
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//
//                        if (isAdded) {
//                            mViewDataBinding.root.snackbar(it.message!!)
//                        }
//                    }
//                }
//            }
//        }

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


                                mViewDataBinding.textView3.setText(data.user.name)
                                mViewDataBinding.textView4.setText(data.user.email)
                                mViewDataBinding.textView52.setText(data.user.chances.toString())
                                mViewDataBinding.textView51.setText(data.user.score.toString())

                                Picasso.get().load(data.user.image).resize(500, 500)
                                    .into(mViewDataBinding.profilePicture)

                            } catch (e: Exception) {

                            }
                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        mViewDataBinding.root.snackbar(it.message!!)
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }
        addListeners()
    }

    private fun addListeners() {
        mViewDataBinding.btneccomernce.setOnClickListener {
            findNavController().navigate(R.id.ecommerceFragment, arguments, options)
        }
        mViewDataBinding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
        }
        mViewDataBinding.btnquiz.setOnClickListener {
            findNavController().navigate(R.id.quizesFragment, arguments, options)
        }
        mViewDataBinding.btnWallet.setOnClickListener {
            findNavController().navigate(R.id.walletFragment, arguments, options)
        }

        mViewDataBinding.btnReffeal.setOnClickListener {
            findNavController().navigate(R.id.referralFragment, arguments, options)
        }

        mViewDataBinding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment, arguments, options)
        }

        mViewDataBinding.btnNotification.setOnClickListener {
            findNavController().navigate(R.id.notificationsFragment, arguments, options)
        }

        mViewDataBinding.btnNews.setOnClickListener {
            findNavController().navigate(R.id.newsFragment, arguments, options)
        }

        mViewDataBinding.btnSupport.setOnClickListener {
            findNavController().navigate(R.id.supportFragment, arguments, options)
        }

        mViewDataBinding.btnSetting.setOnClickListener {
//            Toast.makeText(this, "Comming Soom", Toast.LENGTH_SHORT).show();
        }

        mViewDataBinding.btnTermsAndCondition.setOnClickListener {
//           findNavController().navigate(R.id.wishlistFragment, arguments, options)
        }

        mViewDataBinding.btnCollectPrize.setOnClickListener {
            findNavController().navigate(R.id.profileFragment, arguments, options)
        }

        mViewDataBinding.btnCoupon.setOnClickListener {
            findNavController().navigate(R.id.coupnsFragment, arguments, options)
        }

        mViewDataBinding.btnleaderboard.setOnClickListener {
            findNavController().navigate(R.id.loaderBoardFragment, arguments, options)
        }
        mViewDataBinding.btnSubscribe.setOnClickListener {
            findNavController().navigate(R.id.subscriptionFragment, arguments, options)
        }

    }

    private fun initializeCategoriesAdapter() {

    }


}





