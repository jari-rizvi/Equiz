package com.teamx.equiz.games.games.learningy


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.rightGameAnswers
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.games.wrongGameAnswers
import com.teamx.equiz.ui.theme.BirdColor4
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

//@Composable
//fun SpinningBlockGame(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {
//
//
//    var isGameOver by remember { mutableStateOf(false) }
//    var isAlert by remember { mutableStateOf(false) }
//    rightGameAnswers = 1
//    wrongGameAnswers = 1
//    var isTimeUp by remember { mutableStateOf(false) }
//
//    var timeLeft by remember { mutableStateOf(20L) }
//    val context = LocalContext.current
//
//    var timerRunning by remember { mutableStateOf(true) }
//    LaunchedEffect(true) {
////        generateOptions()
//
//        // Start the timer
//        object : CountDownTimer(timeLeft * 1000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                if (timerRunning) {
//                    timeLeft = millisUntilFinished / 1000
//                }
//                if (timeLeft < 5) {
//                    isAlert = true
//                }
//            }
//
//            override fun onFinish() {
//                isTimeUp = true
//            }
//        }.start()
//    }
//
//
//    if (isGameOver) {
//        content(true, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
//
//    }
//
//    if (isTimeUp) {
//
//        TimeUpDialogCompose() { i ->
//            if (i) {
//                isGameOver = true
//
//            } else {
//                content(false, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
//            }
//        }
//
//
//    } else {
//        Column {
//            Box(
//                modifier = Modifier
//                    .height(48.dp)
//                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
//            ) {
//
//                BackButton(onClick = {content(false,0,0) }/*onContinueClicked*/)
//                Text(
//                    text = "Spinning Block",
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    color = Color.White,
//                    fontSize = 17.sp
//                )
//
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .background(color = Color(0xFFE1E1E1)),
//            ) {
//                SpinObjects67()
//                Image(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(),
//                    painter = painterResource(id = R.drawable.iconbg),
//                    contentDescription = "bg"
//                )
//                if (isAlert) {
//                    GameAlertingTime()
//                }
//            }
//        }
//    }
//
//
//}

@Preview
@Composable
fun PreviewSpinningBlockGame() {
    MaterialTheme {
//        SpinningBlockGame { bool, rightAnswer, total ->
////            SpinObjects67()
//        }
    }

}


var linkListSpin67 = LinkedList<Int>()

@Preview
@Composable
fun SpinObjects67() {
    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }
    var changable by remember { mutableStateOf(true) }
    var gameStarted by remember { mutableStateOf(false) }
    var rotationState by remember { mutableStateOf(0f) }
    var counterNum by remember { mutableStateOf(1) }
    if (changable) {
        val aRandom = Random.nextInt(1, 7)

        rotationState = 0f
        Log.d("123123", "SpinObjects67:$rotationState ")


        val link = LinkedList<Int>()

        linkListSpin67.forEach {
            link.push(it)
        }
        linkListSpin67.clear()
        Log.d("123123", "AscendingObjects:linkListAdded67abb $link")
        if (link.size >= 1) {
            Log.d("123123", "AscendingObjects:1212 $linkListSpin67")
            for (i in link) {
                linkListSpin67.push(i)
            }
        } else {
            Log.d("123123", "AscendingObjects:12 $linkListSpin67")
            for (i in 0..maxCount) {
                if (Random.nextBoolean()) {
                    linkListSpin67.push(i)
                }
            }
            linkListSpin67.forEach {
                link.push(it)
            }
        }

        Log.d("123123", "AscendingObjects:linkListAdded67abbbbbb $linkListSpin67")

        Log.d("123123", "AscendingObjects:linkListAdded67 ${linkListSpin67.size}")
        ///

        DisposableEffect(changable) {

            gameStarted = true
//            delay(1000L)
            counterNum++
//            colorStateList.add(colorState)
            when (aRandom) {
                1 -> {
                    rotationState = 180f
                }

                2 -> {
                    rotationState = 90f
                }

                3 -> {
                    rotationState = 270f
                }

                4 -> {
                    rotationState = 360f
                }

                5 -> {
                    rotationState = 450f
                }

                6 -> {
                    rotationState = 540f
                }

                7 -> {

                    rotationState = 630f
                }

                else -> {
                    rotationState = 630f

                }
            }
//            rotationState =0f
            Log.d("123123", "SpinObjects67:$rotationState ")

            if (counterNum >= 2) {
                // Stop updating rotationState after two cycles
                changable = false
                counterNum = 1
            }

            onDispose {
                Log.d("123123", "SpinObjects6:Dispose ")
            }
        }

    }
    val rotation by animateFloatAsState(
        targetValue = rotationState, animationSpec = repeatable(
            iterations = 1, animation = tween(3000)
        ), label = ""
    )
    if (!gameStarted) {
        // Animate the rotationState from 0f to 360f repeatedly

////


        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            val temp = linkListSpin67
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp)
                    .rotate(rotation),
                verticalArrangement = Arrangement.Center

            ) {

                repeat(3) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { column ->
                            val index = row * 3 + column
                            SpinningObject67(index) {

                                temp.remove(it)
//                            temp.pop()
                                linkListSpin67 = temp
                                if (linkListSpin67.isEmpty()) {
                                    changable = true
                                }
                                Log.d("123123", "AscendingObjects:linkListAdded67 $linkListSpin67")
                            }
                        }
                    }
                }


            }
        }

    } else {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp)
                    .rotate(rotation),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(6.dp)
                        .rotate(rotation),
                    verticalArrangement = Arrangement.Center

                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(3) { column ->
                                val index = row * 3 + column
                                SpinningObject67Supplementary(index) {


                                    Log.d(
                                        "123123",
                                        "AscendingObjects:linkListAdded67 $linkListSpin67"
                                    )
                                }
                            }
                        }
                    }
                    LaunchedEffect(Unit) {
                        delay(1300)
                        gameStarted = false
                    }

                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(35.dp)
                            .background(
                                color = BirdColor4,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(true) { gameStarted = false },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Memorized",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                }
            }
        }

    }


}

@Composable
fun SpinningObject67(number: Int, onClick: (item: Int) -> Unit) {
//    var colorState by remember { mutableStateOf<Color>(BirdColor4) }
    var colorState by remember { mutableStateOf<Color>(Color.Gray) }

    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(85.dp)
            .background(
                color = if (linkListSpin67.contains(number)) {
//                    colorState = BirdColor4
                    colorState = Color.Gray
                    colorState
                } else {
                    colorState = Color.Gray
//                    Color.Gray
                    colorState
                }, shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = linkListSpin67.contains(number)
            ) {
                if (/*colorState == Color.Gray &&*/ !linkListSpin67.contains(number)) {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$number ")
                    return@clickable
//                } else if (number == linkListAdded67.first) {
                } else if (linkListSpin67.contains(number)) {
//                    colorState = Color.Gray
                    colorState = BirdColor4
                    onClick(number)
                    Log.d("123123", "AnimatedObjectWrong1:$number ::$number ")
                } else {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$number ")
                }
            }, contentAlignment = Alignment.Center
//            .graphicsLayer(rotationZ = rotation)

    ) {

        /* Text(
             text = number.toString(),
             style = MaterialTheme.typography.bodySmall,
             color = Color.White,
             fontSize = 20.sp,
             textAlign = TextAlign.Center,
             fontFamily = FontFamily.Cursive
         )*/


    }
}

@Composable
fun SpinningObject67Supplementary(number: Int, onClick: (item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor4) }

    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(85.dp)
            .background(
                color = if (linkListSpin67.contains(number)) {
                    colorState = BirdColor4
                    colorState
                } else {
                    Color.Gray
//                    colorState
                }, shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = linkListSpin67.contains(number)
            ) {
                if (colorState == Color.Gray && !linkListSpin67.contains(number)) {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$number ")
                    return@clickable
                } else if (linkListSpin67.contains(number)) {
                    colorState = Color.Gray
//                    colorState = BirdColor4
                    onClick(number)
                    Log.d("123123", "AnimatedObjectWrong1:$number ::$number ")
                } else {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$number ")
                }
            }, contentAlignment = Alignment.Center

    ) {


    }
}

@Preview
@Composable
fun previewBird() {
    val isRotating: Boolean = true
    val rotation by animateFloatAsState(
        targetValue = if (isRotating) 180f else 0f,
        animationSpec = repeatable(iterations = 1, animation = tween(3000)), label = ""
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .size(89.dp)
                .background(BirdColor4)
                .graphicsLayer {

                    rotationZ = rotation

                }
        ) {
            Text(text = "Hello")
            RotatingBox()

        }

    }


}

@Preview
@Composable
fun RotatingBox() {
    var isRotating by remember { mutableStateOf(true) }

    val rotation by animateFloatAsState(
        targetValue = if (isRotating) 180f else 0f,
        animationSpec = repeatable(iterations = 1, animation = tween(3000)), label = ""
    )

    LaunchedEffect(isRotating) {
        // Trigger rotation when the component is initially composed
        isRotating = !isRotating
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(89.dp)
                .background(Color.Gray)
                .graphicsLayer(
                    rotationZ = rotation
                )
        ) {
            Text(text = "Hello")
        }
    }
}


@Composable
fun You() {

    /*
    * Box Relative Layout
    * Column   - Linearlayout - veritcal
    * Row- Linearlayout - horizontal
    * Surface  - FrameLayout
    * Icon _ImageView
    * ImageButton
    * Button
    * Text TextView
    * TextField EditText
    *
    * */


}

