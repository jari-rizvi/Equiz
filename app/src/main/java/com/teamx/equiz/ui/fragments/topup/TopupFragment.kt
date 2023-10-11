package com.teamx.equiz.ui.fragments.topup


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentTopUpBinding
import com.teamx.equiz.databinding.FragmentWalletBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.wallet.WalletAdapter
import com.teamx.equiz.ui.fragments.wallet.WalletViewModel
import com.teamx.equiz.ui.fragments.wishlist.FavouriteAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopupFragment : BaseFragment<FragmentTopUpBinding, TopupViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_top_up
    override val viewModel: Class<TopupViewModel>
        get() = TopupViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

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





    }


}