package com.teamx.equiz.ui.fragments.ecommerce.checkout


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.categoriesData.Data
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCheckoutBinding
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.ui.fragments.ecommerce.home.CategoriesAdapter
import com.teamx.equiz.ui.fragments.ecommerce.home.EcommerceViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, CheckoutViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_checkout
    override val viewModel: Class<CheckoutViewModel>
        get() = CheckoutViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    var cartAdapter: CartAdapter? = null
    lateinit var cartArrayList2: ArrayList<com.teamx.equiz.data.models.getcart.Data>


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

        mViewModel.getCart()
        if (!mViewModel.getcartResponse.hasActiveObservers()) {
            mViewModel.getcartResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            data.data.forEach {
                                if (it != null) {
                                    cartArrayList2.add(it)
                                }
                          /*      mViewDataBinding.amount.text = it.price
                                mViewDataBinding.orderno.text = it.or*/

                            }
                            cartAdapter?.notifyDataSetChanged()

                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getcartResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }
        cartRecyclerview()

    }

    private fun cartRecyclerview() {
        cartArrayList2 = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerView2.layoutManager = linearLayoutManager

        cartAdapter = CartAdapter(cartArrayList2)
        mViewDataBinding.recyclerView2.adapter = cartAdapter
    }
}