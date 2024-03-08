package com.teamx.equiz.ui.fragments.ecommerce.home


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.compose.ui.text.font.FontSynthesis.Companion.All
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEcommerceBinding
import com.teamx.equiz.ui.fragments.dashboard.adapter.ImageSliderAdapter
import com.teamx.equiz.ui.fragments.dashboard.adapter.OnSliderClickListner
import com.teamx.equiz.ui.fragments.ecommerce.data.Category
import com.teamx.equiz.ui.fragments.ecommerce.home.datanews.Data
import com.teamx.equiz.ui.fragments.news.onNewslistner
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class EcommerceFragment : BaseFragment<FragmentEcommerceBinding, EcommerceViewModel>(),
    OnTopCategoriesListener, OnProductListener, OnSliderClickListner {

    override val layoutId: Int
        get() = R.layout.fragment_ecommerce
    override val viewModel: Class<EcommerceViewModel>
        get() = EcommerceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var timer: Timer
    private val delayInMillis: Long = 2000 // Adjust the delay time as needed
    private val handlerr = Handler(Looper.getMainLooper())

    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoriesArrayList2: ArrayList<Category>

    private lateinit var options: NavOptions

    lateinit var productAdapter: ProductAdapter
    lateinit var productArrayList: ArrayList<com.teamx.equiz.data.models.getProducts.Data>

    lateinit var featureProductAdapter: ProductBannersAdapter
    lateinit var featureProductArrayList: ArrayList<com.teamx.equiz.data.models.bannerData.news_banner.New>
    private var tabLayoutMediator: TabLayoutMediator? = null
    private lateinit var handler: Handler

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


        mViewDataBinding.btnmenu.setOnClickListener {

            findNavController().navigate(
                R.id.settingsFragment, arguments, options
            )
        }

        mViewDataBinding.btnWishlist.setOnClickListener {

            if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                    "null",
                    true
                )
            ) {
                if (isAdded) {
                    try {
                        DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return@setOnClickListener
            }

            findNavController().navigate(
                R.id.wishlistFragment, arguments, options
            )
        }
        mViewDataBinding.managerOders.setOnClickListener {
            if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                    "null",
                    true
                )
            ) {
                if (isAdded) {
                    try {
                        DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return@setOnClickListener
            }

            findNavController().navigate(
                R.id.ordersFragment, arguments, options
            )
        }
        mViewDataBinding.textView155.setOnClickListener {

            findNavController().navigate(
                R.id.allProductEcommerceFragment, arguments, options
            )
        }
        mViewDataBinding.btnCart.setOnClickListener {
            findNavController().navigate(
                R.id.action_ecommerceFragment_to_checkoutFragment
            )
        }


        /* mViewModel.getBanners()

         if (!mViewModel.getBannerResponse.hasActiveObservers()) {
             mViewModel.getBannerResponse.observe(requireActivity()) {
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

                             data.news.forEach {
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
         }*/


        mViewModel.getProducts(null, null)

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
                            productArrayList.clear()
                            data.data.forEach {
                                productAdapter.arrayList.add(it)
                                Log.d("TAG", "onViewCreated1212121212: $it")

                            }


                        }

                        mViewDataBinding.popularRecycler.adapter = productAdapter
                        mViewDataBinding.popularRecycler.adapter?.notifyDataSetChanged()
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

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            categoriesArrayList2.clear()
                            categoriesArrayList2.add(Category(0, "0", "", "All", "", true))
                            data.category.forEach {
                                categoriesArrayList2.add(it)
                            }
                            Log.d("123123", "onViewCreated:${categoriesArrayList2.size} ")
                            categoriesAdapter.notifyDataSetChanged()

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
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.deleteToWishlistResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }

        if (!mViewModel.getBannerResponse.hasActiveObservers()) {
            mViewModel.getBannerResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
//                        mViewDataBinding.shimmerLayout.startShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
//                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        imageList2.clear()
                        it.data?.let { data ->
//                            data.game.forEach {
//                                winnerArrayList.add(it)
//                            }
                            data.newsData.forEach {
                                imageList2.add(Data(it._id, it.image))
                            }
//                            winnerAdapter.notifyDataSetChanged()


                        }
                        mViewDataBinding.viewPager.adapter?.notifyDataSetChanged()

                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
//                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
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
//                        mViewDataBinding.shimmerLayout.stopShimmer()
//                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        DialogHelperClass.errorDialog(
                            requireContext(), it.message!!
                        )
                    }
                }
                if (isAdded) {
//                    mViewModel.getBannerResponse.removeObservers(
//                        viewLifecycleOwner
//                    )
                }
            }
        }



        productRecyclerview()
//        initializeFeatureProducts()

        categoriesRecyclerview()
//        addingSliderAdapter()
        performSearch()
        addingSliderAdapter()

//        getMeApi()
        addNewBanners()
    }

    private fun performSearch() {
        mViewDataBinding.editSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                mViewModel.getProducts(
                    null,
                    keyword = "${mViewDataBinding.editSearch.text.toString()}"
                )
                return@setOnEditorActionListener true
            }
            false
        }
    }

    //    private val imageList2 = arrayListOf<String>()
    private val imageList2 =
        arrayListOf<com.teamx.equiz.ui.fragments.ecommerce.home.datanews.Data>()

    private fun addingSliderAdapter() {


        val imageList = listOf(
            R.drawable.argentina_32,
            R.drawable.brazil_31,
            R.drawable.pakistan_02,
            R.drawable.uae_04,
            R.drawable.usa_03,
            R.drawable.uk_05
            // Add more images as needed
        )

        val adapter = ImageSliderAdapter(imageList2, this)
        mViewDataBinding.viewPager.adapter = adapter

        addDots(imageList.size)
        mViewDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })

        // Initialize the timer
        timer = Timer()
        var a = mViewDataBinding.viewPager
        // Schedule the automatic sliding task
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handlerr.post {
                    val currentItem = a.currentItem
                    val itemCount = adapter.itemCount

                    // If the current item is the last one, set the item to the first
                    a.currentItem = if (currentItem < itemCount - 1) currentItem + 1 else 0
                }
            }
        }, 0, delayInMillis)
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
    }


//    private val runnable = Runnable {
//        mViewDataBinding.screenViewpager.currentItem =
//            mViewDataBinding.screenViewpager.currentItem + 1
//    }

//    private fun initializeFeatureProducts() {
//
//        featureProductArrayList = ArrayList()
//
//        featureProductAdapter = ProductBannersAdapter(featureProductArrayList)
//        mViewDataBinding.screenViewpager.adapter = featureProductAdapter
//
//        TabLayoutMediator(
//            mViewDataBinding.tabIndicator, mViewDataBinding.screenViewpager
//        ) { tab, position ->
//            tab.text = featureProductArrayList[position].toString()
//        }.attach()
//
//        tabLayoutMediator = TabLayoutMediator(
//            mViewDataBinding.tabIndicator, mViewDataBinding.screenViewpager
//        ) { tab: TabLayout.Tab, position: Int ->
//            mViewDataBinding.screenViewpager.setCurrentItem(tab.position, true)
//        }
//        tabLayoutMediator!!.attach()
//
//        mViewDataBinding.screenViewpager.offscreenPageLimit = 3
//        mViewDataBinding.screenViewpager.clipToPadding = false
//        mViewDataBinding.screenViewpager.clipChildren = false
//        mViewDataBinding.screenViewpager.getChildAt(0).overScrollMode =
//            RecyclerView.OVER_SCROLL_NEVER
//
//        handler = Handler(Looper.myLooper()!!)
//
//        mViewDataBinding.screenViewpager.registerOnPageChangeCallback(object :
//            ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                handler.removeCallbacks(runnable)
//                handler.postDelayed(runnable, 2000)
//            }
//        })
//
//
//    }

    private fun categoriesRecyclerview() {
        categoriesArrayList2 = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mViewDataBinding.categoriesRecycler.layoutManager = linearLayoutManager

        categoriesAdapter = CategoriesAdapter(categoriesArrayList2, this)
        mViewDataBinding.categoriesRecycler.adapter = categoriesAdapter
    }

    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        mViewDataBinding.popularRecycler.layoutManager = linearLayoutManager

        productAdapter = ProductAdapter(productArrayList, this, false)
        mViewDataBinding.popularRecycler.adapter = productAdapter

    }

    override fun onTopSellerClick(position: Int, PrePos: Int) {
        categoriesArrayList2.forEach {
            it.isChecked = false
        }
        if(position == 0){

            mViewModel.getProducts(null, null)
            categoriesArrayList2[0].isChecked = true
            mViewDataBinding.categoriesRecycler.adapter?.notifyDataSetChanged()
            return
        }

        val tick = categoriesArrayList2.get(position)

        var catId = tick._id

        Log.d("123123", "onTopSellerClick:$catId ")
        mViewModel.getProducts(catId, "")
//        strArrayList.forEach{
//            if (it.isSelected)
//            it.isSelected = false
//        }
        /*if (PrePos != -1) {
            categoriesArrayList2.get(PrePos).isChecked = false
            mViewDataBinding.categoriesRecycler.adapter?.notifyItemChanged(PrePos)
        }*/
        categoriesArrayList2[position].isChecked = true
//        mViewDataBinding.categoriesRecycler.adapter?.notifyItemChanged(position)
        mViewDataBinding.categoriesRecycler.adapter?.notifyDataSetChanged()
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

    var userId = ""
    fun getMeApi() {
        mViewModel.me()
        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
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

                            try {

                                userId = data.user._id

                                val params = JsonObject()
                                try {
                                    params.addProperty("userId", "$userId")
                                    mViewModel.getBanners(params)


                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }
    }


    fun addNewBanners() {
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        var country = bundle?.getString("country")


        if (country.isNullOrEmpty()) {
            country = PrefHelper.getInstance(requireContext()).getCountry
            mViewModel.getBanners2(country.toString())
        } else {
            mViewModel.getBanners2("$country")

        }
//        mViewModel.getBanners2("World")
        if (!mViewModel.getBannerResponse2.hasActiveObservers()) {
            mViewModel.getBannerResponse2.observe(requireActivity()) {
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

                            try {

                                imageList2.clear()
                                it.data?.let { data ->

                                    data.data.forEach {
                                        imageList2.add(Data(it._id, it.image))
                                    }


                                }
                                mViewDataBinding.viewPager.adapter?.notifyDataSetChanged()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }
    }

    override fun onSliderClick(position: Int) {
        val newsArraya = imageList2[position]

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        if (NetworkCallPoints.TOKENER.isNullOrEmpty() || NetworkCallPoints.TOKENER.equals(
                "null",
                true
            )
        ) {
            DialogHelperClass.signUpLoginDialog(requireContext(), this).show()
            return
        }


        bundle.putString("id", newsArraya._id)

        findNavController().navigate(
            R.id.newsDetailFragment,
            bundle,
            options
        )
    }


}