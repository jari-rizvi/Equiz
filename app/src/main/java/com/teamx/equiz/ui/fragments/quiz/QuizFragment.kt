package com.teamx.equiz.ui.fragments.quiz


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
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
import com.teamx.equiz.ui.fragments.wishlist.FavouriteAdapter
import com.teamx.equiz.ui.fragments.wishlist.WishlistViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>() {

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
        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewModel.getWishlist()

        if (!mViewModel.wishlistResponse.hasActiveObservers()) {
            mViewModel.wishlistResponse.observe(requireActivity()) {
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
                                favouriteArrayList.add(it.product)
                            }
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
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
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

//        favouriteAdapter = FavouriteAdapter(favouriteArrayList,this)
        mViewDataBinding.recyclerView.adapter = favouriteAdapter

    }
}