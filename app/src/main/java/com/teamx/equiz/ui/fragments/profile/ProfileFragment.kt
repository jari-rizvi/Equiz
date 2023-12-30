package com.teamx.equiz.ui.fragments.profile


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentProfileBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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


                                mViewDataBinding.textView3.setText(data.user.name)
                                mViewDataBinding.textView4.setText(data.user.email)
                                mViewDataBinding.textView52.setText(data.user.chances.toString())
                                mViewDataBinding.textView51.setText(data.user.score.toString())

                                if (!data.user.image.isNullOrEmpty()) {
//                                    Picasso.get().load(data.user.image).resize(500, 500)
//                                        .into(mViewDataBinding.profilePicture)

                                    Glide.with(mViewDataBinding.profilePicture.context).load(data.user.image).into(mViewDataBinding.profilePicture)
                                }

                                if (data.user.isPremium) {
                                    mViewDataBinding.btnUnSubscribe.visibility = View.VISIBLE
                                } else {
                                    mViewDataBinding.btnUnSubscribe.visibility = View.GONE

                                }

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




        mViewDataBinding.btnEditProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_editProfileFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnOrder.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_ordersFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnWishList.setOnClickListener {
            findNavController().navigate(
                R.id.wishlistFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnChance.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_chancesFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnleaderboard.setOnClickListener {
            findNavController().navigate(R.id.loaderBoardFragment, arguments, options)
        }
        mViewDataBinding.btnSecurity.setOnClickListener {
//            findNavController().navigate(R.id.loaderBoardFragment, arguments, options)

            val uri: Uri =
                Uri.parse("https://sites.google.com/view/equiz-privacy-policy?usp=sharing") // missing 'http://' will cause crashed


            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }
}