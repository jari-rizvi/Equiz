package com.teamx.equiz.ui.fragments.orders.orderdetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
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
import com.teamx.equiz.data.models.orderDetailData.ProductDetail
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentOrderDetailsBinding
import com.teamx.equiz.ui.fragments.orders.delivered.DeliveredAdapter
import com.teamx.equiz.ui.fragments.orders.processing.ProcessingAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import androidx.activity.addCallback
@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_order_details
    override val viewModel: Class<OrderDetailsViewModel>
        get() = OrderDetailsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    var id: String = ""


    lateinit var productAdapter: ProductsAdapter

    lateinit var productArrayList: ArrayList<ProductDetail>


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

        mViewDataBinding.btnback.setOnClickListener {
            popUpStack()
        }

        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("id").toString()
            Timber.tag("TAG").d(id.toString())
        }

        mViewDataBinding.btnInvoice.setOnClickListener {
            navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host_fragment
            )
            navController.navigate(R.id.recieptFragment, bundle, options)
        }


        Log.d("TAG", "IdGet: $id")

        mViewModel.orderDetail(id)
        if (!mViewModel.orderDetailResponse.hasActiveObservers()) {
            mViewModel.orderDetailResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->

                            data.data.cartDetail.productDetails.forEach {
                                productArrayList.add(it)
                            }

                            productAdapter.notifyDataSetChanged()


                            mViewDataBinding.orderId.text = data.data.orders._id
                            mViewDataBinding.paymentType.text = data.data.orders.paymentIntent
                            val o = data.data.orders.createdAt.toString().replaceAfter('T', "")
                                .replace("T", "")

                            mViewDataBinding.date.text = o
//                            mViewDataBinding.discount.text = data.data.orders.d
                            mViewDataBinding.total.text = data.data.orders.totalPrice.toString()


                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle.putString("id", data.data.orders._id)
                            bundle.putString("paymentMethod", data.data.orders.paymentIntent)
                            bundle.putString("total", data.data.orders.totalPrice.toString())
                            bundle.putString("date", o)


                        }
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.orderDetailResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }

        productRecyclerview()
    }


    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyleritems.layoutManager = linearLayoutManager

        productAdapter = ProductsAdapter(productArrayList)
        mViewDataBinding.recyleritems.adapter = productAdapter

    }


}