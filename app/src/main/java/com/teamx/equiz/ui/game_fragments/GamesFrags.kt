package com.teamx.equiz.ui.game_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.games.games.BirdWatchingGame
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
class HexaChainGameFrag : BaseFragment<FragmentAddressBinding, GameFragsViewModel>() {

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

@AndroidEntryPoint
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
}

@AndroidEntryPoint
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
}

@AndroidEntryPoint
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