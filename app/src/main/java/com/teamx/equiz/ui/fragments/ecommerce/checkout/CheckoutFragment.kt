package com.teamx.equiz.ui.fragments.ecommerce.checkout


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getorderData.ShippingInfo2
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCheckoutBinding
import com.teamx.equiz.ui.fragments.address.adapter.AddressesListAdapter
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.Data
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>(),
    OnCartListener {

    override val layoutId: Int
        get() = R.layout.fragment_checkout
    override val viewModel: Class<CheckoutViewModel>
        get() = CheckoutViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    var cartAdapter: CartAdapter? = null

    var addressArrayList: ArrayList<Data> = ArrayList()

    lateinit var cartArrayList2: ArrayList<com.teamx.equiz.data.models.getcart.Data>
    lateinit var adapter: ArrayAdapter<String>

    var totalPrice = 0.0
    var subTotal = 0.0
    lateinit var selectAddress: String
    private var singleAddress: Data? = null

    lateinit var items: ArrayList<String>
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


        mViewDataBinding.addAddress.setOnClickListener {
            findNavController().navigate(
                R.id.addressListFragment, arguments, options
            )
        }

        items = ArrayList()
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        mViewModel.getAddressList()

        if (!mViewModel.addressList.hasActiveObservers()) {
            mViewModel.addressList.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->


                            data.data.forEach {

                                Log.d("TAG", "dataaaa: $it")
                                addressArrayList.add(it)
                            }

                            addressArrayList.forEach {

                                items.add(it.label)
                                Log.d("TAG", "dataaaa: $it")
                            }
                            adapter.notifyDataSetChanged()
//                            addressAdapter.notifyDataSetChanged()

                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
                    }

                    Resource.Status.ERROR -> {
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.addressList.removeObservers(viewLifecycleOwner)
                }
            })
        }


        val spinner = mViewDataBinding.spinnerr
        try {

//            items = arrayOf(addressArrayList)

            Log.d("TAG", "dataaaa: $items")
            Log.d("TAG", "dataaaa: ${addressArrayList.size}")

            adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter


        } catch (e: Exception) {

        }


        try {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
//                     selectAddress = parent.getItemAtPosition(position) as String

                    addressArrayList.forEach {
                        it.value = false
                    }

                    addressArrayList[position].value = true

                    singleAddress = addressArrayList.get(position)


                    adapter.notifyDataSetChanged()


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        mViewDataBinding.btnApply.setOnClickListener {

            val code = mViewDataBinding.autoCompleteTextView.text.toString()

            if (code.isEmpty()) {
                mViewDataBinding.root.snackbar("Please Enter Coupon Code")
            } else {
                mViewModel.applyCoupon(code)
            }

            if (!mViewModel.applyCouponResponse.hasActiveObservers()) {
                mViewModel.applyCouponResponse.observe(requireActivity(), Observer {
                    when (it.status) {
                        Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                        }

                        Resource.Status.NOTVERIFY -> {
                            loadingDialog.dismiss()
                        }

                        Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.mainLayout.visibility = View.VISIBLE
                            it.data?.let { data ->

                                data.data.forEach {
                                    try {
                                        mViewDataBinding.amount.text = data.discount.toString()
                                        mViewDataBinding.qty.text =
                                            data.discountCoupon.amount.toString()
                                        mViewDataBinding.date.text = ""
                                        mViewDataBinding.orderno.text =
                                            "${data.data[0].totalPoint.toString()} Points"


                                    } catch (e: Exception) {
                                    }
                                }
//

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
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.applyCouponResponse.removeObservers(viewLifecycleOwner)
                    }
                })
            }

        }


        mViewModel.getCart()

        if (!mViewModel.getcartResponse.hasActiveObservers()) {
            mViewModel.getcartResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->

                            data.data.forEach {
                                if (it != null) {
                                    cartArrayList2.add(it)

                                    it.product?.let { itt ->
                                        subTotal += itt.point!! * it.totalPoint
                                    }


                                }
                                var countPrice = 0.0
                                data.data.forEach {
                                    countPrice += it.totalPoint
                                }



                                mViewDataBinding.amount.text = "$countPrice Points"
                                mViewDataBinding.date.text = ""
                                mViewDataBinding.qty.text = "0.0"
                                mViewDataBinding.orderno.text =
                                    "${data.data[0].totalPoint.toString()} Points"

                            }

                            mViewDataBinding.amount.text = data.cartPrice.toString()
                            mViewDataBinding.orderno.text = data.cartPrice.toString()



                                cartAdapter?.notifyDataSetChanged()

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
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getcartResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }
        cartRecyclerview()

        mViewDataBinding.btnProceed.setOnClickListener {
            if (cartArrayList2.isNotEmpty()) {
                val coupon = mViewDataBinding.autoCompleteTextView.text.toString().ifEmpty { "" }

                var bundle = arguments
                if (bundle == null) {
                    bundle = Bundle()
                }
                bundle.putString("couponCode", coupon)

                val label = singleAddress?.label ?: ""
                val etPhone = singleAddress?.phoneNumber ?: ""
                val address = singleAddress?.address ?: ""

                val params = JsonObject()
                try {
                    params.add(
                        "shippingInfo", Gson().toJsonTree(
                            ShippingInfo2(
                                address = address, phoneNumber = etPhone, label = label
                            )
                        )
                    )

                    if (coupon.isNotEmpty()) {
                        params.addProperty("couponCode", coupon)
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if (address.isNullOrEmpty()) {
                    showToast("Please add Address")
                } else {
                    if (etPhone.isNotEmpty() && address.isNotEmpty()) {

                        mViewModel.createOrder(params)
                    } else {
                        showToast("Please add Details")
                    }
                }


                if (!mViewModel.createOrderResponse.hasActiveObservers()) {
                    mViewModel.createOrderResponse.observe(requireActivity()) {
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

                                    var bundle = arguments
                                    if (bundle == null) {
                                        bundle = Bundle()
                                    }
                                    bundle!!.putString("order_id", data.data._id)
                                    bundle!!.putString("points", data.data.totalPoints.toString())

                                    Log.d("TAG", "onViewCreated2222222: ${data.data.totalPoints}")


                                    findNavController().navigate(
                                        R.id.paymentMethodsFragment, bundle, options
                                    )
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
                                loadingDialog.dismiss()
                                DialogHelperClass.errorDialog(
                                    requireContext(), it.message!!
                                )
                            }
                        }
                        if (isAdded) {
                            mViewModel.createOrderResponse.removeObservers(
                                viewLifecycleOwner
                            )
                        }
                    }
                }


//                findNavController().navigate(R.id.addressListCheckoutFragment, bundle, options)
            } else {
                showToast("Cart is Empty")
            }
        }

        if (!mViewModel.updateCartResponse.hasActiveObservers()) {
            mViewModel.updateCartResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            cartArrayList2.clear()
                            mViewModel.getCart()

                            cartAdapter?.notifyDataSetChanged()
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
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.updateCartResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }


    }


    private fun cartRecyclerview() {
        cartArrayList2 = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerView2.layoutManager = linearLayoutManager

        cartAdapter = CartAdapter(cartArrayList2, this)
        mViewDataBinding.recyclerView2.adapter = cartAdapter
    }

    override fun onRemoveToCartListener(position: Int) {


        val id = cartArrayList2[position].product._id


        mViewModel.deleteCart(id)
        if (!mViewModel.deleteCartResponse.hasActiveObservers()) {
            mViewModel.deleteCartResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            cartArrayList2.clear()
                            mViewModel.getCart()
                            cartAdapter?.notifyDataSetChanged()
                        }
                        mViewModel.getCart()
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
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.deleteCartResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }


    }


    override fun onAddClickListener(position: Int) {

        cartArrayList2!![position].quantity = cartArrayList2!![position].quantity + 1


        val params = JsonObject()
        try {
            params.addProperty("productId", cartArrayList2!![position].product._id)
            params.addProperty("quantity", cartArrayList2!![position].quantity)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mViewModel.updateCart(params)
    }

    override fun onSubClickListener(position: Int) {
        if (cartArrayList2!![position].quantity > 1) {
            cartArrayList2!![position].quantity = cartArrayList2!![position].quantity - 1
        }

        val params = JsonObject()
        try {
            params.addProperty("productId", cartArrayList2!![position].product._id)
            params.addProperty("quantity", cartArrayList2!![position].quantity)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mViewModel.updateCart(params)

    }


}

