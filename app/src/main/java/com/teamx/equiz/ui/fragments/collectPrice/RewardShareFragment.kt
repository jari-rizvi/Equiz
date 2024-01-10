package com.teamx.equiz.ui.fragments.collectPrice

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.system.Os.link
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentRewardShareBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RewardShareFragment : BaseFragment<FragmentRewardShareBinding, CollectPriceViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_reward_share
    override val viewModel: Class<CollectPriceViewModel>
        get() = CollectPriceViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions


    @RequiresApi(Build.VERSION_CODES.O)
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

            mViewDataBinding.btnback.setOnClickListener {
                popUpStack()
            }

            var bundle = arguments

            if (bundle == null) {
                bundle = Bundle()
            }

            val img = bundle?.getString("img")
            Log.d("TAG", "onViewCreated121244444: $img")



            Glide.with(mViewDataBinding.container.context).load(img.toString())
                .into(mViewDataBinding.imagee)


            mViewDataBinding.btnProceed.setOnClickListener {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    // Example: content://com.google.android.apps.photos.contentprovider/...
                    putExtra(Intent.EXTRA_STREAM, img)
                    type = "image/jpeg"
                }
                startActivity(Intent.createChooser(shareIntent, null))
            }
        }


    }


}