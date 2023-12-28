package com.teamx.equiz.games.games

import android.os.CountDownTimer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Preview(showBackground = true)
@Composable
fun TetrisGamePreview() {
    TetrisGame()
}

@Composable
fun TetrisGame(content:  (bool:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit = {bool,rightAnswer,total ->}){
//    val context = LocalContext.current
    /*    StatusBarUtil.transparentStatusBar(context as Activity)
        SoundUtil.init(context)*/


    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1
    var isTimeUp by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(120L) }

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                  if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
                if (timeLeft<15){
                    isAlert = true
                }
            }

            override fun onFinish() {
                isTimeUp = true
            }
        }.start()
    }


    if (isGameOver) {


        content(true, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))

    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
            }
        }


    }else{
        MaterialTheme {


            Box(
                modifier = Modifier.fillMaxSize()

                    .background(color = Color(0xFFE1E1E1)),
            ) {
                // A surface container using the 'background' color from the theme
                Surface( modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {

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

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(id = R.drawable.iconbg),
                    contentDescription = "bg"
                )
                if (isAlert) {
                    GameAlertingTime()
                }
            }
        }
    }





}