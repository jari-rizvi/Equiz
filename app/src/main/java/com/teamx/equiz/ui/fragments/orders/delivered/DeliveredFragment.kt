package com.teamx.equiz.ui.fragments.orders.delivered

import android.os.Bundle
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
import com.teamx.equiz.databinding.FragmentDeliveredBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveredFragment : BaseFragment<FragmentDeliveredBinding, DeliveredViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_delivered
    override val viewModel: Class<DeliveredViewModel>
        get() = DeliveredViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var deliveredOrderAdapter: DeliveredAdapter

    lateinit var deliveredOrderArrayList: ArrayList<Data>

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
            mViewModel.getOrders( "Delivered")
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
                                deliveredOrderArrayList.add(it)
                            }

                            deliveredOrderAdapter.notifyDataSetChanged()


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
        deliveredOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.deliveredRecyclerView.layoutManager = linearLayoutManager

        deliveredOrderAdapter = DeliveredAdapter(deliveredOrderArrayList)
        mViewDataBinding.deliveredRecyclerView.adapter = deliveredOrderAdapter

    }


}