package com.teamx.equiz.ui.fragments.orders.cancelled

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCanclledBinding
import com.teamx.equiz.ui.fragments.orders.OrderListener
import com.teamx.equiz.ui.fragments.orders.processing.ProcessingAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
@AndroidEntryPoint
class CancelledFragment : BaseFragment<FragmentCanclledBinding, CancelledViewModel>(),
    OrderListener {

    override val layoutId: Int
        get() = R.layout.fragment_canclled
    override val viewModel: Class<CancelledViewModel>
        get() = CancelledViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var cancelOrderAdapter: CancelledAdapter

    lateinit var cancelOrderArrayList: ArrayList<Data>

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

        sharedViewModel.setActiveUser("")

        try {
            mViewModel.getOrders("Cancelled")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!mViewModel.getOrdersResponse.hasActiveObservers()) {
            mViewModel.getOrdersResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    } /* Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                     if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                }*/
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->

                            data.data.forEach {
                                cancelOrderArrayList.add(it)
                            }

                            cancelOrderAdapter.notifyDataSetChanged()


                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                         if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getOrdersResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }
        productRecyclerview()
    }


    private fun productRecyclerview() {
        cancelOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.cancelledRecyclerView.layoutManager = linearLayoutManager

        cancelOrderAdapter = CancelledAdapter(cancelOrderArrayList, this)
        mViewDataBinding.cancelledRecyclerView.adapter = cancelOrderAdapter

    }

    override fun onItemClick(position: Int) {
        var id = cancelOrderArrayList[position]._id
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("id", id)

        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )
        navController.navigate(R.id.orderDetailsFragment, bundle, options)

    }


}