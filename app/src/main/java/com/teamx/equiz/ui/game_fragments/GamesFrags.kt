package com.teamx.equiz.ui.game_fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.games.games.AdditionAddictionGame
import com.teamx.equiz.games.games.BirdWatchingGame
import com.teamx.equiz.games.games.BreakTheBlockGame
import com.teamx.equiz.games.games.CardCalculationGameScreen
import com.teamx.equiz.games.games.ColorSwitchGameScreen
import com.teamx.equiz.games.games.ConcentrationGame
import com.teamx.equiz.games.games.FlickGameScreen
import com.teamx.equiz.games.games.FollowTheLeaderGame
import com.teamx.equiz.games.games.GuessTheFlagGame
import com.teamx.equiz.games.games.HighLowComponent
import com.teamx.equiz.games.games.HighLowGame
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
import com.teamx.equiz.games.games.ReverseRPS
import com.teamx.equiz.games.games.SimplicityGame
import com.teamx.equiz.games.games.SpinningBlockGame
import com.teamx.equiz.games.games.TapTheColorGame
import com.teamx.equiz.games.games.TetrisGame
import com.teamx.equiz.games.games.TouchTheNumGame
import com.teamx.equiz.games.games.TouchTheNumPlusGame
import com.teamx.equiz.games.games.TouchTheNumbersGameScreen
import com.teamx.equiz.games.games.UnfollowTheLeaderGame
import com.teamx.equiz.games.games.WeatherCastGame
import com.teamx.equiz.games.games.rpsCastGamePlot
import com.teamx.equiz.games.games.ui_components.ToolbarCompose
import dagger.hilt.android.AndroidEntryPoint

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
        mViewDataBinding = FragmentAddressBinding.inflate(inflater)
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {

            AdditionAddictionGame(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
            BirdWatchingGame(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
            ColorSwitchGameScreen(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            MatchingStepGame(Modifier,content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
            MissingPieceGameScreen(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        composeView.setContent {
            OperationGame(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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

        composeView.setContent {
            ResultScreen(){

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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
            TouchTheNumbersGameScreen(content = {
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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
                ToolbarCompose(title = "Training", onClick = {
                    navController.popBackStack()
                })
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