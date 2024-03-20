package com.teamx.equiz.games.games.learningy.unfolw

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.learningy.linkListSpin67
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.learningy.musiclearning.incorrectSound
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.BirdColor4
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

class TouchTheNumGame {}
//Touch The Number


@Keep
data class NumberBox(val number: Int, val color: Color)


private fun generateBoxes(): List<NumberBox> {
    val numbers = mutableListOf<Int>()
    for (i in 0..8) {
        numbers.add(i)
    }
    numbers.shuffle()

    val colors = mutableListOf<Color>()
    val colorBox = if (Random.nextBoolean()) BirdColor3 else BirdColor4
    for (i in 0..8) {
        colors.add(colorBox)
    }

    return numbers.mapIndexed { index, number ->
        NumberBox(number = number, color = colors[index])
    }
}

var score = 0


@Composable
fun PreviewTouchTheNumbersGameScreen() {
    followTouchTheNumGamePlus { bool, rightAnswer, total -> }
}


//Touch The Number
var rightGameAnswersTheNum = 0
var totalGameAnswersTheNum = 0

@Preview
@Composable
fun followTouchTheNumGamePlus(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {

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


        content(
            true,
            rightGameAnswersTheNum,
            totalGameAnswersTheNum
        )
        rightGameAnswersTheNum = 0
        totalGameAnswersTheNum = 0

    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(
                    false,
                    rightGameAnswersTheNum,
                    totalGameAnswersTheNum
                )
                rightGameAnswersTheNum = 0
                totalGameAnswersTheNum = 0
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = { content(false, 0, 0) })
                Text(
                    text = "Follow The Leader",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }

            NumAscendingObjects2()

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

@Composable
fun followTheLeaderPlot2() {

}

var numLinkListAdded32 = LinkedList<Int>()


@Composable
fun NumAscendingObjects2() {

    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }

    var changable by remember { mutableStateOf(true) }

    var gameStarted by remember { mutableStateOf(false) }
    LaunchedEffect(currentCount == 0) {
        repeat(maxCount) { count ->
            delay(190L)
            currentCount = count + 1
        }
    }

    if (changable) {

        /*LaunchedEffect(Unit) {
            repeat(maxCount) { count ->
                delay(190L)
                currentCount=count+1
            }
        }*/

        val link = LinkedList<Int>()

        numLinkListAdded32.forEach {
            link.push(it)
        }

        numLinkListAdded32.clear()

        Log.d("123123", "AscendingObjects:linkListAddedabb $link")
        if (link.size >= 1) {
            Log.d("123123", "AscendingObjects:1212 ${numLinkListAdded32}")
            for (i in link) {
                numLinkListAdded32.push(i)
            }
        } else {
            Log.d("123123", "AscendingObjects:12 ${numLinkListAdded32}")
            for (i in 0..maxCount) {
                if (Random.nextBoolean()) {
                    numLinkListAdded32.push(i)
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(10)

            changable = false

        }
    }


    Box(
        modifier = Modifier
            .padding(16.dp)
//            .rotate(90f)
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
//            .rotate(90f)
                .wrapContentSize()
        ) {

            for (i in 0..currentCount) {
                val temp = numLinkListAdded32
//            Log.d("123123", "AscendingObjects:temp ${temp.first}")
                NumAnimatedObject2(i, temp.last) {
                    temp.remove(it)
                    numLinkListAdded32 = temp

                    if (linkListSpin67.isEmpty()) {
                        changable = true
//                        currentCount=0
                    }
                    if (numLinkListAdded32.isEmpty()) {
                        currentCount = 0

                    }
                    Log.d(
                        "123123",
                        "AscendingObjects:linkListAdded ${numLinkListAdded32}"
                    )


                }
            }
        }


    }
}

@Composable
fun NumAnimatedObject2(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor4) }
    val context = LocalContext.current
    var isBoxRight by remember { mutableStateOf(2) }
    var isEffectLaunched by remember { mutableStateOf(false) }
    Surface(
        color = if (numLinkListAdded32.contains(number)) {
            colorState = BirdColor4
            colorState
        } else {
            Color.Transparent
        },
        shape = RectangleShape,
        modifier = Modifier
            .size(85.dp)
            .offset(
                y =
                if (number in 3..5) {
                    (((number % 3) + 2) * 90).dp
                } else if (number > 5) {
                    (((number % 3) + 2) * 90).dp
                } else {
                    ((number + 2) * 90).dp
                },

                x = if (number in 3..5) {
                    (/*-number*/2 * 70).dp
                } else if (number > 5) {
                    (/*-number*/3 * 80).dp
                } else {
                    (/*-number*/1 * 40).dp
                }/*(-number * 60).dp*/
            )
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = 4.dp,
                color = if (isBoxRight == 1) Color.Green else if (isBoxRight == 0) Color.Red else Color.Transparent,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp)
            .clickable(
                enabled = numLinkListAdded32.contains(number)
            ) {
                isEffectLaunched = true
//                totalGameAnswersTheNum++
//
//                if (colorState == Color.Transparent) {
//
//                    Log.d("foolowtheLe", "AnimatedObjectWrong2: ")
//                    return@clickable
//                } else if (number == numLinkListAdded32.last) {
//                    colorState = Color.Transparent
//                    isBoxRight = 1
//                    onClick(itemCompared)
//                    rightGameAnswersTheNum++
//                    correctSound(context)
//                    Log.d("foolowtheLe", "AnimatedObjectWrong2: right")
//                } else {
//                    isBoxRight = 2
//                    incorrectSound(context)
//                    Log.d("foolowtheLe", "AnimatedObjectWrong2: wrong")
//                }
            },

//            .graphicsLayer(rotationZ = rotation)

    ) {

        if (isEffectLaunched) {
            LaunchedEffect(key1 = Unit) {
                totalGameAnswersTheNum++

                if (colorState == Color.Transparent) {

                    Log.d("foolowtheLe", "AnimatedObjectWrong2: ")
//                    isEffectLaunched = false
                    return@LaunchedEffect
                } else if (number == numLinkListAdded32.last) {
                    colorState = Color.Transparent
                    isBoxRight = 1
                    correctSound(context)
                    rightGameAnswersTheNum++
                    delay(200)
                    isBoxRight = 2
                    onClick(itemCompared)
                    Log.d("foolowtheLe", "AnimatedObjectWrong2: right")
                } else {
                    isBoxRight = 0
                    incorrectSound(context)
                    delay(200)
                    isBoxRight = 2
//                    isEffectLaunched = false
                    Log.d("foolowtheLe", "AnimatedObjectWrong2: wrong")
                }
            }
        }


    /*Text(
        modifier = Modifier*//*.rotate(-90f)*//*,
            text = (number + 1).toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (numLinkListAdded32.contains(number)) {
                Color.White
            } else Color.Transparent,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )*/


}
}


@Composable
fun TouchAnimatedObject(number: Int, itemCompared: NumberBox, onClick: (Item: NumberBox) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor3) }

    Box(modifier = Modifier
        .padding(6.dp)
        .background(
            color = itemCompared.color, shape = RoundedCornerShape(6.dp)
        )
        .size(85.dp)
        .clip(RoundedCornerShape(6.dp))

        .clickable {
            onClick(itemCompared)
        }
//            .graphicsLayer(rotationZ = rotation)
        , contentAlignment = Alignment.Center) {

        Text(
            text = itemCompared.number.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}


