package com.teamx.equiz.ui.fragments.Auth.temp


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentTempBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
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

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

            if (isAdded) {
                CoroutineScope(Dispatchers.Main).launch {
                    dataStoreProvider.token.collect {
                        Timber.tag("TAG").d("CoroutineScope ${it}")

                        val token = it

                        TOKENER = token.toString()

                        if (token.isNullOrBlank()) {


                        } else {
                            findNavController().navigate(R.id.action_tempFragment_to_dashboardFragment)

                        }
                    }
                }
            }



        mViewDataBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_tempFragment_to_temp2Fragment)

        }

        mViewDataBinding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_tempFragment_to_temp2RegisterFragment)

        }


    }

}
