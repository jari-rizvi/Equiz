package com.teamx.equiz.ui.fragments.ecommerce.checkout


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCheckoutBinding
import com.teamx.equiz.utils.DialogHelperClass
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

    lateinit var cartArrayList2: ArrayList<com.teamx.equiz.data.models.getcart.Data>
    var totalPrice = 0.0
    var subTotal = 0.0
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


        mViewModel.getCart()
        if (!mViewModel.getcartResponse.hasActiveObservers()) {
            mViewModel.getcartResponse.observe(requireActivity(), Observer {
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

                            data.data.forEach {
                                if (it != null) {
                                    cartArrayList2.add(it)

                                    it.product?.let { itt ->
                                        subTotal += itt.price!! * it.totalPrice
                                    }


                                }

                                      mViewDataBinding.amount.text = data.cartPrice.toString()
                                      mViewDataBinding.date.text = ""
                                      mViewDataBinding.qty.text = "0.0"
                                      mViewDataBinding.orderno.text = "${ data.cartPrice.toString()}"
                            }
//                                      mViewDataBinding.orderno.text = it.or
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
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
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
                findNavController().navigate(R.id.addressListFragment, arguments, options)
            } else {
                showToast("Cart is Empty")
            }
        }





        if (!mViewModel.updateCartResponse.hasActiveObservers()) {
            mViewModel.updateCartResponse.observe(requireActivity(), Observer {
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
                            cartArrayList2.clear()
                            mViewModel.getCart()
                            cartAdapter?.notifyDataSetChanged()
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
                            cartArrayList2  .clear()
                            mViewModel.getCart()
                            cartAdapter?.notifyDataSetChanged()
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
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.deleteCartResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }


    }


    ///////


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


    /////

}

