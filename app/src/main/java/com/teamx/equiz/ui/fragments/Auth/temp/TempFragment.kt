package com.teamx.equiz.ui.fragments.Auth.temp


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.databinding.FragmentTempBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TempFragment : BaseFragment<FragmentTempBinding, TempViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_temp
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





        CoroutineScope(Dispatchers.Main).launch {
            dataStoreProvider.token.collect {
                Timber.tag("TAG").d("CoroutineScope ${it}")



//token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTJlNWEyYTE2YTU5OGNjYzRhNmIwZGUiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDI5ODk4NjQsImV4cCI6MTcwMzA3NjI2NH0.vgKdhrZQqSc_x-0kB_EHgRmVi4zoI6LzDMbCzGLTtXQ"
                TOKENER = it.toString()

                if (it.isNullOrBlank()&&false) {


                } else {

 

                    if(isAdded){

                    navController = Navigation.findNavController(
                        requireActivity(), R.id.nav_host_fragment
                    )
                    navController.navigate(
                        R.id.dashboardFragment, null, options
                    )
                    }

//                            findNavController().navigate(R.id.action_tempFragment_to_dashboardFragment)

                }
            }
        }


        mViewDataBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_tempFragment_to_temp2Fragment,arguments,options)

        }

        mViewDataBinding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_tempFragment_to_temp2RegisterFragment,arguments,options)

        }


    }

}
