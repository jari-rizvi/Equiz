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
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.PlayQuizLayoutBinding
import com.teamx.equiz.ui.fragments.wishlist.FavouriteAdapter
import com.teamx.equiz.ui.fragments.wishlist.WishlistViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayQuizFragment : BaseFragment<PlayQuizLayoutBinding, WishlistViewModel>() {

    override val layoutId: Int
        get() = R.layout.play_quiz_layout
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
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
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
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
        }
        val bundle = arguments
       val id=  bundle?.getString("modelQuizId")
        mViewDataBinding.playAgainBtn.setOnClickListener {

            findNavController().navigate(R.id.singleQuizFragment,arguments,options)

        }


    }

}