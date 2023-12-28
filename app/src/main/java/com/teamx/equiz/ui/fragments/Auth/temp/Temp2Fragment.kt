package com.teamx.equiz.ui.fragments.Auth.temp


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentTemp2Binding
import com.teamx.equiz.databinding.FragmentTempBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.activity.addCallback
@AndroidEntryPoint
class Temp2Fragment : BaseFragment<FragmentTemp2Binding, TempViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_temp2
    override val viewModel: Class<TempViewModel>
        get() = TempViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        mViewDataBinding.btnEmail.setOnClickListener {
            findNavController().navigate(R.id.action_temp2Fragment_to_logInEmailFragment,arguments,options)


        }

        mViewDataBinding.btnPhone.setOnClickListener {
            findNavController().navigate(R.id.action_temp2Fragment_to_logInFragment,arguments,options)

        }


    }

}
