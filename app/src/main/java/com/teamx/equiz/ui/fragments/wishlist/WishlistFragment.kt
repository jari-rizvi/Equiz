package com.teamx.equiz.ui.fragments.wishlist


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentWishlistBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>(),
    WishListListener {

    override val layoutId: Int
        get() = R.layout.fragment_wishlist
    override val viewModel: Class<WishlistViewModel>
        get() = WishlistViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


    lateinit var favouriteAdapter: FavouriteAdapter
    lateinit var favouriteArrayList: ArrayList<Product>


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

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewModel.getWishlist()

        if (!mViewModel.wishlistResponse.hasActiveObservers()) {
            mViewModel.wishlistResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        favouriteArrayList.clear()
                        it.data?.let { data ->

                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.mainLayout.visibility = View.VISIBLE

                            data.data.forEach {
                                favouriteArrayList.add(it.product)
                            }

                            Log.d("TAG", "wishlist: ${data.data}")

                            favouriteAdapter.notifyDataSetChanged()


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
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.wishlistResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        favRecyclerview()
    }

    private fun favRecyclerview() {
        favouriteArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerView.layoutManager = linearLayoutManager

        favouriteAdapter = FavouriteAdapter(favouriteArrayList, this)
        mViewDataBinding.recyclerView.adapter = favouriteAdapter

    }

    override fun onClickItem(position: Int) {
        val favItem = favouriteArrayList.get(position)
        var catId = favItem._id

        Log.d("123123", "onTopSellerClick:$catId ")
        val params = JsonObject()
        try {
            params.addProperty("productId", catId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.deleteToWishlist(params)
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
                            mViewModel.getWishlist()

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
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.deleteToWishlistResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }


    }

    override fun onAddToCart(position: Int) {

        var productId = favouriteArrayList[position]._id
        val params = JsonObject()
        try {
            params.addProperty("productId", productId)
            params.addProperty("quantity", "1")
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
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()

                        it.data?.let { data ->
                            if (isAdded) {
                                findNavController().navigate(
                                    R.id.checkoutFragment,
                                    arguments,
                                    options
                                )
                            }


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