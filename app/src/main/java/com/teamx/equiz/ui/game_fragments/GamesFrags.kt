package com.teamx.equiz.ui.game_fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.games.games.AdditionAddictionGameMethod
import com.teamx.equiz.games.games.BirdWatchingGame
import com.teamx.equiz.games.games.CardCalculationGameScreen
import com.teamx.equiz.games.games.ConcentrationGame
import com.teamx.equiz.games.games.GuessTheFlagGame
import com.teamx.equiz.games.games.HighLowComponent
import com.teamx.equiz.games.games.ImplicityGameScreen
import com.teamx.equiz.games.games.MissingPieceGameScreen
import com.teamx.equiz.games.games.OperationGame
import com.teamx.equiz.games.games.QuickEyeGame
import com.teamx.equiz.games.games.RainFallGame
import com.teamx.equiz.games.games.RapidSortingGame
import com.teamx.equiz.games.games.ReflectionGame
import com.teamx.equiz.games.games.ResultScreen
import com.teamx.equiz.games.games.SpinningBlockGame
import com.teamx.equiz.games.games.TapTheColorGame
import com.teamx.equiz.games.games.TetrisGame
import com.teamx.equiz.games.games.TouchTheColorGameScreen
import com.teamx.equiz.games.games.TouchTheNumGamePlus
import com.teamx.equiz.games.games.TouchTheShapesGameScreen
import com.teamx.equiz.games.games.WeatherCastGame
import com.teamx.equiz.games.games.learningy.FlicksSc
import com.teamx.equiz.games.games.learningy.NumPlus
import com.teamx.equiz.games.games.learningy.ViewMatching
import com.teamx.equiz.games.games.learningy.follows.UnfollowTouchTheNumGamePlus
import com.teamx.equiz.games.games.learningy.makingran10.Rain10Game
import com.teamx.equiz.games.games.learningy.unfolw.followTouchTheNumGamePlus
import com.teamx.equiz.games.games.rpsCastGamePlot
import com.teamx.equiz.games.games.ui_components.StartUpDialogCompose
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.ui.fragments.dashboard.GamesUID2
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class AdditionAddictionGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = com.teamx.equiz.databinding.FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {

            AdditionAddictionGameMethod(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)

                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()
                }
//                })
            })

        }


    }
}

@AndroidEntryPoint
class BirdWatchingGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(composeView, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }
        composeView.setContent {
            Box {
//                ToolbarCompose(title = "Training", onClick = {
//                findNavController().popBackStack()
//                })
                BirdWatchingGame() { bool, rightAnswer, total ->
                    if (bool) {
                        var argumentBundle = arguments
                        if (argumentBundle == null) {
                            argumentBundle = Bundle()
                        }
                        argumentBundle.putInt("rightAnswer", rightAnswer)
                        argumentBundle.putInt("total", total)
                        findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                    } else {
                        findNavController().popBackStack()

                    }


                }
            }


        }


    }
}

@AndroidEntryPoint
class CardCalculationGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            CardCalculationGameScreen(content = { bool, rightAnswer, total ->

                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }

            })
        }


    }
}

@AndroidEntryPoint
class ColorOfDecepGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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



        composeView.setContent {
            TouchTheColorGameScreen { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
            }
        }


    }
}

@AndroidEntryPoint
class ConcentrationGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            ConcentrationGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }


            })
        }


    }
}

@AndroidEntryPoint
class FlickGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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

        composeView.setContent {
            FlicksSc(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }




            })
        }


    }
}

@AndroidEntryPoint
class FollowTheLeaderGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            followTouchTheNumGamePlus(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class GuessTheFlagGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            GuessTheFlagGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }

            })
        }


    }
}

@AndroidEntryPoint
class HighLowGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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

        composeView.setContent {
            HighLowComponent(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class Make10GameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            Rain10Game(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class MatchingGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        composeView.setContent {
            ViewMatching( content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class MissingPieceGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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

        composeView.setContent {
            MissingPieceGameScreen(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class OperationsGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
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
        composeView.setContent {
            OperationGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}


@AndroidEntryPoint
class QuickEyeGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            QuickEyeGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class RainFallGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            RainFallGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }



            })
        }


    }
}

@AndroidEntryPoint
class RapidSortingGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            RapidSortingGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class ReflectionGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            ReflectionGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                findNavController().popBackStack()
//                })
            })
        }


    }
}

@AndroidEntryPoint
class ResultComposeFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            val route = bundle?.getString("route")
            if (route.equals("dash", true)) {
                findNavController().navigate(R.id.dashboardFragment, arguments, options)
            } else {
                findNavController().navigate(R.id.gamesFragment, arguments, options)
            }
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle!!.getString("gameName")

        val rightAnswer = bundle!!.getInt("rightAnswer", 0)
        val total = bundle!!.getInt("total", 0)

        Log.d("123123", "onViewCreated: $gameName ")
        Log.d("123123", "onViewCreated: $rightAnswer ")
        Log.d("123123", "onViewCreated: $total ")

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


        composeView.setContent {
            ResultScreen(
                total,
                rightAnswer,
                20,
                returnGameName(gameName.toString()),
                returnGameIcon(gameName.toString())
            ) { i ->
                when (i) {
                    1 -> {
                        findNavController().navigate(R.id.startUpGameFrag, arguments, options)
                    }

                    2 -> {
                        var bundle = arguments
                        if (bundle == null) {
                            bundle = Bundle()
                        }
                        val route = bundle?.getString("route")
                        if (route.equals("dash", true)) {
                            findNavController().navigate(R.id.dashboardFragment, arguments, options)
                        } else {
                            findNavController().navigate(R.id.gamesFragment, arguments, options)
                        }
                    }

                    3 -> {
                        /* DialogHelperClass.shareGameResultDialog(
                             requireContext(),
                             object : DialogHelperClass.Companion.DialogInviteAnotherCallBack {
                                 override fun InviteClicked() {

                                 }

                             },
                             true,
                             "priceAddTopUp".toString(), total,
                             rightAnswer,
                             20,
                             gameName.toString()
                         ) {


                         }*/
                    }

                    else -> {
                        findNavController().navigate(R.id.subscriptionFragment, arguments, options)
                    }
                }

            }
        }

        resultGame(total, rightAnswer)
    }

    private fun resultGame(total: Int, right: Int, time: Int = 20) {


        val params = JsonObject()
        try {
            params.addProperty("correct", right)
            params.addProperty("total", total)
            params.addProperty("time", time)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.resultGame(params)

        mViewModel.resultResponseGameOB.observe(requireActivity(), Observer {
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

                        val score = data.game.score

                        MainActivity.service?.showNotification1(
                            "Score",
                            "$score"
                        )
//                         findNavController().navigate(R.id.resultComposeFrag)
                    }
                }

                Resource.Status.AUTH -> {
                    loadingDialog.dismiss()
                     if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(requireContext(), it.message!!)
                }
            }
        })
    }


    @Composable
    private fun returnGameIcon(enumNumberEnum: String): Painter {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {

                painterResource(R.drawable.addition_icon)


            }

            GamesUID2.BirdWatching.name -> {
                painterResource(R.drawable.bird_favicon)

            }
            GamesUID2.Operations.name -> {
                painterResource(R.drawable.operations_icon)

            }


            GamesUID2.ColorDeception.name -> {
                painterResource(R.drawable.colorofdeception_icon)

            }

            GamesUID2.Tetris.name -> {
                painterResource(R.drawable.tetris_icon)

            }

            GamesUID2.Concentration.name -> {

                painterResource(R.drawable.concentration_icon)
            }

            GamesUID2.CardCalculation.name -> {
                painterResource(R.drawable.cardscalculations_icon)

            }

            GamesUID2.Flick.name -> {
                painterResource(R.drawable.flick_icon)

            }

            GamesUID2.FollowTheLeader.name -> {
                painterResource(R.drawable.follow_the_leder)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                painterResource(R.drawable.follow_the_leder)

            }

            GamesUID2.GuessTheFlag.name -> {
                painterResource(R.drawable.guestheflag_icon)
            }

            GamesUID2.HighLow.name -> {
                painterResource(R.drawable.highorlow_icon)

            }

            GamesUID2.MakeTen.name -> {
                painterResource(R.drawable.maketen_icon)

            }

            GamesUID2.MissingPiece.name -> {
                painterResource(R.drawable.missingpieces_icon)

            }


            GamesUID2.QuickEye.name -> {
                painterResource(R.drawable.quickeye_icon)

            }

            GamesUID2.RainFall.name -> {
                painterResource(R.drawable.rainfall_icon)

            }

            GamesUID2.RapidSorting.name -> {
                painterResource(R.drawable.rapid_sorting_icon)

            }

            GamesUID2.ReverseRps.name -> {
                painterResource(R.drawable.reverserps_icon)

            }

            GamesUID2.Simplicity.name -> {
                painterResource(R.drawable.simplicity_icon)

            }

            GamesUID2.SpinningBlock.name -> {
                painterResource(R.drawable.spinthewheel_icon)

            }

            GamesUID2.ShapeDeception.name -> {
                painterResource(R.drawable.shapedeception_icon)
            }

            GamesUID2.TapTheColor.name -> {
                painterResource(R.drawable.tapthecolor_icon)

            }

            GamesUID2.TouchTheNum.name -> {
                painterResource(R.drawable.touchthenumber_icon)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                painterResource(R.drawable.touchthenumbers_icon)

            }

            GamesUID2.WeatherCast.name -> {
                painterResource(R.drawable.weathercast_icon)

            }


            else -> {
                painterResource(R.drawable.weathercast_icon)
            }
        }


    }

    private fun returnGameName(enumNumberEnum: String): String {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {
                "Addition Addiction"

            }

            GamesUID2.BirdWatching.name -> {
                "Bird Watching"

            }

            GamesUID2.Matching.name -> {
                "Matching"

            }

            GamesUID2.Operations.name -> {

                "Operations"
            }

            GamesUID2.ColorDeception.name -> {
                "ColorDeception"

            }

            GamesUID2.Tetris.name -> {
                "Tetris"

            }


            GamesUID2.CardCalculation.name -> {
                "Card Calculation"

            }

            GamesUID2.Concentration.name -> {
                "Concentration"

            }

            GamesUID2.Flick.name -> {
                "Flick"

            }

            GamesUID2.FollowTheLeader.name -> {
                "Follow The Leader"

            }

            GamesUID2.UnfollowTheLeader.name -> {
                "Unfollow The Leader"

            }

            GamesUID2.GuessTheFlag.name -> {
                "Guess The Flag"
            }

            GamesUID2.HighLow.name -> {
                "High Low"

            }

            GamesUID2.MakeTen.name -> {
                "Make Ten"

            }

            GamesUID2.MissingPiece.name -> {
                "Missing Piece"

            }


            GamesUID2.QuickEye.name -> {
                "Quick Eye"

            }

            GamesUID2.RainFall.name -> {
                "Rain Fall"

            }

            GamesUID2.RapidSorting.name -> {
                "Rapid Sorting"

            }

            GamesUID2.ReverseRps.name -> {
                "Reverse RPS"

            }

            GamesUID2.Simplicity.name -> {
                "Simplicity"

            }

            GamesUID2.SpinningBlock.name -> {
                "Spinning Block"

            }

            GamesUID2.ShapeDeception.name -> {
                "Shape Deception"
            }

            GamesUID2.TapTheColor.name -> {
                "Tap The Color"

            }

            GamesUID2.TouchTheNum.name -> {
                "Touch The Number"

            }

            GamesUID2.TouchTheNumPlus.name -> {
                "Touch The Number Plus"

            }

            GamesUID2.WeatherCast.name -> {
                "Weather Cast"

            }


            else -> {
                "Weather Cast"
            }
        }


    }
}

@AndroidEntryPoint
class ReverseRPSFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            rpsCastGamePlot(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class SimplicityGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            ImplicityGameScreen(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class SpinningBlockGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            SpinningBlockGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

/*@AndroidEntryPoint
class SpinningLotteryGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            SpinningBlockGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}*/

@AndroidEntryPoint
class TapTheColorGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
        composeView.setContent {
            TapTheColorGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class TetrisGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            TetrisGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class TouchTheNumGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
        composeView.setContent {
            TouchTheNumGamePlus(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class TouchTheNumPlusGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            NumPlus(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class UnfollowTheLeaderGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        composeView.setContent {
            UnfollowTouchTheNumGamePlus(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }
//                ToolbarCompose(title = "Training", onClick = {

//                })
            })
        }


    }
}

@AndroidEntryPoint
class WeatherCastGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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

        composeView.setContent {
            WeatherCastGame(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }

            })
        }


    }
}

@AndroidEntryPoint
class ShapeDeceptionGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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

        composeView.setContent {
            TouchTheShapesGameScreen(content = { bool, rightAnswer, total ->
                if (bool) {
                    var argumentBundle = arguments
                    if (argumentBundle == null) {
                        argumentBundle = Bundle()
                    }
                    argumentBundle.putInt("rightAnswer", rightAnswer)
                    argumentBundle.putInt("total", total)
                    findNavController().navigate(R.id.resultComposeFrag, arguments, options)
                } else {
                    findNavController().popBackStack()

                }

            })
        }


    }
}

@AndroidEntryPoint
class StartUpGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<GameFragsViewModel>
        get() = GameFragsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

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
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle?.getString("gameName")


        Log.d("123123", "onViewCreated:$gameName ")

        composeView.setContent {
            StartUpDialogCompose(
                "${returnGameName(gameName.toString())}",
                "${returnGameInstructions(gameName.toString())}",
                onClick = {
                    when (it) {
                        1 -> {
                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            val route = bundle?.getString("route")
                            if (route.equals("dash", true)) {
                                findNavController().navigate(
                                    R.id.dashboardFragment,
                                    arguments,
                                    options
                                )
                            } else {
                                findNavController().navigate(R.id.gamesFragment, arguments, options)
                            }
                        }

                        2 -> {
                            onClickGame(gameName.toString())
                        }
                    }

                },
                returnGameIconInstruction(gameName.toString())
            )
            /*    StartUpDialogCompose(
                    "${returnGameName(GamesUID2.ShapeDeception.name)}",
                    "${returnGameInstructions(GamesUID2.ShapeDeception.name)}",
                    onClick = {

                        onClickGame(GamesUID2.ShapeDeception.name)
                    },
                    returnGameIcon(GamesUID2.ShapeDeception.name)
                )*/
        }


    }


    @Composable
    private fun returnGameIcon(enumNumberEnum: String): Painter {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {

                painterResource(R.drawable.addition_icon)


            }

            GamesUID2.BirdWatching.name -> {
                painterResource(R.drawable.bird_favicon)

            }


            GamesUID2.ColorDeception.name -> {
                painterResource(R.drawable.colorofdeception_icon)

            }

            GamesUID2.Tetris.name -> {
                painterResource(R.drawable.tetris_icon)

            }

            GamesUID2.Concentration.name -> {

                painterResource(R.drawable.concentration_icon)
            }

            GamesUID2.CardCalculation.name -> {
                painterResource(R.drawable.cardscalculations_icon)

            }

            GamesUID2.Flick.name -> {
                painterResource(R.drawable.flick_icon)

            }

            GamesUID2.FollowTheLeader.name -> {
                painterResource(R.drawable.follow_the_leder)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                painterResource(R.drawable.follow_the_leder)

            }

            GamesUID2.GuessTheFlag.name -> {
                painterResource(R.drawable.guestheflag_icon)
            }

            GamesUID2.HighLow.name -> {
                painterResource(R.drawable.highorlow_icon)

            }

            GamesUID2.MakeTen.name -> {
                painterResource(R.drawable.maketen_icon)

            }

            GamesUID2.MissingPiece.name -> {
                painterResource(R.drawable.missingpieces_icon)

            }


            GamesUID2.QuickEye.name -> {
                painterResource(R.drawable.quickeye_icon)

            }

            GamesUID2.RainFall.name -> {
                painterResource(R.drawable.rainfall_icon)

            }

            GamesUID2.RapidSorting.name -> {
                painterResource(R.drawable.rapid_sorting_icon)

            }

            GamesUID2.ReverseRps.name -> {
                painterResource(R.drawable.reverserps_icon)

            }

            GamesUID2.Simplicity.name -> {
                painterResource(R.drawable.simplicity_icon)

            }

            GamesUID2.SpinningBlock.name -> {
                painterResource(R.drawable.spinthewheel_icon)

            }

            GamesUID2.ShapeDeception.name -> {
                painterResource(R.drawable.shapedeception_icon)
            }

            GamesUID2.TapTheColor.name -> {
                painterResource(R.drawable.tapthecolor_icon)

            }

            GamesUID2.TouchTheNum.name -> {
                painterResource(R.drawable.touchthenumber_icon)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                painterResource(R.drawable.touchthenumbers_icon)

            }

            GamesUID2.WeatherCast.name -> {
                painterResource(R.drawable.weathercast_icon)

            }


            else -> {
                painterResource(R.drawable.weathercast_icon)
            }
        }


    }

    @Composable
    private fun returnGameIconInstruction(enumNumberEnum: String): Painter {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {

                painterResource(R.drawable.howtoplay_addition)


            }

            GamesUID2.BirdWatching.name -> {
                painterResource(R.drawable.howtoplay_bird)

            }

            GamesUID2.Operations.name -> {

                painterResource(R.drawable.howtoplay_operation_icon)
            }


            GamesUID2.ColorDeception.name -> {
                painterResource(R.drawable.howtoplay_colo)

            }

            GamesUID2.Tetris.name -> {
                painterResource(R.drawable.howtoplay_tetris)

            }

            GamesUID2.Concentration.name -> {

                painterResource(R.drawable.howtoplay_concentration)
            }

            GamesUID2.CardCalculation.name -> {
                painterResource(R.drawable.howtoplay_card)

            }

            GamesUID2.Flick.name -> {
                painterResource(R.drawable.howtoplay_flick)

            }

            GamesUID2.FollowTheLeader.name -> {
                painterResource(R.drawable.howtoplay_follow)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                painterResource(R.drawable.howtoplay_unfollow)

            }

            GamesUID2.GuessTheFlag.name -> {
                painterResource(R.drawable.howtoplay_flag)
            }

            GamesUID2.HighLow.name -> {
                painterResource(R.drawable.howtoplay_highorlow)

            }

            GamesUID2.MakeTen.name -> {
                painterResource(R.drawable.howtoplay_make)

            }

            GamesUID2.MissingPiece.name -> {
                painterResource(R.drawable.howtoplay_missing)

            }

            GamesUID2.Matching.name -> {
                painterResource(R.drawable.howtoplay_matching)
            }


            GamesUID2.QuickEye.name -> {
                painterResource(R.drawable.howtoplay_quick)

            }

            GamesUID2.RainFall.name -> {
                painterResource(R.drawable.howtoplay_rainone)

            }

            GamesUID2.RapidSorting.name -> {
                painterResource(R.drawable.howtoplay_rapidone)

            }

            GamesUID2.ReverseRps.name -> {
                painterResource(R.drawable.howtoplayrps)

            }

            GamesUID2.Simplicity.name -> {
                painterResource(R.drawable.howtoplay_simplicity)

            }

            GamesUID2.SpinningBlock.name -> {
                painterResource(R.drawable.spiningblock_howtoplay)

            }

            GamesUID2.ShapeDeception.name -> {
                painterResource(R.drawable.howtoplay_shape)
            }

            GamesUID2.TapTheColor.name -> {
                painterResource(R.drawable.howtoplay_tapthecolor)

            }

            GamesUID2.TouchTheNum.name -> {
                painterResource(R.drawable.howtoplay_touchthenumber)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                painterResource(R.drawable.howtoplay_touch)

            }

            GamesUID2.WeatherCast.name -> {
                painterResource(R.drawable.howtoplay_weather)

            }


            else -> {
                painterResource(R.drawable.howtoplay_weather)
            }
        }


    }


    private fun returnGameInstructions(enumNumberEnum: String): String {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {

                "Sum up panels equal to the" +
                        "indicated number"


            }

            GamesUID2.BirdWatching.name -> {
                "Tap the color that appears the most"

            }


            GamesUID2.ColorDeception.name -> {
                "Choose the color that doesn’t belong"

            }

            GamesUID2.Tetris.name -> {
                "painterResource(R.drawable.tetris_icon)"

            }

            GamesUID2.Concentration.name -> {

                "Pair up the identical symbols"
            }

            GamesUID2.Operations.name -> {

                "Choose the correct arithmetic symbol"
            }

            GamesUID2.CardCalculation.name -> {
                "1- Add up if purple, subtract if red" +
                        "\n2- Select the answer"

            }

            GamesUID2.Flick.name -> {
                "Blue: Follow the arrow Pink: Unfollow the arrow"

            }

            GamesUID2.FollowTheLeader.name -> {
                "Tap the blocks in the order they appeared"

            }

            GamesUID2.UnfollowTheLeader.name -> {
                "Tap the blocks in the reverse order" +
                        "of how they appeared"

            }

            GamesUID2.GuessTheFlag.name -> {
                "Choose the correct flag"
            }

            GamesUID2.HighLow.name -> {
                "Greater than the previous: Swipe up Less than the previous: Swipe down"

            }

            GamesUID2.MakeTen.name -> {
                "Tap the blocks to make a 10."

            }

            GamesUID2.Matching.name -> {
                "Find the matching pair"

            }

            GamesUID2.MissingPiece.name -> {
                "Find the missing pieces"

            }


            GamesUID2.QuickEye.name -> {
                "Find an identical card to the one displayed"

            }

            GamesUID2.RainFall.name -> {
                "Move the umbrella with a tap catch the drops, avoid the lighting"

            }

            GamesUID2.RapidSorting.name -> {
                "Same Shape: Swipe the same direction Different Shape: Opposite direction"

            }

            GamesUID2.ReverseRps.name -> {
                "Purple : Win against the symbol" +
                        "Pink : Lose against the symbol"

            }

            GamesUID2.Simplicity.name -> {
                "Select the correct answer"

            }

            GamesUID2.SpinningBlock.name -> {
                "1- Memorize the blue block locations" +
                        "\n2- Tap after the blocks spin"

            }

            GamesUID2.ShapeDeception.name -> {
                "Choose The Shape That  Goes With The Name"
            }

            GamesUID2.TapTheColor.name -> {
                "1- Memorize the location of each color  \n2- Tap in the displayed order"

            }

            GamesUID2.TouchTheNum.name -> {
                "Purple: Tap in ascending order Pink: Tap in descending order"

            }

            GamesUID2.TouchTheNumPlus.name -> {
                "1- Tap in an ascending order\n" +
                        "\n2- Select the sum of the numerals"

            }

            GamesUID2.WeatherCast.name -> {
                "Tap the same shape and color card. If none, a completely different one."

            }


            else -> {
                ""
            }
        }


    }

    private fun returnGameName(enumNumberEnum: String): String {


        return when (enumNumberEnum) {

            GamesUID2.AdditionAddiction.name -> {
                "Addition Addiction"

            }

            GamesUID2.BirdWatching.name -> {
                "Bird Watching"

            }

            GamesUID2.Matching.name -> {
                "Matching"

            }

            GamesUID2.Operations.name -> {

                "Operations"
            }

            GamesUID2.ColorDeception.name -> {
                "Color Deception"

            }

            GamesUID2.Tetris.name -> {
                "Tetris"

            }


            GamesUID2.CardCalculation.name -> {
                "Card Calculation"

            }

            GamesUID2.Concentration.name -> {
                "Concentration"

            }

            GamesUID2.Flick.name -> {
                "Flick"

            }

            GamesUID2.FollowTheLeader.name -> {
                "Follow The Leader"

            }

            GamesUID2.UnfollowTheLeader.name -> {
                "Unfollow The Leader"

            }

            GamesUID2.GuessTheFlag.name -> {
                "Guess The Flag"
            }

            GamesUID2.HighLow.name -> {
                "High Low"

            }

            GamesUID2.MakeTen.name -> {
                "Make Ten"

            }

            GamesUID2.MissingPiece.name -> {
                "Missing Piece"

            }


            GamesUID2.QuickEye.name -> {
                "Quick Eye"

            }

            GamesUID2.RainFall.name -> {
                "Rain Fall"

            }

            GamesUID2.RapidSorting.name -> {
                "Rapid Sorting"

            }

            GamesUID2.ReverseRps.name -> {
                "Reverse RPS"

            }

            GamesUID2.Simplicity.name -> {
                "Simplicity"

            }

            GamesUID2.SpinningBlock.name -> {
                "Spinning Block"

            }

            GamesUID2.ShapeDeception.name -> {
                "Shape Deception"
            }

            GamesUID2.TapTheColor.name -> {
                "Tap The Color"

            }

            GamesUID2.TouchTheNum.name -> {
                "Touch The Number"

            }

            GamesUID2.TouchTheNumPlus.name -> {
                "Touch The Number Plus"

            }

            GamesUID2.WeatherCast.name -> {
                "Weather Cast"

            }


            else -> {
                "Weather Cast"
            }
        }


    }

    private fun onClickGame(enumNumberEnum: String) {
        Log.d("123123", "onClickGame: ")

        when (enumNumberEnum) {
            GamesUID2.Matching.name -> {
                findNavController().navigate(R.id.matchingGameFrag, arguments, options)


            }


            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag, arguments, options)


            }

            GamesUID2.Operations.name -> {

                findNavController().navigate(R.id.operationsGameFrag, arguments, options)

            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag, arguments, options)

            }

            GamesUID2.ColorDeception.name -> {
                findNavController().navigate(R.id.colorOfDecepGameFrag, arguments, options)

            }

            GamesUID2.Tetris.name -> {
                findNavController().navigate(R.id.tetrisGameFrag, arguments, options)

            }

            GamesUID2.Concentration.name -> {

                findNavController().navigate(R.id.concentrationGameFrag, arguments, options)
            }

            GamesUID2.CardCalculation.name -> {
                findNavController().navigate(R.id.cardCalculationGameFrag, arguments, options)

            }

            GamesUID2.Flick.name -> {
                findNavController().navigate(R.id.flickGameFrag, arguments, options)

            }

            GamesUID2.FollowTheLeader.name -> {
                findNavController().navigate(R.id.followTheLeaderGameFrag, arguments, options)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                findNavController().navigate(R.id.unfollowTheLeaderGameFrag, arguments, options)

            }

            GamesUID2.GuessTheFlag.name -> {
                findNavController().navigate(R.id.guessTheFlagGameFrag, arguments, options)

            }

            GamesUID2.HighLow.name -> {
                findNavController().navigate(R.id.highLowGameFrag, arguments, options)

            }

            GamesUID2.MakeTen.name -> {
                findNavController().navigate(R.id.make10GameFrag, arguments, options)

            }

            GamesUID2.MissingPiece.name -> {
                findNavController().navigate(R.id.missingPieceGameFrag, arguments, options)

            }


            GamesUID2.QuickEye.name -> {
                findNavController().navigate(R.id.quickEyeGameFrag, arguments, options)

            }

            GamesUID2.RainFall.name -> {
                findNavController().navigate(R.id.rainFallGameFrag, arguments, options)

            }

            GamesUID2.RapidSorting.name -> {
                findNavController().navigate(R.id.rapidSortingGameFrag, arguments, options)

            }

            GamesUID2.ReverseRps.name -> {
                findNavController().navigate(R.id.reverseRPSFrag, arguments, options)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag, arguments, options)

            }

            GamesUID2.SpinningBlock.name -> {
                findNavController().navigate(R.id.spinningBlockGameFrag, arguments, options)

            }

            GamesUID2.ShapeDeception.name -> {
                findNavController().navigate(R.id.shapeDeceptionGameFrag, arguments, options)
            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag, arguments, options)

            }

            GamesUID2.TouchTheNum.name -> {
                findNavController().navigate(R.id.touchTheNumGameFrag, arguments, options)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag, arguments, options)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag, arguments, options)

            }


        }


    }


}