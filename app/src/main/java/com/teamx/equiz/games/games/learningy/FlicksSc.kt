package com.teamx.equiz.games.games.learningy

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.learningy.musiclearning.incorrectSound
import com.teamx.equiz.games.games.rightGameAnswersFlick
import com.teamx.equiz.games.games.totalGameAnswersFlick
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.ui.theme.BirdColor1
import com.teamx.equiz.ui.theme.BirdColor3
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

@Preview
@Composable
fun FlicksSc(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { _, _, _ -> }) {

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
        content(true, rightGameAnswersFlick, totalGameAnswersFlick)
        rightGameAnswersFlick = 0
        totalGameAnswersFlick = 0
    }
    if (isTimeUp) {
        Log.d("234234", "FlicksSc:$totalGameAnswersFlick ")
        Log.d("234234", "FlicksSc:$rightGameAnswersFlick ")
        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersFlick, totalGameAnswersFlick)
                totalGameAnswersFlick = 0
                rightGameAnswersFlick = 0
            }
        }


    } else {
        val transitionState = remember { MutableTransitionState(false) }
        val transition = updateTransition(transitionState, "cardTransition")
//    val valuesTranslation = remember { 90f }
        var valuesTranslation by remember {
            mutableFloatStateOf(
                590f
            )
        }


        val offsetTransitionY by transition.animateFloat(label = "cardOffsetTransition",
            transitionSpec = { tween(durationMillis = 900) },

            targetValueByState = {
                if (it) {
                    valuesTranslation
                } else {
                    0f
                }
            })
        val offsetTransitionX by transition.animateFloat(label = "cardOffsetTransition",
            transitionSpec = { tween(durationMillis = 900) },

            targetValueByState = {
                if (it) {
                    valuesTranslation
                } else {
                    0f
                }
            })

//    var randomInt = remember { 1 }


        var randInt by remember { mutableIntStateOf(0) }
        var isInverse by remember { mutableStateOf(false) }

        /*
            LaunchedEffect(transitionState.targetState) {

                delay(500)
                transitionState.targetState = false



            }*/


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFEFF4F9)),

            ) {


            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(165.dp)
                    .offset(
                        x =

                        if (randInt == 2 && transitionState.targetState) {
                            if (isInverse) {
                                offsetTransitionX.dp
                            } else {
                                -offsetTransitionX.dp
                            }

                        } else if (randInt == 3 && transitionState.targetState) {
                            if (isInverse) {
                                -offsetTransitionX.dp
                            } else {
                                offsetTransitionX.dp
                            }
                        } else {
                            0.dp
                        }, y = if (randInt == 0 && transitionState.targetState) {
                            if (isInverse) {
                                offsetTransitionY.dp
                            } else {
                                -offsetTransitionY.dp
                            }
                        } else if (randInt == 1 && transitionState.targetState) {
                            if (isInverse) {
                                -offsetTransitionY.dp
                            } else {
                                offsetTransitionY.dp
                            }
                        } else {
                            0.dp
                        }


                    )
                    .background(
                        color =
                        if (isInverse) {
                            BirdColor1
                        } else {
                            BirdColor3
                        }, shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    modifier = Modifier


                        .size(105.dp)
                        .pointerInput(Unit) {

                            detectDragGestures(
                                onDragEnd = {
                                    totalGameAnswersFlick++

                                    if (!transitionState.targetState) {
                                        randInt = Random.nextInt(0, 4)
                                        isInverse = Random.nextBoolean()
                                        incorrectSound(context)
                                    }
                                }
                            ) { change, dragAmount ->
                                change.consume()


                                Log.d("123123", "FlicksSc:${dragAmount.x} ")
                                Log.d("123123", "FlicksSc:${dragAmount.y} ")


                                if (randInt == 1 && dragAmount.y < 15 && (dragAmount.x.roundToInt() < 10 || dragAmount.x.roundToInt() > -10)) {

                                    return@detectDragGestures
                                } else if (randInt == 0 && dragAmount.y > -15 && (dragAmount.x.roundToInt() < 10 || dragAmount.x.roundToInt() > -10)) {


                                    return@detectDragGestures
                                } else if (randInt == 2 && dragAmount.x > -15 && (dragAmount.y.roundToInt() < 10 || dragAmount.y.roundToInt() > -10)) {


                                    return@detectDragGestures
                                } else if (randInt == 3 && dragAmount.x < 15 && (dragAmount.y.roundToInt() < 10 || dragAmount.y.roundToInt() > -10)) {


                                    return@detectDragGestures
                                }


                                transitionState.targetState = true
                                correctSound(context)

                            }

                        },
                    painter = if (isInverse) {
                        returnArrowIconInverse(enumNumberEnum = randInt)
                    } else {
                        returnArrowIcon(enumNumberEnum = randInt)
                    }, tint = Color.White.copy(
                        alpha = if (transitionState.targetState) {


                            1f
                        } else {
                            1f
                        }
                    ), contentDescription = ""
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
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
            /* Image(
                 modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(),
                 painter = painterResource(id = R.drawable.iconbg),
                 contentDescription = "bg"
             )*/
            if (isAlert) {
                GameAlertingTime()
            }

        }

        LaunchedEffect(transitionState.targetState) {
            if (transitionState.targetState) {
                delay(800)
                rightGameAnswersFlick++

                randInt = Random.nextInt(0, 4)
                isInverse = Random.nextBoolean()
                transitionState.targetState = false

            }
        }
    }


}


@Composable
fun returnArrowIcon(enumNumberEnum: Int): Painter {


    return when (enumNumberEnum) {

        0 -> {

            painterResource(R.drawable.top_arrow_flick)
        }

        1 -> {
            painterResource(R.drawable.down_arrow_flick)
        }


        2 -> {
            painterResource(R.drawable.left_arrow_flick)
        }

        3 -> {
            painterResource(R.drawable.right_arrow_flick)
        }

        else -> {
            painterResource(R.drawable.colorofdeception_icon)

        }
    }
}

@Composable
fun returnArrowIconInverse(enumNumberEnum: Int): Painter {


    return when (enumNumberEnum) {

        1 -> {

            painterResource(R.drawable.top_arrow_flick)
        }

        0 -> {
            painterResource(R.drawable.down_arrow_flick)
        }


        3 -> {
            painterResource(R.drawable.left_arrow_flick)
        }

        2 -> {
            painterResource(R.drawable.right_arrow_flick)
        }

        else -> {
            painterResource(R.drawable.colorofdeception_icon)

        }
    }
}


