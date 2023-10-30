package com.teamx.equiz.ui.fragments.Auth.login


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentLoginEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInEmailFragment : BaseFragment<FragmentLoginEmailBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_login_email
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
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
        mViewDataBinding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_logInEmailFragment_to_signupEmailFragment)
        }

        mViewDataBinding.btnForgot.setOnClickListener {
            findNavController().navigate(R.id.action_logInEmailFragment_to_forgotPassFragment2)
        }
    }
}