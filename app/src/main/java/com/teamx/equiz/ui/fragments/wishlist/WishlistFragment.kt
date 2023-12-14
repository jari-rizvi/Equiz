package com.teamx.equiz.ui.fragments.wishlist


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.data.models.wishlistdata.WishlistData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentWishlistBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        mViewModel.getWishlist()

        if (!mViewModel.wishlistResponse.hasActiveObservers()) {
            mViewModel.wishlistResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.root.visibility = View.VISIBLE

                            data.data.forEach {
                                favouriteArrayList.add(it.product)
                            }

                            favouriteAdapter.notifyDataSetChanged()


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

        favouriteAdapter = FavouriteAdapter(favouriteArrayList)
        mViewDataBinding.recyclerView.adapter = favouriteAdapter

    }
}