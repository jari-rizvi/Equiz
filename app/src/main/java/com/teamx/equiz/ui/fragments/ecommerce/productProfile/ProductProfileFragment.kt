package com.teamx.equiz.ui.fragments.ecommerce.productProfile


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentProductProfileBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

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

    private var productId: String? = null

    var productQuantity = 1

    lateinit var ProductImageAdapter: ProductImagesAdapter
    lateinit var ProductImageArrayList: ArrayList<com.teamx.equiz.data.models.getProductById.Data>
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

        mViewDataBinding.btnWish.setOnClickListener {
            AddToWishList()
        }


        mViewDataBinding.btnremove.setOnClickListener {
            if (productQuantity > 1) {
                productQuantity--
            }
            mViewDataBinding.quantity.text = productQuantity.toString()
        }
        mViewDataBinding.btnadd.setOnClickListener {
            if (productQuantity >= 1) {
                productQuantity++
            }
            mViewDataBinding.quantity.text = productQuantity.toString()
        }



        mViewDataBinding.btnCheckout.setOnClickListener {
            AddToCart()
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


                            /*       it.data.data.images.forEach {
                                       if (it != null) {

                                       }
                                   }*/

                            ProductImageArrayList.add(it.data.data)
                            ProductImageAdapter.notifyDataSetChanged()
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

        initializeFeatureProducts()

    }

    fun AddToCart() {
        if (!productId!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("productId", productId)
                params.addProperty("quantity", productQuantity)
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            mViewModel.addtocart(params)

            if (!mViewModel.addtocartResponse.hasActiveObservers()) {
                mViewModel.addtocartResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                showToast("Cart Updated")

                            }
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.addtocartResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }

        }

    }

    fun AddToWishList() {
        if (!productId!!.isEmpty()) {

            val params = JsonObject()
            try {
                params.addProperty("productId", productId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }


            mViewModel.addtowishlist(params)

            if (!mViewModel.addtowishlistResponse.hasActiveObservers()) {
                mViewModel.addtowishlistResponse.observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Resource.Status.SUCCESS -> {
                            loadingDialog.dismiss()

                            it.data?.let { data ->

                                showToast("Added to Wishlist")

                            }
                        }

                        Resource.Status.ERROR -> {
                            loadingDialog.dismiss()
                            DialogHelperClass.errorDialog(requireContext(), it.message!!)
                        }
                    }
                    if (isAdded) {
                        mViewModel.addtowishlistResponse.removeObservers(viewLifecycleOwner)
                    }
                }
            }

        }

    }

    private val runnable = Runnable {
        mViewDataBinding.screenViewpager.currentItem =
            mViewDataBinding.screenViewpager.currentItem + 1
    }

    private fun initializeFeatureProducts() {

        ProductImageArrayList = ArrayList()

        ProductImageAdapter = ProductImagesAdapter(ProductImageArrayList)
        mViewDataBinding.screenViewpager.adapter = ProductImageAdapter

        TabLayoutMediator(
            mViewDataBinding.tabIndicator, mViewDataBinding.screenViewpager
        ) { tab, position ->
            tab.text = ProductImageArrayList[position].toString()
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


}