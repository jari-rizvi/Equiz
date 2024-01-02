package com.teamx.equiz.ui.fragments.ecommerce.allproduct


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.AllProductFragmentLayoutBinding
import com.teamx.equiz.ui.fragments.ecommerce.data.Category
import com.teamx.equiz.ui.fragments.ecommerce.home.CategoriesAdapter
import com.teamx.equiz.ui.fragments.ecommerce.home.EcommerceViewModel
import com.teamx.equiz.ui.fragments.ecommerce.home.OnProductListener
import com.teamx.equiz.ui.fragments.ecommerce.home.OnTopCategoriesListener
import com.teamx.equiz.ui.fragments.ecommerce.home.ProductAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class AllProductEcommerceFragment :
    BaseFragment<AllProductFragmentLayoutBinding, EcommerceViewModel>(), OnTopCategoriesListener,
    OnProductListener {

    override val layoutId: Int
        get() = R.layout.all_product_fragment_layout
    override val viewModel: Class<EcommerceViewModel>
        get() = EcommerceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel



    private lateinit var options: NavOptions

    lateinit var productAdapter: ProductAdapter
    lateinit var productArrayList: ArrayList<com.teamx.equiz.data.models.getProducts.Data>


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
            findNavController().popBackStack()
        }


        mViewModel.getProducts()

        if (!mViewModel.getProductsResponse.hasActiveObservers()) {
            mViewModel.getProductsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        productAdapter.arrayList.clear()
                        it.data?.let { data ->
                            data.data.forEach {
                                productAdapter.arrayList.add(it)
                                Log.d("TAG", "onViewCreated1212121212: $it")

                            }


                        }

                        mViewDataBinding.popularRecycler.adapter = productAdapter
                        mViewDataBinding.popularRecycler.adapter?.notifyDataSetChanged()
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
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







        if (!mViewModel.addtowishlistResponse.hasActiveObservers()) {
            mViewModel.addtowishlistResponse.observe(requireActivity()) {
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
                            showToast("Added To Wishlist")
                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
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
                    mViewModel.addtowishlistResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        if (!mViewModel.deleteToWishlistResponse.hasActiveObservers()) {
            mViewModel.deleteToWishlistResponse.observe(requireActivity()) {
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

                            showToast("Deleted From Wishlist")

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.deleteToWishlistResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }



        productRecyclerview()

    }


    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        mViewDataBinding.popularRecycler.layoutManager = linearLayoutManager

        productAdapter = ProductAdapter(productArrayList, this)
        mViewDataBinding.popularRecycler.adapter = productAdapter

    }

    override fun onTopSellerClick(position: Int, PrePos: Int) {

    }

    override fun onproductClick(position: Int) {
        val id_ = productArrayList[position]._id

        val bundle = Bundle()
        bundle.putString("id", id_)
        findNavController().navigate(
            R.id.action_ecommerceFragment_to_productProfileFragment,
            bundle
        )


    }

    override fun onAddFavClick(position: Int, isFav: Boolean) {


        val tick = productArrayList.get(position)

        var catId = tick._id

        Log.d("123123", "onTopSellerClick:$catId ")
        val params = JsonObject()
        try {
            params.addProperty("productId", catId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (productArrayList.get(position).isFavorite) {
            mViewModel.deleteToWishlist(params)
            productArrayList.get(position).isFavorite = false
        } else {
            mViewModel.addtowishlist(params)
            productArrayList.get(position).isFavorite = true
        }
//        mViewModel.addtowishlist(params)
//        strArrayList.forEach{
//            if (it.isSelected)
//            it.isSelected = false
//        }

        mViewDataBinding.popularRecycler.adapter?.notifyItemChanged(position)


    }

/////////////

//this can be used to open the slider at anytime in the dev phase

    /*    private fun addingSliderAdapter() {


            val imageList = listOf(
                R.drawable.argentina_32,
                R.drawable.brazil_31,
                R.drawable.pakistan_02,
                R.drawable.uae_04,
                R.drawable.usa_03,
                R.drawable.uk_05
                // Add more images as needed
            )

            val adapter = ImageSliderAdapter(imageList)
            mViewDataBinding.viewPager.adapter = adapter

            addDots(imageList.size)
            mViewDataBinding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateDots(position)
                }
            })
        }


        private fun addDots(count: Int) {
            for (i in 0 until count) {
                val dot = ImageView(requireContext())
                dot.setImageResource(R.drawable.dot_unselected)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, 0, 8, 0)
                mViewDataBinding.dotsContainer.addView(dot, params)
            }
            updateDots(0)
        }

        private fun updateDots(selectedPosition: Int) {
            val childCount = mViewDataBinding.dotsContainer.childCount
            for (i in 0 until childCount) {
                val dot = mViewDataBinding.dotsContainer.getChildAt(i) as ImageView
                if (i == selectedPosition) {
                    dot.setImageResource(R.drawable.dot_selected_dash)
                } else {
                    dot.setImageResource(R.drawable.dot_unselected)
                }
            }
        }*/

//////////////


}