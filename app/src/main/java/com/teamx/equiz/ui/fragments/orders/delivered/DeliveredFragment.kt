package com.teamx.equiz.ui.fragments.orders.delivered

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentDeliveredBinding
import com.teamx.equiz.ui.fragments.orders.OrderListener
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class DeliveredFragment : BaseFragment<FragmentDeliveredBinding, DeliveredViewModel>(),
    OrderListener {

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

        try {
            mViewModel.getOrders( "Delivered")
        } catch (e: Exception) {

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
                    onToSignUpPage()
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
                                deliveredOrderArrayList.add(it)
                            }

                            deliveredOrderAdapter.notifyDataSetChanged()


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
        deliveredOrderArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.deliveredRecyclerView.layoutManager = linearLayoutManager

        deliveredOrderAdapter = DeliveredAdapter(deliveredOrderArrayList,this)
        mViewDataBinding.deliveredRecyclerView.adapter = deliveredOrderAdapter

    }

    override fun onItemClick(position: Int) {
        var id =deliveredOrderArrayList[position]._id
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