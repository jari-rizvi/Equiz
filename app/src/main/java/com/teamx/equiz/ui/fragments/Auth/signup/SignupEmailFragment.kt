package com.teamx.equiz.ui.fragments.Auth.signup


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentLoginEmailBinding
import com.teamx.equiz.databinding.FragmentSignupEmailBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupEmailFragment : BaseFragment<FragmentSignupEmailBinding, SignupViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_signup_email
    override val viewModel: Class<SignupViewModel>
        get() = SignupViewModel::class.java
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
        mViewDataBinding.btnLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        mViewDataBinding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signupPhoneFragment_to_otpPhoneFragment)
        }


    }
}