package com.teamx.equiz.ui.fragments.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.SettingsFragmentLayoutBinding
import com.teamx.equiz.ui.fragments.quizes.TitleData
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


    var referralCode = ""
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

                                referralCode = data.user.referralCode
                                mViewDataBinding.textView3.setText(data.user.name)
                                mViewDataBinding.textView4.setText(data.user.email)
                                mViewDataBinding.textView52.setText(data.user.chances.toString())
                                mViewDataBinding.textView51.setText(data.user.score.toString())

//                                Picasso.get().load(data.user.image).resize(500, 500).into(mViewDataBinding.profilePicture)
                                Glide.with(mViewDataBinding.profilePicture.context).load(data.user.image).into(mViewDataBinding.profilePicture)

                                if (!data.user.isPremium) {
                                    mViewDataBinding.btnSubscribe.visibility = View.VISIBLE
                                } else {
                                    mViewDataBinding.btnSubscribe.visibility = View.GONE

                                }


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
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }
        addListeners()


    }

    private fun addListeners() {
        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("referralCode", referralCode)
        mViewDataBinding.btnLogout.setOnClickListener {


//            mViewModel.logOutUser()
            lifecycleScope.launch(Dispatchers.IO) {
                dataStoreProvider.removeAll()

            }
            PrefHelper.getInstance(requireContext())
                .setFavouriteShop(listOf<String>())
            PrefHelper.getInstance(requireContext()).clearAll()

            navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host_fragment
            )

            findNavController().popBackStack(R.id.settingsFragment, true)
            findNavController().navigate(R.id.temp2Fragment, arguments, null)


        }
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
            bundle.putString("referralCode", referralCode)
            findNavController().navigate(R.id.walletFragment, bundle, options)
        }

        mViewDataBinding.btnReffeal.setOnClickListener {



            bundle.putString("referralCode", referralCode)
            findNavController().navigate(R.id.referralFragment, bundle, options)

        }

        mViewDataBinding.btnProfile.setOnClickListener {


            bundle.putString("rankUser", mViewDataBinding.textView50.text.toString())
            findNavController().navigate(R.id.profileFragment, bundle, options)
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

            val uri: Uri =
                Uri.parse("https://sites.google.com/view/equiz-terms-and-conditions?usp=sharing") // missing 'http://' will cause crashed


            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }

        mViewDataBinding.btnCollectPrize.setOnClickListener {
            findNavController().navigate(R.id.collectPriceFragment, arguments, options)
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
        addLeaderBoard()
    }

    fun addLeaderBoard() {
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


                            try {
                                data.userRank.forEach {
                                    mViewDataBinding.textView50.text = it.rank.toString()
                                }

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
    }

    private fun initializeCategoriesAdapter() {

    }


}




