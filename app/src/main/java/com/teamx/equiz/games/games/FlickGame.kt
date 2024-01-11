package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor1
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.Pink80
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

var rightGameAnswersFlick = 0
var totalGameAnswersFlick = 1

@Preview
@Composable
fun FlickGameScreen(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var isTimeUp by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(20L) }

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
        content(true, rightGameAnswersFlick, totalGameAnswersFlick)
        rightGameAnswersFlick=0
        totalGameAnswersFlick=1
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersFlick, totalGameAnswersFlick)
                rightGameAnswersFlick=0
                totalGameAnswersFlick=1
            }
        }


    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column {

                Box(
                    modifier = Modifier.fillMaxSize(), Alignment.Center
                ) {
                    FlickComponent()
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

//flick
@Composable
fun FlickComponent() {
    var swipeStateX by remember { mutableStateOf(false) }
    var restart by remember { mutableStateOf(true) }
    val checkOppo by remember { mutableStateOf(false) }
    var randomInt by remember { mutableStateOf(0) }
    var wapsiState by remember { mutableStateOf(false) }
    var intOffset by remember { mutableStateOf(IntOffset(y = 0, x = 0)) }
    var valuesTranslation by remember { mutableStateOf(150f) }

    var bimap by remember { mutableStateOf(R.drawable.right_arrow_flick) }

    val transitionState = remember {
        MutableTransitionState(swipeStateX).apply { targetState = !swipeStateX }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    /*  val offsetTransitionY by transition.animateFloat(label = "cardOffsetTransition",
          transitionSpec = { tween(durationMillis = 300) },
          targetValueByState = { if (swipeStateX) valuesTranslation else 0f })
      val offsetTransitionX by transition.animateFloat(label = "cardOffsetTransition",
          transitionSpec = { tween(durationMillis = 300) },
          targetValueByState = { if (swipeStateX) valuesTranslation else 0f })*/

    wapsiState = !swipeStateX
//    wapsiState = transition.isRunning

    LaunchedEffect(key1 = !swipeStateX) {

        randomInt = Random.nextInt(0, 4)
    }
    if (!wapsiState) {
        valuesTranslation = 0f
        swipeStateX = false
        swipeStateX = false
        valuesTranslation = 100f
    }
    when (randomInt) {

        0 -> {
//            intOffset = IntOffset(x = -offsetTransitionX.roundToInt(), y = 0)
            bimap = R.drawable.right_arrow_flick
        }

        1 -> {
//            intOffset = IntOffset(x = offsetTransitionX.roundToInt(), y = 0)
            bimap = R.drawable.left_arrow_flick
        }

        2 -> {
//            intOffset = IntOffset(y = -offsetTransitionY.roundToInt(), x = 0)
            bimap = R.drawable.top_arrow_flick
        }

        3 -> {
//            intOffset = IntOffset(y = offsetTransitionY.roundToInt(), x = 0)
            bimap = R.drawable.down_arrow_flick
        }

        else -> {
//            intOffset = IntOffset(y = -offsetTransitionY.roundToInt(), x = 0)
            bimap = R.drawable.left_arrow_flick
        }


    }


    if (restart) {
        Box(
            modifier = Modifier
                .testTag("DraggableCard")
                .size(165.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(
                    color = if (checkOppo) {
                        BirdColor1
                    } else {
                        BirdColor3
                    }
                )
                .alpha(valuesTranslation)


                .offset {
                    intOffset

                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if (dragAmount.y > 26 || dragAmount.x > 26) {

                        }
                        when {
                            dragAmount.x >= 26 && randomInt == 0 -> {

                                Log.d("123123", "MyCardRight: ")
                                swipeStateX = true
                                restart = true
                                rightGameAnswersFlick++
                                totalGameAnswersFlick++
                            }

                            dragAmount.x < -26 && randomInt == 1 -> {

                                Log.d("123123", "MyCardLeft: ")
                                swipeStateX = true
                                restart = true
                                rightGameAnswersFlick++
                                totalGameAnswersFlick++
                            }

                            dragAmount.y >= 26 && randomInt == 3 -> {
//                            randomInt = 3
                                Log.d("123123", "MyCardDOWN: ")
                                swipeStateX = true
                                restart = true
                                rightGameAnswersFlick++
                                totalGameAnswersFlick++
                            }

                            dragAmount.y < -26 && randomInt == 2 -> {

                                Log.d("123123", "MyCardUP: ")
                                swipeStateX = true
                                restart = true
                                rightGameAnswersFlick++
                                totalGameAnswersFlick++
                            }

                        }
                    }


                },
        ) {
            Image(
                painter = painterResource(id = bimap),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)

            )


        }
    }

}


//new implementation
var i2322 = 1
var dragged22 = true


@Composable
fun HighLowComponent2() {
    val swipeStateX by remember { mutableStateOf(false) }
    var isInverseCheck by remember { mutableStateOf(true) }

    var previousNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
    var showNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
    val valuesTranslation by remember {
        mutableStateOf(
            590f
        )
    }
    var randomInt by remember { mutableStateOf(1) }
    randomInt = if (25 > showNumber) {
        1
    } else if (50 > showNumber) {
        2
    } else if (75 > showNumber) {
        3
    } else {
        4
    }

    var fadeValue by remember { mutableStateOf(0f) }
    var bimap by remember { mutableStateOf(R.drawable.down) }
    bimap = when (randomInt) {
        4 -> {
            R.drawable.left
        }

        3 -> {
            R.drawable.right
        }

        2 -> {
            R.drawable.up
        }

        1 -> {
            R.drawable.down
        }

        else -> {
            R.drawable.down
        }

    }
    val transitionState = remember { MutableTransitionState(false) }
    val transition = updateTransition(transitionState, "cardTransition")

    val offsetTransitionY by transition.animateFloat(
        label = "cardOffsetTransition", transitionSpec = { tween(durationMillis = 500) },

        targetValueByState = {
            if (it) {
                valuesTranslation
            } else {
                0f
            }
        })
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .testTag("DraggableCard")
                .wrapContentSize()
                .padding(horizontal = 4.dp, vertical = 1.dp)
                .offset(
                    x = if (!transitionState.targetState) {
                        if (randomInt == 1) {
                            offsetTransitionY.dp
                        } else if (randomInt == 2) {
                            -offsetTransitionY.dp
                        } else if (randomInt == 3) {
                            0.dp
                        } else {
                            0.dp
                        }
                    } else {
                        if (randomInt == 1) {
                            0.dp
                        } else if (randomInt == 2) {
                            0.dp
                        } else if (randomInt == 3) {
                            offsetTransitionY.dp
                        } else {
                            -offsetTransitionY.dp
                        }
                    }, y = if (transitionState.targetState) {
                        if (randomInt == 1) {
                            offsetTransitionY.dp
                        } else if (randomInt == 2) {
                            -offsetTransitionY.dp
                        } else if (randomInt == 3) {
                            0.dp
                        } else {
                            0.dp
                        }
                    } else {
                        if (randomInt == 1) {
                            0.dp
                        } else if (randomInt == 2) {
                            0.dp
                        } else if (randomInt == 3) {
                            offsetTransitionY.dp
                        } else {
                            -offsetTransitionY.dp
                        }
                    }
                )
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        when {/*previousNumber <= showNumber &&*/ (dragAmount.x.toInt() == 0 && dragAmount.y <= -4.0 && dragAmount.y < 0) && randomInt == if (isInverseCheck) {
                            1
                        } else 2 -> {

                            if (dragged22) {
                                dragged22 = false
                                Log.d("123123", "MyCardUP: ${dragAmount.y} $swipeStateX")
                                transitionState.targetState = true
                                i2322 = 1

                                GlobalScope.launch {
                                    delay(800)
                                    dragged22 = true
                                }
                            }
                        }

                            /*previousNumber > showNumber &&*/ (dragAmount.x.toInt() == 0 && dragAmount.y >= 4.0 && dragAmount.y > 0) && randomInt == if (isInverseCheck) {
                            2
                        } else 1 -> {

                            if (dragged22) {
                                dragged22 = false
                                Log.d("123123", "MyCardDOWN:${dragAmount.y} $swipeStateX")
                                transitionState.targetState = true
                                i2322 = 1
                                GlobalScope.launch {
                                    delay(800)
                                    dragged22 = true
                                }
                            }
                        }

                            /*previousNumber <= showNumber &&*/ (dragAmount.y.toInt() == 0 && dragAmount.x <= -4.0 && dragAmount.x < 0) && randomInt == if (isInverseCheck) {
                            3
                        } else 4 -> {

                            if (dragged22) {
                                dragged22 = false
                                Log.d("123123", "MyCardUP: ${dragAmount.x} $swipeStateX")
                                transitionState.targetState = true
                                i2322 = 1

                                GlobalScope.launch {
                                    delay(800)
                                    dragged22 = true
                                }
                            }
                        }

                            /*previousNumber > showNumber &&*/ (dragAmount.y.toInt() == 0 && dragAmount.x >= 4.0 && dragAmount.x > 0) && randomInt == if (isInverseCheck) {
                            4
                        } else 3 -> {

                            if (dragged22) {

                                dragged22 = false
                                Log.d("123123", "MyCardDOWN:${dragAmount.x} $swipeStateX")
                                transitionState.targetState = true
                                i2322 = 1

                                GlobalScope.launch {
                                    delay(800)
                                    dragged22 = true
                                }
                            }
                        }
                        }
                    }
                }
                .graphicsLayer {

                    if (i2322 < 2) {
                        if (transition.isRunning) {
                            Log.d("123123", "MyCard2222: ${i2322++}")
                            GlobalScope.launch {

                                for (i in 0..10) {
                                    delay(25)
                                    fadeValue = 1 - i * 0.1f
                                }
                                delay(700)
                                transitionState.targetState = false
                                previousNumber = showNumber
                                showNumber = Random.nextInt(0, 100)
                                isInverseCheck = Random.nextBoolean()
                                randomInt = if (previousNumber > showNumber) {
                                    1
                                } else {
                                    2
                                }
                                randomInt = if (25 > showNumber) {
                                    1
                                } else if (50 > showNumber) {
                                    2
                                } else if (75 > showNumber) {
                                    3
                                } else {
                                    4
                                }

                                when (randomInt) {
                                    4 -> {
                                        bimap = R.drawable.left
                                    }

                                    3 -> {
                                        bimap = R.drawable.right
                                    }

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
                Image(
                    painter = painterResource(id = bimap),
                    contentDescription = null,
                    modifier = Modifier
                        .size(165.dp)
                        .background(
                            color = if (isInverseCheck) {

                                BirdColor1.copy(
                                    alpha = if (transitionState.targetState) {
                                        fadeValue
                                    } else {
                                        1 - fadeValue
                                    }
                                )
                            } else {
                                BirdColor3.copy(
                                    alpha = if (transitionState.targetState) {
                                        fadeValue
                                    } else {
                                        1 - fadeValue
                                    }
                                )
                            }
                        )
                )

//                Text(
//                    modifier = Modifier.wrapContentSize(),
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily.Monospace,
//                    fontSize = 40.sp,
////                    gravity = Alignment.Center,
//                    text = "$showNumber"
//                )
            }
        }

    }
}


@Preview
@Composable
fun FlickGameS() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(), Alignment.Center
        ) {
            HighLowComponent2()
        }
    }
}


//
