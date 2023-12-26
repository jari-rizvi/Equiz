package com.teamx.equiz.ui.game_fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.teamx.equiz.games.games.BreakTheBlockGame
import com.teamx.equiz.games.games.CardCalculationGameScreen
import com.teamx.equiz.games.games.ColorSwitchGameScreen
import com.teamx.equiz.games.games.ConcentrationGame
import com.teamx.equiz.games.games.FlickGameScreen
import com.teamx.equiz.games.games.FollowTheLeaderGame
import com.teamx.equiz.games.games.GuessTheFlagGame
import com.teamx.equiz.games.games.HighLowComponent
import com.teamx.equiz.games.games.ImplicityGameScreen
import com.teamx.equiz.games.games.Make10GameScreen
import com.teamx.equiz.games.games.MatchingStepGame
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
import com.teamx.equiz.games.games.TouchTheNumPlusGame
import com.teamx.equiz.games.games.TouchTheShapesGameScreen
import com.teamx.equiz.games.games.UnfollowTheLeaderGame
import com.teamx.equiz.games.games.WeatherCastGame
import com.teamx.equiz.games.games.rpsCastGamePlot
import com.teamx.equiz.games.games.ui_components.StartUpDialogCompose
import com.teamx.equiz.games.games.ui_components.ToolbarCompose
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle.getString("gameName")


        Log.d("123123", "onViewCreated:$gameName ")

        composeView.setContent {

            AdditionAddictionGameMethod(content = {
                if (it) {
                    findNavController().navigate(R.id.resultComposeFrag, arguments)
                } else {
                    navController.popBackStack()
                }
//                })
            })

        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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

        composeView.setContent {
            Box {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
                BirdWatchingGame() { bool ->
                    if (bool) {
                        findNavController().navigate(R.id.resultComposeFrag, arguments)
                    } else {
                        findNavController().popBackStack()

                    }


                }
            }


        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

@AndroidEntryPoint
class BreakTheBlockGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BreakTheBlockGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            CardCalculationGameScreen(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TouchTheColorGameScreen( {})
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

@AndroidEntryPoint
class ColorSwitchGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            ColorSwitchGameScreen(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            ConcentrationGame(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            FlickGameScreen(content = {

                    navController.popBackStack()

    
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            FollowTheLeaderGame(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            GuessTheFlagGame(content = {
//               /* ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })*/
                findNavController().navigate(R.id.resultComposeFrag, arguments)
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            HighLowComponent(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

/*
@AndroidEntryPoint
class LearningThingFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}
*/

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            Make10GameScreen(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            MatchingStepGame(Modifier,content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

/*@AndroidEntryPoint
class MenuScreenFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            MissingPieceGameScreen(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            OperationGame(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

/*@AndroidEntryPoint
class PathToSaftyGFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

/*@AndroidEntryPoint
class ProfileScreenFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            QuickEyeGame(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            RainFallGame(content = {

                    navController.popBackStack()

            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            RapidSortingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            ReflectionGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle.getString("gameName")


        Log.d("123123", "onViewCreated: $gameName ")

        composeView.setContent {
            ResultScreen(
                10,
                7,
                30,
                returnGameName(gameName.toString()),
                returnGameIcon(gameName.toString())
            ) {
                findNavController().popBackStack()
            }
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        resultGame(10, 7)
    }

     private fun resultGame(total: Int, right: Int, time: Int = 30) {

         var bundle = arguments
         if (bundle == null) {
             bundle = Bundle()
         }


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
                         showToast("Toast")
//                         findNavController().navigate(R.id.resultComposeFrag)
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
                 painterResource(R.drawable.colorofdeception_icon)
             }

             GamesUID2.TapTheColor.name -> {
                 painterResource(R.drawable.tapthecolor_icon)

             }

             GamesUID2.TouchTheNum.name -> {
                 painterResource(R.drawable.touchthenumber_icon)

             }

             GamesUID2.TouchTheNumPlus.name -> {
                 painterResource(R.drawable.touchthenumbers_icon_plus)

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
                 "Un Follow The Leader"

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
                 "Touch The Num"

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            rpsCastGamePlot(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            ImplicityGameScreen(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            SpinningBlockGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

@AndroidEntryPoint
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            SpinningBlockGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TapTheColorGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

/*@AndroidEntryPoint
class TenSecondFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

/*@AndroidEntryPoint
class testiFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

/*@AndroidEntryPoint
class TestingFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TetrisGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}

/*@AndroidEntryPoint
class ToolbarFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            BirdWatchingGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }


    }
}*/

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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TouchTheNumGamePlus(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TouchTheNumPlusGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            UnfollowTheLeaderGame(content = {
//                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            WeatherCastGame(content = {
//              /*  ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })*/
                findNavController().navigate(R.id.resultComposeFrag, arguments)
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            TouchTheShapesGameScreen(content = {
//              /*  ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
//                })*/
                findNavController().navigate(R.id.resultComposeFrag, arguments)
            })
        }
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        val gameName = bundle.getString("gameName")


        Log.d("123123", "onViewCreated:$gameName ")

        composeView.setContent {
            StartUpDialogCompose(
                "${returnGameName(gameName.toString())}",
                "${returnGameInstructions(gameName.toString())}",
                onClick = {

                    onClickGame(gameName.toString())
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
        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
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
                painterResource(R.drawable.colorofdeception_icon)
            }

            GamesUID2.TapTheColor.name -> {
                painterResource(R.drawable.tapthecolor_icon)

            }

            GamesUID2.TouchTheNum.name -> {
                painterResource(R.drawable.touchthenumber_icon)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                painterResource(R.drawable.touchthenumbers_icon_plus)

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
                painterResource(R.drawable.howtoplay_spin)

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
                painterResource(R.drawable.howtoplay_touchthenumber)

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
                        "2- Select the answer"

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
                        "2- Tap after the blocks spin"

            }

            GamesUID2.ShapeDeception.name -> {
                "Choose The Shape That  Goes With The Name"
            }

            GamesUID2.TapTheColor.name -> {
                "1- Memorize the location of each color  2- Tap in the displayed order"

            }

            GamesUID2.TouchTheNum.name -> {
                "Purple: Tap in ascending order Pink: Tap in descending order"

            }

            GamesUID2.TouchTheNumPlus.name -> {
                "1- Tap in an ascending order\n" +
                        "2- Select the sum of the numerals"

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
                "Un Follow The Leader"

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
                "Touch The Num"

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
                findNavController().navigate(R.id.matchingGameFrag, arguments)


            }

            GamesUID2.AdditionAddiction.name -> {

                findNavController().navigate(R.id.additionAddictionGameFrag, arguments)


            }

            GamesUID2.Operations.name -> {

                findNavController().navigate(R.id.operationsGameFrag, arguments)

            }

            GamesUID2.BirdWatching.name -> {
                findNavController().navigate(R.id.birdWatchingGameFrag, arguments)

            }

            GamesUID2.ColorDeception.name -> {
                findNavController().navigate(R.id.colorOfDecepGameFrag, arguments)

            }

            GamesUID2.Tetris.name -> {
                findNavController().navigate(R.id.tetrisGameFrag, arguments)

            }

            GamesUID2.Concentration.name -> {

                findNavController().navigate(R.id.concentrationGameFrag, arguments)
            }

            GamesUID2.CardCalculation.name -> {
                findNavController().navigate(R.id.cardCalculationGameFrag, arguments)

            }

            GamesUID2.Flick.name -> {
                findNavController().navigate(R.id.flickGameFrag, arguments)

            }

            GamesUID2.FollowTheLeader.name -> {
                findNavController().navigate(R.id.followTheLeaderGameFrag, arguments)

            }

            GamesUID2.UnfollowTheLeader.name -> {
                findNavController().navigate(R.id.unfollowTheLeaderGameFrag, arguments)

            }

            GamesUID2.GuessTheFlag.name -> {
//                findNavController().navigate(R.id,arguments)

            }

            GamesUID2.HighLow.name -> {
                findNavController().navigate(R.id.highLowGameFrag, arguments)

            }

            GamesUID2.MakeTen.name -> {
                findNavController().navigate(R.id.make10GameFrag, arguments)

            }

            GamesUID2.MissingPiece.name -> {
                findNavController().navigate(R.id.missingPieceGameFrag, arguments)

            }


            GamesUID2.QuickEye.name -> {
                findNavController().navigate(R.id.quickEyeGameFrag, arguments)

            }

            GamesUID2.RainFall.name -> {
                findNavController().navigate(R.id.rainFallGameFrag, arguments)

            }

            GamesUID2.RapidSorting.name -> {
                findNavController().navigate(R.id.rapidSortingGameFrag, arguments)

            }

            GamesUID2.ReverseRps.name -> {
                findNavController().navigate(R.id.reverseRPSFrag, arguments)

            }

            GamesUID2.Simplicity.name -> {
                findNavController().navigate(R.id.simplicityGameFrag, arguments)

            }

            GamesUID2.SpinningBlock.name -> {
                findNavController().navigate(R.id.spinningBlockGameFrag, arguments)

            }

            GamesUID2.ShapeDeception.name -> {
                findNavController().navigate(R.id.shapeDeceptionGameFrag, arguments)
            }

            GamesUID2.TapTheColor.name -> {
                findNavController().navigate(R.id.tapTheColorGameFrag, arguments)

            }

            GamesUID2.TouchTheNum.name -> {
                findNavController().navigate(R.id.touchTheNumGameFrag, arguments)

            }

            GamesUID2.TouchTheNumPlus.name -> {
                findNavController().navigate(R.id.touchTheNumPlusGameFrag, arguments)

            }

            GamesUID2.WeatherCast.name -> {
                findNavController().navigate(R.id.weatherCastGameFrag, arguments)

            }


        }


    }


}