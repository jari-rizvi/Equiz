package com.teamx.equiz.ui.fragments.Auth.createNewPass


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentCreatePasswordBinding
import com.teamx.equiz.databinding.FragmentForgotPassBinding
import com.teamx.equiz.databinding.FragmentLoginEmailBinding
import com.teamx.equiz.databinding.FragmentPassChangeSuccessBinding
import com.teamx.equiz.ui.fragments.Auth.forgot.ForgotPassViewModel
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassChangeSuccessFragment :
    BaseFragment<FragmentPassChangeSuccessBinding, CreateNewPassViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_pass_change_success
    override val viewModel: Class<CreateNewPassViewModel>
        get() = CreateNewPassViewModel::class.java
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

        mViewDataBinding.btnVerify.setOnClickListener {
            findNavController().navigate(R.id.action_passChangeSuccessFragment_to_logInFragment,arguments,options)

        }


    }
}