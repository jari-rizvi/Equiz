package com.teamx.equiz.ui.fragments.subscription


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.utils.DialogHelperClass

@AndroidEntryPoint
class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding, SubscriptionViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_subscription
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
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


        mViewDataBinding.btnBuy.setOnClickListener {
                bottomSheetBehavior =
                    BottomSheetBehavior.from(mViewDataBinding.bottomSheetLayout.bottomSheetStripe)

                bottomSheetBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_EXPANDED -> MainActivity.bottomNav?.visibility =
                                View.GONE

                            BottomSheetBehavior.STATE_COLLAPSED -> MainActivity.bottomNav?.visibility =
                                View.VISIBLE

                            else -> "Persistent Bottom Sheet"
                        }
                    }
                })

                val state =
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_COLLAPSED
                    else BottomSheetBehavior.STATE_EXPANDED
                bottomSheetBehavior.state = state


        }

        mViewModel.getPlan()

        if (!mViewModel.getPlanResponse.hasActiveObservers()) {
            mViewModel.getPlanResponse.observe(requireActivity()) {
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
                            /*    data.data.forEach {
                                    couponsArrayList.add(it)
                                }*/

                            val divide = "${data.dataObject.amount / 100}"+"AED"

                            mViewDataBinding.textView61.text = divide.toString()


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
                    mViewModel.getPlanResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


    }
}