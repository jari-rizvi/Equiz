package com.teamx.equiz.ui.fragments.Auth.temp


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.airbnb.lottie.LottieAnimationView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.databinding.FragmentTemp1Binding
import com.teamx.equiz.databinding.FragmentTempBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream

@AndroidEntryPoint
class Temp1Fragment : BaseFragment<FragmentTemp1Binding, TempViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_temp1
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

        /*       val animationView =mViewDataBinding.animationView
               animationView.playAnimation()*/





        CoroutineScope(Dispatchers.Main).launch {
            dataStoreProvider.token.collect {
                Timber.tag("TAG").d("CoroutineScope ${it}")
                TOKENER = it.toString()

                if (it.isNullOrBlank() && false) {
                    delay(2500)
                    findNavController().navigate(R.id.tempFragment, arguments, options)
                } else {
                    delay(2500)
                    if (isAdded) {

                        navController = Navigation.findNavController(
                            requireActivity(), R.id.nav_host_fragment
                        )
                        navController.navigate(
                            R.id.dashboardFragment, null, options
                        )
                    }

                }
            }
        }


    }

}
