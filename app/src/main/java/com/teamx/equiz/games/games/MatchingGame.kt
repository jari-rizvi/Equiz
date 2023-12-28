package com.teamx.equiz.games.games

import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

var arr = arrayListOf<MemoryItem>()

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MatchingStepGame(modifier: Modifier=Modifier, content:   (boolean:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit={bool,rightAnswer,total ->}) {

    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1
    var isTimeUp by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(10L) }

    var timerRunning by remember { mutableStateOf(true) }
    var gameStarted by remember { mutableStateOf(false) }
    var r by remember { mutableStateOf(0) }
    var r1 by remember { mutableStateOf(0) }
    val myRandomValues = mutableListOf(1, 2, 3, 1, 2, 3)
    myRandomValues.shuffle()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)
    for (i in myRandomValues) {
        arr.add(MemoryItem(name = "$i", uniqueId = counter++))
    }

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
                isGameOver = true
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
        if (gameStarted) {
            LaunchedEffect(true) {
                delay(3000)
                gameStarted = true
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
            Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

                BackButton(onClick = { content(false,0,0) }
                )
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



            Column(
                modifier = modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
            ) {
//        Text(modifier = modifier, text = "Memorize The Same numbers")

//        Text(modifier = modifier, text = "Total Attemps: $r   Accurate: $r1")
                Row(modifier = modifier) {
                    for (i in arr.subList(0, 3).indices) {
                        // Trigger the vibration effect when the Composable is recomposed
                        LaunchedEffect(true) {
                            vibrator?.let { v ->
                                v.vibrate(
                                    VibrationEffect.createOneShot(
                                        500, VibrationEffect.DEFAULT_AMPLITUDE
                                    )
                                )
                                // Delay for the vibration duration
                                delay(500)
                                // Stop the vibration
                                v.cancel()
                            }
                        }

                        CardShape(modifier, i, {
                            r++
                        }, {
                            r1++
                        }, {


                        })
                    }
                }
                Row(modifier = modifier) {
                    for (i in arr.subList(3, 6).indices) {
                        CardShape(modifier, i, {
                            r++
                        }, {
                            r1++
                        }, {})
                    }
                }

                ShakingButton()
//        SwipeGestureExample()
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







    /*else {
            Column(
                modifier = modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
            ) {

                Row(modifier = modifier) {
                    CardShape(modifier, "", false)
                    CardShape(modifier, "", false)
                    CardShape(modifier, "", false)
                }
                Row(modifier = modifier) {
                    CardShape(modifier, "", false)
                    CardShape(modifier, "", false)
                    CardShape(modifier, "", false)
                }
            }
    //        gameStarted = true
        }*/
}

var firstChoice: MemoryItem? = null
var secondChoice: MemoryItem? = null
var listOfAccurate = arrayListOf<Int>()

var counter = 0
var allCounter = 0
var rightCounter = 0

@Composable
fun CardShape(
    modifier: Modifier,
    str: Int,
    addTotal: () -> Unit,
    rightCount: () -> Unit,
    wrongAction: () -> Unit
) {
    var isShaking by remember { mutableStateOf(false) }
    val offset = remember { Animatable(0f) }
    val amplitude = 5f
    val duration = 100
    var colorChange by remember { mutableStateOf(Color.LightGray) }
    var str2 = ""

    val context = LocalContext.current
    LaunchedEffect(isShaking) {
        if (isShaking) {
            launch {
                delay(1000)
                isShaking = false
                offset.snapTo(0f)
                offset.stop()
            }
            while (isShaking) {
                offset.animateTo(
                    targetValue = amplitude,
                    animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                )
                offset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                )
            }
        } else {
            offset.snapTo(0f)
            offset.stop()
        }
    }

    Button(modifier = modifier
        .padding(vertical = 18.dp, horizontal = 6.dp)
        .offset { IntOffset(offset.value.dp.roundToPx(), 0) }
        .requiredWidth(60.dp)
        .requiredHeight(90.dp)
        .height(90.dp)
        .width(90.dp), colors = ButtonDefaults.buttonColors(
        containerColor = colorChange
    ), onClick = {
        addTotal()
        if (firstChoice == null) {
            arr[str].bool = true
            firstChoice = arr[str]
            listOfAccurate.add(arr[str].uniqueId)

        } else {
            if (firstChoice?.uniqueId == arr[str].uniqueId) {
                listOfAccurate.remove(arr[str].uniqueId)
                arr[str].bool = false
                firstChoice = null
            } else {
                if (firstChoice?.name == arr[str].name) {
                    arr[str].bool = true
                    rightCounter++
                    rightCount()
                    listOfAccurate.add(arr[str].uniqueId)
                    firstChoice = null
                } else {
                    isShaking = true
                    wrongAction()
                    /* arr.filter {
                         it != firstChoice
                     }.forEach {
                         it.bool = false
                     }*/
                }
            }
        }

        if (arr[str].bool) {
            str2 = arr[str].name
            colorChange = Color.Blue
        } else {
            colorChange = Color.LightGray
            str2 = ""
        }
    }, elevation = ButtonDefaults.buttonElevation(
        defaultElevation = 6.dp, pressedElevation = 8.dp, disabledElevation = 0.dp
    ), shape = RoundedCornerShape(4.dp)
    ) {
        if (colorChange == Color.LightGray) {
            Text(text = str2, textAlign = TextAlign.Center)
        } else {
            Text(text = arr[str].name, textAlign = TextAlign.Center)
        }
    }

}


/*@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun previewMatching() {
    MaterialTheme {
        MatchingStepGame(Modifier.padding(2.dp))
    }
}*/

@Preview
@Composable
fun ShakingButton() {
    var isShaking by remember { mutableStateOf(false) }
    val offset = remember { Animatable(0f) }
    val amplitude = 5f
    val duration = 100
    /*  DisposableEffect(Unit) {
          val animation = offset.animateTo(
              targetValue = amplitude,
              animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
          )

          onDispose {
              animation.endState.()
          }
      }*/
    LaunchedEffect(isShaking) {
        if (isShaking) {

            /*  offset.animateTo(
                  targetValue = amplitude,
                  animationSpec = repeatable(

                      animation = tween(duration, easing = FastOutSlowInEasing),
                      repeatMode = RepeatMode.Reverse, iterations = 2
                  )
              )*/
            launch {
                delay(1000)
                isShaking = false
                offset.snapTo(0f)
                offset.stop()
            }

            while (isShaking) {
                offset.animateTo(
                    targetValue = amplitude,
                    animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                )
                offset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                )
            }


            /*   offset.animateTo(
                   targetValue = 0f,
                   animationSpec = repeatable(
                       animation = tween(duration, easing = FastOutSlowInEasing),
                       repeatMode = RepeatMode.Reverse, iterations = 2
                   )
               )*/


        } else {
            offset.snapTo(0f)
            offset.stop()
        }
    }

    Button(onClick = { isShaking = !isShaking }, modifier = Modifier
        .padding(16.dp)
//            .align(Alignment.CenterHorizontally)
        .offset { IntOffset(offset.value.dp.roundToPx(), 0) }) {
        Text(text = "Nodding Button")
    }

    /*
        var shakingOffset by remember { mutableStateOf(0f) }

        val shakingAnimation = rememberInfiniteTransition()
        val shakeAnimationSpec = infiniteRepeatable<Float>(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
        val fp = shakingAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = shakeAnimationSpec
        ).value
        LaunchedEffect(isShaking) {
            if (isShaking) {
                shakingOffset = fp
            } else {
                shakingOffset = 0f
            }
        }

        Button(
            onClick = {
                isShaking = !isShaking
                *//* Button click action *//*
        },
        modifier = Modifier
            .padding(16.dp)
            .offset(x = shakingOffset.dp)
//            .align(Alignment.CenterHorizontally)
    ) {
        Text(text = "Shaking Button")
    }*/
}

@Composable
fun GestureDetectionExample() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Text(text = "Drag Me",
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.roundToInt(),
                        offsetY.roundToInt()
                    )
                })
    }
}

/*@Composable
fun SwipeGestureExample() {
    var offsetX by remember { mutableStateOf(0f) }
    var isSwiping by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isSwiping = true },
                    onDragEnd = { isSwiping = false },
                    onDrag = { change, dragAmount ->
                        if (isSwiping) {
                            offsetX += dragAmount.x
                            change.consumeAllChanges()
                        }
                    }
                )
            }
    ) {
        Text(
            text = "Swipe Me",
            modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), 0) }
        )
    }
}*/


data class MemoryItem(
    var name: String = "",
    var color: Color = Color.LightGray,
    var bool: Boolean = false,
    var uniqueId: Int = 0
)