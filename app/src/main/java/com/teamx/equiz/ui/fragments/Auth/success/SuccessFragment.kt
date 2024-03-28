package com.teamx.equiz.ui.fragments.Auth.success


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.databinding.FragmentSuccessBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.Auth.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : BaseFragment<FragmentSuccessBinding, SignupViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_success
    override val viewModel: Class<SignupViewModel>
        get() = SignupViewModel::class.java
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

        sharedViewModel.setActiveUser("")
        MainActivity.isiaDialog = true

        mViewDataBinding.btnVerify.setOnClickListener {

            var bundle = arguments

            if (bundle == null) {
                bundle = Bundle()
            }
            val token_id = bundle.getString("token_id")
            NetworkCallPoints.TOKENER = token_id

            findNavController().navigate(
                R.id.action_successFragment_to_dashboardFragment,
                bundle,
                options
            )
        }


    }
}