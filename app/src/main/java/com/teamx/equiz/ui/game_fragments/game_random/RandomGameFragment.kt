package com.teamx.equiz.ui.game_fragments.game_random

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentRandomGamesBinding
import com.teamx.equiz.games.games.learningy.LockScreenOrientation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RandomGameFragment : BaseFragment<FragmentRandomGamesBinding, RandomGameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_random_games
    override val viewModel: Class<RandomGameFragsViewModel>
        get() = RandomGameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
//    private lateinit var composeView: ComposeView

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        mViewDataBinding = FragmentRandomGamesBinding.inflate(inflater)
//        return ComposeView(requireContext()).also {
//            composeView = it
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle.getString("gameName")


        Log.d("123123", "onViewCreated:$gameName ")

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

//        MyComposableComponent()

        if (sharedViewModel.roundInteger == 1){
            mViewDataBinding.mainLayout.visibility = View.VISIBLE
        }else{
            mViewDataBinding.mainLayout.visibility = View.GONE
            MyComposableComponent()
        }

        mViewDataBinding.btnProceed.setOnClickListener {
            MyComposableComponent()
        }




    }


    fun MyComposableComponent() {

        mViewDataBinding.mainLayout.visibility = View.GONE
        val roundName = sharedViewModel.roundInteger.toString()

        Log.d("roundInteger", "onViewCreatedds: $roundName")
        mViewDataBinding.composeView.setContent {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            RandomViewCompose(roundName = roundName) { name ->
                Log.d("123123", "onClickGame: ")
                var strname = name
                Log.d("123123", "onClickGame:$strname ")
                var bundle = arguments
                if (bundle == null) {
                    bundle = Bundle()
                }
                bundle?.putString("gameName", strname)
                bundle?.putString("route", "gameRand")


                findNavController().navigate(R.id.startUpGameFrag, bundle, options)

            }

        }
    }
}