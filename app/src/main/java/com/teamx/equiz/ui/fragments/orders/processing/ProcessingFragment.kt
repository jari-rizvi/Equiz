package com.teamx.equiz.ui.fragments.orders.processing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentProcessingBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProcessingFragment : BaseFragment<FragmentProcessingBinding, ProcessingViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_processing
    override val viewModel: Class<ProcessingViewModel>
        get() = ProcessingViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var activeOrderAdapter: ProcessingAdapter

    lateinit var activeOrderArrayList: ArrayList<Data>

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


        try {
            mViewModel.getOrders( "Processing")
        } catch (e: Exception) {

        }

        if (!mViewModel.getOrdersResponse.hasActiveObservers()) {
            mViewModel.getOrdersResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    } /* Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                    onToSignUpPage()
                }*/

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            data.data.forEach {
                                activeOrderArrayList.add(it)
                            }

                            activeOrderAdapter.notifyDataSetChanged()


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
                    mViewModel.getOrdersResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }
        productRecyclerview()
    }


    private fun productRecyclerview() {
        activeOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.ProcessingRecyclerView.layoutManager = linearLayoutManager

        activeOrderAdapter = ProcessingAdapter(activeOrderArrayList)
        mViewDataBinding.ProcessingRecyclerView.adapter = activeOrderAdapter

    }


}