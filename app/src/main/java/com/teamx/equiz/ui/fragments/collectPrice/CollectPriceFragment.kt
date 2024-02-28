package com.teamx.equiz.ui.fragments.collectPrice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cooltechworks.views.ScratchImageView
import com.google.android.play.integrity.internal.w
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCollectPriceBinding
import com.teamx.equiz.ui.fragments.chances.adapter.ChancesAdapter
import com.teamx.equiz.ui.fragments.chances.data.ChancesTransaction
import com.teamx.equiz.ui.fragments.collectPrice.adapter.RewardsAdapter
import com.teamx.equiz.ui.fragments.collectPrice.data.Raffle
import com.teamx.equiz.ui.fragments.collectPrice.data.WinnerData
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CollectPriceFragment() : BaseFragment<FragmentCollectPriceBinding, CollectPriceViewModel>(),
    ClaimInterfaceCallback {

    override val layoutId: Int
        get() = R.layout.fragment_collect_price
    override val viewModel: Class<CollectPriceViewModel>
        get() = CollectPriceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var winnerid: String

    private lateinit var options: NavOptions

    lateinit var bundle: Bundle


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        mViewDataBinding.btnback.setOnClickListener {
            popUpStack()
        }
        mViewDataBinding.btnProceed.setOnClickListener {
            bundle = Bundle()
            bundle.putString("winnerid", winnerid)

            findNavController().navigate(
                R.id.claimPrizeFragment, bundle, options
            )

//            DialogHelperClass.claimPrizeDialog(requireContext(), this, true, "")

        }

        mViewDataBinding.btnCalim.setOnClickListener {

            findNavController().navigate(
                R.id.rewardsFragment, arguments, options
            )
        }

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.enter_from_left
                popExit = R.anim.exit_to_left
            }
        }
        addImagesOver()

    }

    private fun addImagesOver() {


        mViewModel.collectPrize()
        if (!mViewModel.collectPrizeResponse.hasActiveObservers()) {
            mViewModel.collectPrizeResponse.observe(requireActivity()) {
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


                                if( data.winnerData.isNotEmpty()){
                                    winnerid =  data.winnerData[0]._id

                                    Log.d("TAG", "addImagesOver: $winnerid")
                                }



                                data.winnerData.forEach {


//                                mViewDataBinding.textView3.setText(data.user.name)
//                                mViewDataBinding.textView4.setText(data.user.email)
//                                mViewDataBinding.textView52.setText(data.user.chances.toString())
//                                mViewDataBinding.textView51.setText(data.user.score.toString())
                                    if (it.raffle.prize.image.isNotEmpty()) {
                                        imageList2.add(it)
//                                        Picasso.get().load(it.raffle.prize.image).resize(500, 500)
//                                            .into(mViewDataBinding.profilePicture)
                                    }
                                }
//

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        mViewDataBinding.viewPager.adapter?.notifyDataSetChanged()
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }

        addingSliderAdapter()
    }


    /////
    private val imageList2 = arrayListOf<WinnerData>()
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

        val adapter =
            com.teamx.equiz.ui.fragments.collectPrice.adapter.ImageSliderAdapter(imageList2)
        mViewDataBinding.viewPager.adapter = adapter

        addDots(imageList.size)
        mViewDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })
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

    override fun onClickItem(position: Int) {

        when (position) {
            1 -> {
                navController = Navigation.findNavController(
                    requireActivity(), R.id.nav_host_fragment
                )
                navController.navigate(
                    R.id.chattFragment, null, options
                )
            }

            2 -> {

            }

            3 -> {

            }

            4 -> {

            }

            5 -> {

            }
        }

    }
    ////
}

interface ClaimInterfaceCallback {
    fun onClickItem(position: Int)
}