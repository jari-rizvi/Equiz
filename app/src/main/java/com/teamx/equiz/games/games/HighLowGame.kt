package com.teamx.equiz.games.games

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.teamx.equiz.R
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.learningy.musiclearning.incorrectSound
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor4
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HighOrLowGameScreen(content: @Composable () -> Unit = {}, onContinueClicked: () -> Unit = {}) {
//    var score by remember { mutableStateOf(0) }
//    var currentNumber by remember { mutableStateOf(generateNumber()) }

    HighLowGame()
}


var i23 = 1
var dragged = true


var rightGameAnswersHigh = 0
var totalGameAnswersHigh = 0


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HighLowComponent(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var isTimeUp by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(20L) }
    val context = LocalContext.current

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
                if (timeLeft < 5) {
                    isAlert = true
                }
            }

            override fun onFinish() {
                isTimeUp = true
            }
        }.start()
    }
    if (isGameOver) {
        content(true, rightGameAnswersHigh, totalGameAnswersHigh)
        rightGameAnswersHigh = 0
        totalGameAnswersHigh = 0
    }
    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersHigh, totalGameAnswersHigh)
                rightGameAnswersHigh = 0
                totalGameAnswersHigh = 0
            }
        }


    }
    else {
        var swipeStateX by remember { mutableStateOf(false) }

        var previousNumber by remember { mutableStateOf(Random.nextInt(1, 100)) }
        var showNumber by remember { mutableStateOf(Random.nextInt(1, 100)) }
        var valuesTranslation by remember {
            mutableStateOf(
                590f
            )
        }
        var randomInt by remember { mutableStateOf(1) }
        randomInt = if (previousNumber > showNumber) {
            1
        } else {
            2
        }
        var fadeValue by remember { mutableStateOf(0f) }
        var bimap by remember { mutableStateOf(R.drawable.down) }

        val transitionState = remember { MutableTransitionState(false) }
        val transition = updateTransition(transitionState, "cardTransition")

        val context = LocalContext.current

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        var shakeOffset by remember { mutableStateOf(0f) }

        val infiniteTransition = rememberInfiniteTransition()

        val animatedShakeOffset = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )

//        shakeOffset = animatedShakeOffset.value

        Log.d("fadeValuesd", "if: ${fadeValue}")

        val offsetTransitionY by transition.animateFloat(label = "cardOffsetTransition",
            transitionSpec = { tween(durationMillis = 500) },

            targetValueByState = {
                if (it) {
                    valuesTranslation
                } else {
                    0f
                }
            })


        val temp = rightGameAnswersHigh

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFEFF4F9)),
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "High Low",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


                Card(

                    modifier = Modifier
                        .testTag("DraggableCard")
//            .width(165.dp)
                        .wrapContentSize()
//            .height(165.dp)

                        .padding(horizontal = 4.dp, vertical = 1.dp)
                        .offset(
                            x = if (!transitionState.targetState) {
                                if (randomInt == 1) {
                                    offsetTransitionY.dp
                                } else {
                                    -offsetTransitionY.dp
                                }
                            } else {
                                shakeOffset.dp
//                                0.dp
                            }, y = if (transitionState.targetState) {
                                if (randomInt == 1) {
                                    offsetTransitionY.dp
                                } else {
                                    -offsetTransitionY.dp
                                }
                            } else {
                                shakeOffset.dp
//                                0.dp
                            }
                        )
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    if (temp == rightGameAnswersHigh) {
                                        incorrectSound(context)
                                    }
                                    totalGameAnswersHigh++

                                }
                            ) { change, dragAmount ->

//                                Log.d("rightGameAnswersHigh", "HighLowComponent Y: ${dragAmount.y}")
//                                Log.d("rightGameAnswersHigh", "HighLowComponent X: ${dragAmount.x}")
                                when {

                                    previousNumber <= showNumber && (dragAmount.y <= -2.0 && dragAmount.y < 0) && randomInt == 2 -> {

                                        if (dragged) {
                                            dragged = false

                                            transitionState.targetState = true
                                            i23 = 1

                                            GlobalScope.launch {
                                                delay(800)
                                                dragged = true
                                            }
                                            rightGameAnswersHigh++
//                                            Log.d("rightGameAnswersHigh", "HighLowComponent 1st: $rightGameAnswersHigh tota; $totalGameAnswersHigh")
                                            correctSound(context)

                                        }
                                    }

                                    previousNumber > showNumber && (dragAmount.y >= 2.0 && dragAmount.y > 0) && randomInt == 1 -> {

                                        if (dragged) {
                                            dragged = false

                                            transitionState.targetState = true
                                            i23 = 1
                                            GlobalScope.launch {
                                                delay(800)
                                                dragged = true
                                            }
                                            rightGameAnswersHigh++
//                                            Log.d("rightGameAnswersHigh", "HighLowComponent 2nd: $rightGameAnswersHigh tota; $totalGameAnswersHigh")
                                            correctSound(context)
                                        }
                                    }

                                    (dragAmount.y >= 2.0 && randomInt == 2) && dragged -> {
                                         Log.d("rightGameAnswersHigh", "HighLowComponent: if working")
                                        transitionState.targetState = false
                                        shakeOffset = animatedShakeOffset.value
                                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                                    }

                                    (dragAmount.y <= -2.0 && randomInt == 1) && dragged -> {

                                        Log.d("rightGameAnswersHigh", "HighLowComponent: else working")
                                        transitionState.targetState = false
                                        shakeOffset = animatedShakeOffset.value
                                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                                    }


                                }
                            }


                        }
                        .graphicsLayer {

//                if (transition.currentState) {
                            if (i23 < 2) {
                                if (transition.isRunning) {
                                    Log.d("123123", "MyCard2222: ${i23++}")
                                    GlobalScope.launch {

                                        for (i in 0..10) {
                                            delay(25)
                                            fadeValue = 1 - i * 0.1f
                                        }


                                        delay(700)
                                        transitionState.targetState = false


                                        previousNumber = showNumber
                                        showNumber = Random.nextInt(0, 100)
                                        randomInt = if (previousNumber > showNumber) {
                                            1
                                        } else {
                                            2
                                        }

                                        when (randomInt) {
                                            2 -> {
                                                bimap = R.drawable.up
                                            }

                                            1 -> {
                                                bimap = R.drawable.down
                                            }

                                        }


                                        for (i in 0..10) {
                                            delay(25)
                                            fadeValue = 1 - i * 0.1f
                                        }
                                    }
                                }

                            }
                        },

                    ) {
                    Box(
                        modifier = Modifier
                            .size(135.dp)
                            .background(
                                color = Color.White.copy(
                                    alpha = if (transitionState.targetState) {
                                        fadeValue
                                    } else {
                                        1 - fadeValue
                                    }
                                )
                            )
                            .clip(RoundedCornerShape(11.dp)), contentAlignment = Alignment.Center
                    ) {


                        Text(
                            modifier = Modifier.wrapContentSize(),
                            textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,

                            fontSize = 67.sp, color = BirdColor4,
//                    gravity = Alignment.Center,
                            text = "$showNumber"
                        )
                    }
                }

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


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HighLowGame() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.Center
        ) {
            HighLowComponent() { bool, rightAnswer, total -> }
        }
    }
}

