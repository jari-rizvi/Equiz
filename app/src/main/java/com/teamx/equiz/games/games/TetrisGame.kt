package com.teamx.equiz.games.games

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teamx.equiz.R
import com.teamx.equiz.games.games.tetris.GameBody
import com.teamx.equiz.games.games.tetris.GameScreen
import com.teamx.equiz.games.games.tetris.combinedClickable
import com.teamx.equiz.games.games.tetris.logic.Action
import com.teamx.equiz.games.games.tetris.logic.Direction
import com.teamx.equiz.games.games.tetris.logic.GameViewModel
import com.teamx.equiz.games.games.tetris.logic.SoundUtil
import com.teamx.equiz.games.games.tetris.logic.StatusBarUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Preview(showBackground = true)
@Composable
fun TetrisGamePreview() {
    TetrisGame()
}

@Composable
fun TetrisGame(content: @Composable () -> Unit = {}){
    val context = LocalContext.current
    StatusBarUtil.transparentStatusBar(context as Activity)
    SoundUtil.init(context)

        MaterialTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colorScheme.background) {

                val viewModel = viewModel<GameViewModel>()
                val viewState = viewModel.viewState.value

                LaunchedEffect(key1 = Unit) {
                    while (isActive) {
                        delay(650L - 55 * (viewState.level - 1))
                        viewModel.dispatch(Action.GameTick)
                    }
                }

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = Unit) {
                    val observer = object : DefaultLifecycleObserver {
                        override fun onResume(owner: LifecycleOwner) {
                            viewModel.dispatch(Action.Resume)
                        }

                        override fun onPause(owner: LifecycleOwner) {
                            viewModel.dispatch(Action.Pause)
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

//
                Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
//
                GameBody(combinedClickable(
                    onMove = { direction: Direction ->
                        if (direction == Direction.Up) viewModel.dispatch(Action.Drop)
                        else viewModel.dispatch(Action.Move(direction))
                    },
                    onRotate = {
                        viewModel.dispatch(Action.Rotate)
                    },
                    onRestart = {
                        viewModel.dispatch(Action.Reset)
                    },
                    onPause = {
                        if (viewModel.viewState.value.isRuning) {
                            viewModel.dispatch(Action.Pause)
                        } else {
                            viewModel.dispatch(Action.Resume)
                        }
                    },
                    onMute = {
                        viewModel.dispatch(Action.Mute)
                    }
                )) {
                    GameScreen(
                        Modifier.fillMaxSize()
                    )
                }
//
                  Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.iconbg),
                contentDescription = "bg"
            )
        }//
            }
        }

}