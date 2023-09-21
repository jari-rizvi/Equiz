package com.teamx.equiz.ui.fragments.ecommerce.productProfile


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.databinding.FragmentProductProfileBinding
import com.teamx.equiz.ui.fragments.ecommerce.home.EcommerceViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductProfileFragment :
    BaseFragment<FragmentProductProfileBinding, ProductProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_product_profile
    override val viewModel: Class<ProductProfileViewModel>
        get() = ProductProfileViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    private  var productId : String? = null

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

        val bundle = arguments
        if (bundle != null) {
            productId = bundle.getString("id").toString()

        }

        mViewModel.productById(productId.toString())
        if (!mViewModel.productbyidResponse.hasActiveObservers()) {
            mViewModel.productbyidResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            mViewDataBinding.productName.text = it.data.data.title
                            mViewDataBinding.productPrice.text = it.data.data.price.toString()
                            mViewDataBinding.desc.text = it.data.data.description


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.productbyidResponse.removeObservers(viewLifecycleOwner)
                }
            })
        }



    }
}