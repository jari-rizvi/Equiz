package com.teamx.equiz.games.games.ui_components

import android.content.pm.ActivityInfo
import android.os.CountDownTimer
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.learningy.LockScreenOrientation
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.ui.theme.BirdColor3
import com.teamx.equiz.ui.theme.BirdColor4
import com.teamx.equiz.ui.theme.neoGreen
import com.teamx.equiz.ui.theme.neoRed
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

var rightGameAnswersAddition = 0
var totalGameAnswersAddition = 0

var rightGameAnswers = 1
var wrongGameAnswers = 0

@Composable
fun AdditionAddictionGameMethodNew(content: (boolean: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

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


        content(true, rightGameAnswersAddition, (totalGameAnswersAddition))

    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersAddition, (totalGameAnswersAddition))
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

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "Addition Addiction",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }

            AddictGame()

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

var linkListAddict67 = LinkedList<Int>()
var linkListAddict67Checker = LinkedList<Int>()

@Preview
@Composable
fun ViewAddictionGame() {
    MaterialTheme {
        AdditionAddictionGameMethodNew() { bool, rightAnswer, total -> }
    }
}

var previousRandomNumber = 0
var previousIndexRandomNumber = 0

@Composable
fun AddictGame() {
    var counterNum by remember { mutableStateOf(1) }

    val maxCount = 5
    var gameStarted by remember { mutableStateOf(false) }
    var changable by remember { mutableStateOf(false) }

    var counter = 0
    var randNumber by remember { mutableStateOf(0) }
    if (changable) {
        Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 3")
        var effectCompleted by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit) {

            delay(300)
            linkListAddict67.clear()
            linkListAddict67Checker.clear()

            delay(300)
            effectCompleted = true
        }
        if (effectCompleted) {
//            randNumber = Random.nextInt(1, 10)

            randNumber = Random.Default.nextInt(1, 18)

            while (randNumber == previousRandomNumber) {
                randNumber = Random.nextInt(1, 18)
            }
            previousRandomNumber = randNumber

            Log.d("LaunchedEffedf", "AddictGame: LaunchedEffect 4 --> ${randNumber}")

            for (i in 0..maxCount) {

                linkListAddict67.push(i)

            }
            changable = false
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        modifier = Modifier,
                        text = randNumber.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp),
                        verticalArrangement = Arrangement.Center

                    ) {
                        repeat(3) { row ->
                            Row(
                                modifier = Modifier.wrapContentSize().fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(3) { column ->
                                    var index = row * 3 + column

//                                    index = Random.nextInt(1, 10)
//
//                                    while (index == previousIndexRandomNumber) {
//                                        index = Random.nextInt(1, 10)
//                                    }
//
//                                    Log.d("LaedEffectdfdf", "AddictGame: LaunchedEffect $index")
//
//                                    previousIndexRandomNumber = index

//                                Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 4")
                                    AddictObject67(index, randNumber, Color.White) {
                                        changable = it
                                    }
                                }
                            }
                        }
//                        LaunchedEffect(Unit) {
//                            delay(1300)
//                            gameStarted = false
//                            changable = true
//                        }
                    }
                }
            }
        }

    }

    Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 100")

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier,
                text = randNumber.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Center

            ) {
                repeat(3) { row ->
                    Row(
                        modifier = Modifier
                            .wrapContentSize().fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { column ->
                            var index = row * 3 + column
//                            Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 5")
//                            index = Random.nextInt(1, 10)
//
//                            while (index == previousIndexRandomNumber) {
//                                index = Random.nextInt(1, 10)
//                            }
//
//                            Log.d("LaedEffectdfdf", "AddictGame: LaunchedEffect $index")
//
//                            previousIndexRandomNumber = index
                            AddictObject67(index, randNumber, Color.White) {

                                changable = it

                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect")
                    delay(1300)
                    Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 2")
                    gameStarted = false
                    changable = true
                }
            }
        }
    }

//    Box(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize(), contentAlignment = Alignment.Center
//    ) {
//
//
//
//        Column(
//            modifier = Modifier
//                .wrapContentSize()
//                .padding(6.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//
//        ) {
//            Text(
//                modifier = Modifier,
//
//                text = if (randNumber != 0) {
//                    randNumber.toString()
//                } else {
//                    ""
//                },
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
//                fontSize = 60.sp,
//                fontWeight = FontWeight.ExtraBold,
//                textAlign = TextAlign.Center
//            )
//
//            Column(
//                modifier = Modifier
//                    .wrapContentSize()
//                    .padding(6.dp),
//                verticalArrangement = Arrangement.Center
//
//            ) {
//                repeat(2) { row ->
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        repeat(3) { column ->
//                            val index = row * 3 + column
//                            Log.d("123123", "AddictGame:$index ")/*counter*/
//                            AddictObject67(index, randNumber, Color.White) {
////                                temp.remove(it)
////                            temp.pop()
////                                linkListAddict67Checker /*= temp*/
//                                changable = it
//
//                                Log.d(
//                                    "123123", "AscendingObjects:AddictGame $linkListAddict67Checker"
//                                )
//                            }
//                        }
//                    }
//                }
//                LaunchedEffect(Unit) {
//                    delay(1300)
//                    gameStarted = false
//                    changable = true
//                }
//            }
//        }
//    }
}

@Composable
fun AddictObject67(
    number: Int,
    randomNum: Int,
    colorState1: Color,
    onClick: (item: Boolean) -> Unit
) {
    var colorState by remember { mutableStateOf<Color>(Color.White) }
    var colorStateTxt by remember { mutableStateOf<Color>(BirdColor3) }
//    var colorState by remember { mutableStateOf<Color>(Color.Gray) }

//    colorState = Color.White
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(85.dp)
            .background(
                color = if (linkListAddict67Checker.isEmpty() && (colorState == BirdColor4 || colorState == neoGreen || colorState == neoRed)) {
                    Log.d(
                        "Launfectdfdfdfsdsd",
                        "linkListAddict67Checker: color"
                    )
                    colorState = Color.White
                    colorState
                } else {
                    colorState
                }, shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = linkListAddict67Checker.contains(number) || true
            ) {
                totalGameAnswersAddition++
                if (/*colorState == Color.White &&*/ !linkListAddict67Checker.contains(number)) {
//                    colorState = BirdColor4
                    colorStateTxt = Color.White
                    linkListAddict67Checker.push(number)
                    Log.d(
                        "LaunchedEffectdfdfdfsd",
                        "linkListAddict67Checker: $linkListAddict67Checker"
                    )
                    Log.d("LaunchedEffectdfdfdfsd", "linkListAddict67: $linkListAddict67")
                    Log.d("LaunchedEffectdfdfdfsd", "randomNum: $randomNum")
//                    val too =
                    if (linkListAddict67Checker.size <= linkListAddict67.size && randomNum == linkListAddict67Checker.sum()) {

                        Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 6")
                        colorState = BirdColor4
                        rightGameAnswersAddition++
                        correctSound(context)
//                            true
                        onClick(true)
                    } else if (linkListAddict67Checker.sum() < randomNum) {
                        colorState = BirdColor4
                        Log.d("LaunchedEffectdfdfdfsd", "AddictGame: LaunchedEffect contains")
                    } else {
                        colorState = neoRed
                        Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 7")
//                            false
                        onClick(true)
                    }

//                    onClick(too)

                    return@clickable
                } else if (linkListAddict67Checker.contains(number)) {
                    linkListAddict67Checker.remove(number)
                    colorState = Color.White
                    colorStateTxt = BirdColor3
                    val too =
                        if (linkListAddict67Checker.size <= linkListAddict67.size && randomNum == linkListAddict67Checker.sum()) {

                            Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 8")
                            rightGameAnswersAddition++
                            correctSound(context)
                            true
                        } else {
                            Log.d("LaunchedEffectdfdf", "AddictGame: LaunchedEffect 9")
                            false
                        }

                    onClick(too)
                } else {

                }
            }, contentAlignment = Alignment.Center

    ) {
        Column {

            Text(
                modifier = Modifier,
                text = number.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = if (colorState == BirdColor4 || colorState == neoGreen || colorState == neoRed) {
                    Color.White
                } else {
                    BirdColor3
                },
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }


    }
}

@Composable
fun returnNumberImage(enumNumberEnum: NumberEnum): Painter {


    return when (enumNumberEnum) {

        NumberEnum.ONE -> {

            painterResource(id = R.drawable.numbers_blueone)
        }

        NumberEnum.TWO -> {

            painterResource(id = R.drawable.numbers_bluetwo)
        }

        NumberEnum.THREE -> {

            painterResource(id = R.drawable.numbers_bluethree)
        }

        NumberEnum.FOUR -> {

            painterResource(id = R.drawable.numbers_bluefour)
        }

        NumberEnum.FIVE -> {

            painterResource(id = R.drawable.numbers_bluefive)
        }

        NumberEnum.SIX -> {

            painterResource(id = R.drawable.numbers_bluesix)
        }

        NumberEnum.SEVEN -> {

            painterResource(id = R.drawable.numbers_blueseven)
        }

        NumberEnum.EIGHT -> {

            painterResource(id = R.drawable.numbers_blueeight)
        }

        NumberEnum.NINE -> {

            painterResource(id = R.drawable.numbers_bluenine)
        }

        NumberEnum.TEN -> {
            painterResource(id = R.drawable.numbers_blueten)
        }

        NumberEnum.ZERO -> {
            painterResource(id = R.drawable.numbers_bluezero)
        }

        else -> {

            painterResource(id = R.drawable.ic_launcher_background)
        }
    }

}

fun returnEnumNumber(enumNumber: Int): NumberEnum {


    return when (enumNumber) {

        1 -> {
            NumberEnum.ONE
        }

        2 -> {
            NumberEnum.TWO
        }

        3 -> {
            NumberEnum.THREE
        }

        4 -> {
            NumberEnum.FOUR
        }

        5 -> {
            NumberEnum.FIVE
        }

        6 -> {
            NumberEnum.SIX
        }

        7 -> {
            NumberEnum.SEVEN
        }

        8 -> {
            NumberEnum.EIGHT
        }

        9 -> {
            NumberEnum.NINE
        }

        10 -> {
            NumberEnum.TEN
        }

        0 -> {
            NumberEnum.ZERO
        }

        else -> {

            NumberEnum.NINE
        }


    }
}

enum class NumberEnum {
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
}