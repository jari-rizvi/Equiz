package com.teamx.equiz.ui.fragments.ecommerce.home


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.categoriesData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EcommerceFragment : BaseFragment<FragmentEcommerceBinding, EcommerceViewModel>(),
    OnTopCategoriesListener,OnProductListener {

    override val layoutId: Int
        get() = R.layout.fragment_ecommerce
    override val viewModel: Class<EcommerceViewModel>
        get() = EcommerceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoriesArrayList2: ArrayList<Data>

    private lateinit var options: NavOptions

    lateinit var productAdapter: ProductAdapter
    lateinit var productArrayList: ArrayList<com.teamx.equiz.data.models.getProducts.Data>

    lateinit var featureProductAdapter: ProductBannersAdapter
    lateinit var featureProductArrayList: ArrayList<com.teamx.equiz.data.models.bannerData.Data>
    private var tabLayoutMediator: TabLayoutMediator? = null
    private lateinit var handler: Handler

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


        mViewModel.getBanners()

        if (!mViewModel.getBannerResponse.hasActiveObservers()) {
            mViewModel.getBannerResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            data.data?.forEach {
                                if (it != null) {
                                    featureProductArrayList.add(it)
                                }

                            }
                            featureProductAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getBannerResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }


        mViewModel.getProducts()

        if (!mViewModel.getProductsResponse.hasActiveObservers()) {
            mViewModel.getProductsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            data.data?.forEach {
                                if (it != null) {
                                    productArrayList.add(it)
                                }

                            }
                            featureProductAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getProductsResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }


        mViewModel.getCategories()

        if (!mViewModel.getcategoriesResponse.hasActiveObservers()) {
            mViewModel.getcategoriesResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            data.data.forEach {
                                categoriesArrayList2.add(it)
                            }

                            categoriesAdapter.notifyDataSetChanged()

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
                    mViewModel.getcategoriesResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        initializeFeatureProducts()

        categoriesRecyclerview()
        productRecyclerview()
    }
    private val runnable = Runnable {
        mViewDataBinding.screenViewpager.currentItem =
            mViewDataBinding.screenViewpager.currentItem + 1
    }
    private fun initializeFeatureProducts() {

        featureProductArrayList = ArrayList()

        featureProductAdapter = ProductBannersAdapter(featureProductArrayList)
        mViewDataBinding.screenViewpager.adapter = featureProductAdapter

        TabLayoutMediator(
            mViewDataBinding.tabIndicator, mViewDataBinding.screenViewpager
        ) { tab, position ->
            tab.text = featureProductArrayList[position].toString()
        }.attach()

        tabLayoutMediator = TabLayoutMediator(
            mViewDataBinding.tabIndicator, mViewDataBinding.screenViewpager
        ) { tab: TabLayout.Tab, position: Int ->
            mViewDataBinding.screenViewpager.setCurrentItem(tab.position, true)
        }
        tabLayoutMediator!!.attach()

        mViewDataBinding.screenViewpager.offscreenPageLimit = 3
        mViewDataBinding.screenViewpager.clipToPadding = false
        mViewDataBinding.screenViewpager.clipChildren = false
        mViewDataBinding.screenViewpager.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        handler = Handler(Looper.myLooper()!!)

        mViewDataBinding.screenViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })


    }

    private fun categoriesRecyclerview() {
        categoriesArrayList2 = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.categoriesRecycler.layoutManager = linearLayoutManager

        categoriesAdapter = CategoriesAdapter(categoriesArrayList2, this)
        mViewDataBinding.categoriesRecycler.adapter = categoriesAdapter
    }
    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = GridLayoutManager(context, 2,RecyclerView.VERTICAL, false)
        mViewDataBinding.popularRecycler.layoutManager = linearLayoutManager

        productAdapter = ProductAdapter(productArrayList, this)
        mViewDataBinding.popularRecycler.adapter = productAdapter

    }

    override fun onTopSellerClick(position: Int) {

    }

    override fun onproductClick(position: Int) {
    }

    override fun onAddFavClick(position: Int, isFav: Boolean) {
    }

}