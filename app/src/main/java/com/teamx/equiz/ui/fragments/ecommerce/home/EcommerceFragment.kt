package com.teamx.equiz.ui.fragments.ecommerce.home


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.categoriesData.Data
import com.teamx.equiz.data.models.categoriesData.GetAllCategoriesData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EcommerceFragment : BaseFragment<FragmentEcommerceBinding, EcommerceViewModel>(),
    OnTopCategoriesListener {

    override val layoutId: Int
        get() = R.layout.fragment_ecommerce
    override val viewModel: Class<EcommerceViewModel>
        get() = EcommerceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoriesArrayList2: ArrayList<GetAllCategoriesData>

    private lateinit var options: NavOptions


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
//
//        mViewModel.getCategories()
//
//        if (!mViewModel.getcategoriesResponse.hasActiveObservers()) {
//            mViewModel.getcategoriesResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//                            data.data.forEach {
//                                categoriesArrayList2.add(it.)
//                            }
//
//                            categoriesAdapter.notifyDataSetChanged()
//
//
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        DialogHelperClass.errorDialog(
//                            requireContext(),
//                            it.message!!
//                        )
//                    }
//                }
//                if (isAdded) {
//                    mViewModel.getcategoriesResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
//                }
//            }
//        }

        categoriesRecyclerview()
    }

    private fun categoriesRecyclerview() {
        categoriesArrayList2 = ArrayList()


        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.categoriesRecycler.layoutManager = linearLayoutManager

        categoriesAdapter = CategoriesAdapter(categoriesArrayList2, this)
        mViewDataBinding.categoriesRecycler.adapter = categoriesAdapter
    }

    override fun onTopSellerClick(position: Int) {
        TODO("Not yet implemented")
    }

}