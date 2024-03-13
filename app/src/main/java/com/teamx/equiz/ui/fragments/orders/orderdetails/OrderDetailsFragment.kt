package com.teamx.equiz.ui.fragments.orders.orderdetails

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
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
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentOrderDetailsBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import com.teamx.equiz.data.models.orderDetailData.ProductDetail
import org.json.JSONException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel>(),
    ProductCancelListener {

    override val layoutId: Int
        get() = R.layout.fragment_order_details
    override val viewModel: Class<OrderDetailsViewModel>
        get() = OrderDetailsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    var id: String = ""


    lateinit var productAdapter: ProductsAdapter

    lateinit var params: JsonObject

    lateinit var productArrayList: ArrayList<ProductDetail>

//     var OArrayList1: ArrayList<Orders> = ArrayList()
//     var CArrayList1: ArrayList<CartDetail> = ArrayList()


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
//                            productArrayList.add(it.data.data)


                            data.data.cartDetail.productDetails.forEach {
                                it.orderStatus = data.data.orders.orderStatus
                            }

                            data.data.cartDetail.productDetails.forEach {
                                productArrayList.add(it)
                            }

                            productAdapter.notifyDataSetChanged()


                            mViewDataBinding.orderId.text = data.data.orders._id
                            mViewDataBinding.paymentType.text = data.data.orders.paymentIntent
                            val o = data.data.orders.createdAt.toString().replaceAfter('T', "")
                                .replace("T", "")


                            /*      if (data.data.orders.orderStatus == "Processing") {
                                      mViewDataBinding.btnCancel.visibility = View.VISIBLE
                                  }
                                  if (data.data.orders.orderStatus == "Cancel") {
                                      mViewDataBinding.btnReOrder1.visibility = View.VISIBLE
                                  }

                                  if (data.data.orders.orderStatus == "Delivered") {
                                      mViewDataBinding.btnInvoice.visibility = View.VISIBLE
                                      mViewDataBinding.btnReOrder.visibility = View.VISIBLE
                                  }*/


                            mViewDataBinding.date.text = o

                            try {

                                mViewDataBinding.discount.text =
                                    data.data.orders.coupon!!.amount.toString()
                            } catch (e: Exception) {
                            }

                            if (data.data.orders.coupon == null) {
                                mViewDataBinding.total.text =
                                    data.data.orders.totalPoints.toString() + " Points"
                            } else {
                                mViewDataBinding.total.text =
                                    data.data.orders.discountedPrice.toString() + " Points"
                            }


                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle.putString("id", data.data.orders._id)
                            bundle.putString("paymentMethod", data.data.orders.paymentIntent)
                            bundle.putString("total", mViewDataBinding.total.text.toString())
                            bundle.putString("date", o)


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

        productAdapter = ProductsAdapter(productArrayList, this)
        mViewDataBinding.recyleritems.adapter = productAdapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCancelItemClick(position: Int) {

        CancelOrderDialog().show()

        var p_id = productArrayList[position].product._id

        params = JsonObject()
        try {
            params.addProperty("productId", p_id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }


    lateinit var cancel: TextView
    lateinit var ok: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    fun CancelOrderDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.order_cancel_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)

        cancel = dialog.findViewById(R.id.cancelBtn)
        ok = dialog.findViewById(R.id.ok)


        ok.setOnClickListener {
            mViewModel.cancelProduct(id, params)
            if (!mViewModel.cancelProductResponse.hasActiveObservers()) {
                mViewModel.cancelProductResponse.observe(requireActivity(), Observer {
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
                                productArrayList.clear()
                                mViewModel.orderDetail(id)

                                dialog.dismiss()

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
//                        loadingDialog.dismiss()
                            mViewDataBinding.shimmerLayout.stopShimmer()
                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.cancelProductResponse.removeObservers(viewLifecycleOwner)
                    }
                })
            }

        }


        cancel.setOnClickListener {
            dialog.dismiss()
        }



        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }


}