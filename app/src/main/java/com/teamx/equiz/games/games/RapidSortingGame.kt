package com.teamx.equiz.games.games

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime. mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R

import com.teamx.equiz.games.ui.theme.Pink80
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RapidSortingGame {}

@Composable
fun RapidSortingGame(content: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

            BackButton(onClick = {}/*onContinueClicked*/)
            Text(
                text = "Training",
                modifier = Modifier
                    .fillMaxWidth()

                    .align(alignment = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 17.sp
            )

        }
        content()

        RapidGame()

    }


}



var i232 = 1
var dragged2 = true


    @Composable
    fun RapidComponent() {
        val swipeStateX by remember { mutableStateOf(false) }

        var previousNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
        var showNumber by remember { mutableStateOf(Random.nextInt(0, 100)) }
        val valuesTranslation by remember {
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
        var bimap by remember { mutableStateOf(R.drawable.round_square_24) }

        val transitionState = remember { MutableTransitionState(false) }
        val transition = updateTransition(transitionState, "cardTransition")


        val offsetTransitionY by transition.animateFloat(label = "cardOffsetTransition",
            transitionSpec = { tween(durationMillis = 700) },

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
    //            .width(165.dp)
                    .wrapContentSize()
    //            .height(165.dp)

                    .padding(horizontal = 4.dp, vertical = 1.dp)
                    .offset(
                        x = if (transitionState.targetState) {
                            if (randomInt == 1) {
                                offsetTransitionY.dp
                            } else {
                                -offsetTransitionY.dp
                            }
                        } else {
                            0.dp
                        }, y = if (transitionState.targetState) {
                            if (randomInt == 1) {
                                (offsetTransitionY*0.2).dp
                            } else {
                                (offsetTransitionY*0.2).dp
                            }
                        } else {
                            0.dp
                        }


                    )
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->

                            when {

                                previousNumber <= showNumber && (dragAmount.x <= -2.0 && dragAmount.x < 0) && randomInt == 2 -> {

                                    if (dragged2) {
                                        dragged2 = false
                                        Log.d("123123", "MyCardUP: ${dragAmount.y} $swipeStateX")
                                        transitionState.targetState = true
                                        i232 = 1

                                        GlobalScope.launch {
                                            delay(500)
                                            dragged2 = true
                                        }
                                    }
                                }

                                previousNumber > showNumber && (dragAmount.x >= 2.0 && dragAmount.x > 0) && randomInt == 1 -> {

                                    if (dragged2) {
                                        dragged2 = false
                                        Log.d("123123", "MyCardDOWN:${dragAmount.y} $swipeStateX")
                                        transitionState.targetState = true
                                        i232 = 1
                                        GlobalScope.launch {
                                            delay(500)
                                            dragged2 = true
                                        }
                                    }
                                }


                            }
                        }


                    }
                    .graphicsLayer {

    //                if (transition.currentState) {
                        if (i232 < 2) {
                            if (transition.isRunning) {
                                Log.d("123123", "MyCard2222: ${i232++}")
                                GlobalScope.launch {

                                    for (i in 0..10) {
                                        delay(25)
                                        fadeValue = 1 - i * 0.1f
                                    }


    //                                delay(700)
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
                                            bimap = R.drawable.round_square_24
                                        }

                                        1 -> {
                                            bimap = R.drawable.baseline_lens_24
                                        }

                                    }


                                    /*for (i in 0..10) {
                                        delay(25)
                                        fadeValue = 1 - i * 0.1f
                                    }*/
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
                                color = Color.White.copy(
                                    alpha = if (transitionState.targetState) {
                                        fadeValue
                                    } else {
                                        1 - fadeValue
                                    }
                                )
                            )

                    )

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
                        text = ""/*"$showNumber"*/
                    )
                }
            }

        }
    }


    @Preview
    @Composable
    fun RapidGame() {
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize(), Alignment.Center
            ) {
                RapidComponent()
            }
        }
    }



