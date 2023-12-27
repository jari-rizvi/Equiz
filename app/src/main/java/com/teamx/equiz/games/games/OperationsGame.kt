package com.teamx.equiz.games.games

import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor4
import kotlin.random.Random


//operations
val operators = listOf("+", "-", "x", "/")

@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun OperationGame(content: (boolean:Boolean) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(20L) }
    var isTimeUp by remember { mutableStateOf(false) }
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
        content(true)
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false)
            }
        }


    }else{
        var equation by remember { mutableStateOf(generateEquation()) }
        var selectedOperator by remember { mutableStateOf("") }
        var allCounter by remember { mutableStateOf(0) }
        var accurateCounter by remember { mutableStateOf(0) }

        var isShaking by remember { mutableStateOf(false) }
        val offset = remember { Animatable(0f) }

        var selectedButtonIndex by remember { mutableStateOf(-1) }
        val context = LocalContext.current
        val vibrator = context.getSystemService(Vibrator::class.java)

        LaunchedEffect(isShaking) {
            if (isShaking) {

                vibrator?.let { v ->
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                    // Delay for the vibration duration
                    kotlinx.coroutines.delay(500)
                    // Stop the vibration
                    isShaking = false
                    v.cancel()
                }
            } else {
                offset.snapTo(0f)
                offset.stop()
            }
        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = equation
                            .replaceFirst("x", "[ ]")
                            .replaceFirst("+", "[ ]")
                            .replaceFirst("-", "[ ]")
                            .replaceFirst("/", "[ ]"),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 26.dp),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = 36.sp,
                    )
                    /*    Text(
                            text = "Accurate: $accurateCounter \n WrongAnswer: $allCounter",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 16.dp),
                            color = Color.White
                        )*/

                }
                Box(
                    modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center
                ) {
                    var counter1 = 0
                    var counter2 = 0
                    var indexer = 0
                    operators.shuffled().forEachIndexed { index, operator ->

                        Box(
                            modifier = Modifier

                                .padding(2.dp)

                                .offset {
                                    IntOffset(
                                        if ((indexer) % 2 == 0) {
                                            if (indexer >= 4) {
                                                indexer = 0
                                            }
                                            indexer++
                                            Log.d("TAG", "GameScreen:$indexer ")
                                            counter1 += 260
                                            counter1 - 390
                                        } else {
                                            if (indexer >= 4) {
                                                indexer = 0
                                            }
                                            indexer++
                                            Log.d("TAG", "GameScreen:$indexer ")
                                            counter1 - 390
                                        }, if ((indexer) % 2 != 0) {
                                            counter2 = 0
                                            counter2 += counter2
                                            counter2
                                        } else {
                                            0 + 260
                                        }
                                    )
                                }
                                .size(83.dp)
                                .clip(RoundedCornerShape((5.dp)))
                                .background(Color.White)
                                .clickable {
                                    selectedButtonIndex = index
                                    allCounter++

                                    selectedOperator = operator
                                    if (selectedOperator == equation.split(" ")[1]) {
                                        equation = generateEquation()
                                        accurateCounter++
                                    } else {
                                        if (index == selectedButtonIndex) {
                                            isShaking = true
                                        }
                                    }
                                    indexer = 0

                                },
                            contentAlignment = Alignment.Center,


                            ) {


                            Text(
                                modifier = Modifier.wrapContentSize(),
                                color = BirdColor4,
                                text = operator,
                                fontWeight = FontWeight.ExtraBold,

                                fontSize = 53.sp
                            )
                        }

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

fun generateEquation(): String {
    val number1 = Random.nextInt(1, 10)
    val number2 = Random.nextInt(1, 10)
    val operator = operators.random()
    val equation = "$number1 $operator $number2"
//    val result = evaluateEquation(number1, number2, operator)
    val result = evaluateEquation(equation)
    return "$number1 $operator $number2 = $result"
}

fun evaluateEquation(equation: String): Int {
    val parts = equation.split(" ")
    val number1 = parts[0].toInt()
    val number2 = parts[2].toInt()
    val operator = parts[1]
    return when (operator) {
        "+" -> number1 + number2
        "-" -> number1 - number2
        "x" -> number1 * number2
        "/" -> number1 / number2
        else -> throw IllegalArgumentException("Invalid operator: $operator")
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ShowBar2() {
    MaterialTheme {
        OperationGame {}
    }


}


@Composable
fun ShowBar() {
    var counter = 0
    var counter2 = 260
    var indexer = 0
    Box(modifier = Modifier.fillMaxSize()) {

        operators.shuffled().forEachIndexed { index, operator ->

            Button(modifier = Modifier
                .padding(2.dp)
                .offset {
                    IntOffset(
                        if ((indexer) % 2 == 0) {
                            indexer++
                            counter += 260
                            counter - 100
                        } else {
                            indexer++
                            counter - 100
                        }, if ((indexer) % 2 != 0) {
                            counter2 = 0
                            counter2 += counter2
                            counter2
                        } else {
                            0 + 260
                        }
                    )
                }
                .size(83.dp),
                onClick = {

                    allCounter++


                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(12.dp)
            ) {
                Text(text = (index).toString())
            }

        }
    }
}

//operations