package com.teamx.equiz.games.games

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.Pink80
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun MyCard() {
    var swipeState by remember { mutableStateOf(false) }
    var randomInt by remember { mutableStateOf(0) }
    var previousNumber by remember { mutableStateOf(randomInt) }
    var wapsiState by remember { mutableStateOf(false) }

    val transitionState = remember {
        MutableTransitionState(swipeState).apply { targetState = !swipeState }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = 300) },
        targetValueByState = { if (swipeState) 1275f else 0f },
    )
    if (swipeState) {

        LaunchedEffect(key1 = true) {
            delay(1000)

            swipeState = false
            previousNumber = randomInt

            wapsiState = !wapsiState
            randomInt = Random.nextInt(4, 100);
        }

    }
    var i = 0;

    if (previousNumber >= randomInt) {
        wapsiState = true
    } else {
        wapsiState = false

    }

    if (wapsiState) {
        i = -1
    } else {
        i = 1
    }

    Card(
        modifier = Modifier
            .testTag("DraggableCard")
            .width(65.dp)
            .height(65.dp)
            .padding(horizontal = 4.dp, vertical = 1.dp)
            .offset {
                if (wapsiState) {

                    IntOffset(y = offsetTransition.roundToInt(), x = 0)
                } else {
                    IntOffset(y = -offsetTransition.roundToInt(), x = 0)

                }

            }
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (!wapsiState) {
                        when {
                            dragAmount >= 6 -> {
                                swipeState = false
                            }

                            dragAmount < -6 -> {
                                swipeState = true
                            }
                        }
                    } else {
                        when {
                            dragAmount >= 6 -> {
                                swipeState = true
                            }

                            dragAmount < -6 -> {
                                swipeState = false
                            }
                        }
                    }

                }
            }
            .background(color = Color.Gray),


        ) { Text(text = "$randomInt") }
}


@Preview
@Composable
fun HighOrLowGameScreen(content: @Composable () -> Unit = {}, onContinueClicked: () -> Unit = {}) {
//    var score by remember { mutableStateOf(0) }
//    var currentNumber by remember { mutableStateOf(generateNumber()) }

    HighLowGame()
}


var i23 = 1
var dragged = true


@Composable
fun HighLowComponent(content: (boo:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1
    var isTimeUp by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(10L) }

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                  if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
                if (timeLeft<5){
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
        var swipeStateX by remember { mutableStateOf(false) }

        var previousNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
        var showNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
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


        val offsetTransitionY by transition.animateFloat(label = "cardOffsetTransition",
            transitionSpec = { tween(durationMillis = 500) },

            targetValueByState = {
                if (it) {
                    valuesTranslation
                } else {
                    0f
                }
            })




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
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
                                0.dp
                            }, y = if (transitionState.targetState) {
                                if (randomInt == 1) {
                                    offsetTransitionY.dp
                                } else {
                                    -offsetTransitionY.dp
                                }
                            } else {
                                0.dp
                            }


                        )
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->

                                when {

                                    previousNumber <= showNumber && (dragAmount.y <= -2.0 && dragAmount.y < 0) && randomInt == 2 -> {

                                        if (dragged) {
                                            dragged = false
                                            Log.d("123123", "MyCardUP: ${dragAmount.y} $swipeStateX")
                                            transitionState.targetState = true
                                            i23 = 1

                                            GlobalScope.launch {
                                                delay(800)
                                                dragged = true
                                            }
                                        }
                                    }

                                    previousNumber > showNumber && (dragAmount.y >= 2.0 && dragAmount.y > 0) && randomInt == 1 -> {

                                        if (dragged) {
                                            dragged = false
                                            Log.d("123123", "MyCardDOWN:${dragAmount.y} $swipeStateX")
                                            transitionState.targetState = true
                                            i23 = 1
                                            GlobalScope.launch {
                                                delay(800)
                                                dragged = true
                                            }
                                        }
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
                            .size(165.dp)
                            .background(
                                color = Pink80.copy(
                                    alpha = if (transitionState.targetState) {
                                        fadeValue
                                    } else {
                                        1 - fadeValue
                                    }
                                )
                            )
                            .clip(RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center
                    ) {

//                Image(
//                    painter = painterResource(id = bimap),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(165.dp)
//                        .background(
//                            color = BirdColor1.copy(
//                                alpha = if (transitionState.targetState) {
//                                    fadeValue
//                                } else {
//                                    1 - fadeValue
//                                }
//                            )
//                        )
//                )

                        Text(
                            modifier = Modifier.wrapContentSize()

//                        .background(
//                            color = Pink80.copy(
//                                alpha = if (transitionState.targetState) {
//                                    fadeValue
//                                } else {
//                                    1 - fadeValue
//                                }
//                            )
//                        )
//                        .clip(RoundedCornerShape(12.dp))
                            ,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 40.sp,
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


@Preview
@Composable
fun HighLowGame() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.Center
        ) {
            HighLowComponent(){bool,rightAnswer,total ->}
        }
    }
}

