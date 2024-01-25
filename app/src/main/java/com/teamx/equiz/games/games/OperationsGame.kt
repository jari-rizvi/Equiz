package com.teamx.equiz.games.games

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

var rightGameAnswersOp = 0
var wrongGameAnswersOp = 0

//@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun OperationGame(content: (boolean: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { _, _, _ -> }) {

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
        content(true, rightGameAnswersOp, wrongGameAnswersOp)
        rightGameAnswersOp = 0
        wrongGameAnswersOp = 1
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersOp, wrongGameAnswersOp)
                rightGameAnswersOp = 0
                wrongGameAnswersOp = 1
            }
        }


    } else {
        var equation by remember { mutableStateOf(generateEquation()) }
        var firstNum1 by remember { mutableStateOf(firstNum) }
        var secondNum1 by remember { mutableStateOf(secondNum) }
        var resultNum1 by remember { mutableStateOf(resultNum) }
//        var selectedOperator by remember { mutableStateOf("") }
//        var allCounter by remember { mutableStateOf(0) }
//        var accurateCounter by remember { mutableStateOf(0) }

//        var isShaking by remember { mutableStateOf(false) }
//        val offset = remember { Animatable(0f) }

        var selectedButtonIndex by remember { mutableStateOf(-1) }
//        val context = LocalContext.current
//        val vibrator = context.getSystemService(Vibrator::class.java)

        /*  LaunchedEffect(isShaking) {
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
          }*/



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
                    text = "Operation",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    /*Text(
                        text = equation
                            .replaceFirst("x", "[]")
                            .replaceFirst("+", "[]")
                            .replaceFirst("-", "[]")
                            .replaceFirst("/", "[]"),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 26.dp),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = 36.sp,
                    )*/
                    Row(
                        modifier = Modifier.padding(vertical = 18.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = firstNum1.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 36.sp,
                        )
                        Icon(
                            modifier = Modifier
                                .size(25.dp)
                                .background(Color.White, shape = RoundedCornerShape(2.dp)),
                            imageVector = Icons.Default.CheckBoxOutlineBlank,
                            contentDescription = null
                        )
                        Text(
                            text = secondNum1.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 36.sp,
                        )
                        Text(
                            text = "=",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 36.sp,
                        )

                        Text(
                            text = resultNum1.toString(),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 36.sp,
                        )
                    }


                }
                Box(
                    modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center
                ) {
                    /*    var counter1 = 0
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
                                    .clip(RoundedCornerShape((15.dp)))
                                    .background(Color.White)
                                    .clickable {
                                        selectedButtonIndex = index
    //                                    allCounter++
                                        wrongGameAnswersOp++

    //                                    selectedOperator = operator
                                        if (operator == equation.split(" ")[1]) {
                                            equation = generateEquation()
    //                                        accurateCounter++
                                            rightGameAnswersOp++
                                        } else {
                                            equation = generateEquation()
                                            if (index == selectedButtonIndex) {
    //                                            isShaking = true
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

                        }*/

                    Column {
                        repeat(2) { column ->
                            Row {
                                repeat(2) { row ->
                                    val index = row * 2 + column
                                    val operator = operators.get(index)
                                    Box(
                                        modifier = Modifier

                                            .padding(5.dp)


                                            .size(95.dp)
                                            .clip(RoundedCornerShape((10.dp)))
                                            .background(Color.White)
                                            .clickable {
                                                selectedButtonIndex = index
//                                    allCounter++
                                                wrongGameAnswersOp++

//                                    selectedOperator = operator
                                                if (operator == equation.split(" ")[1]) {
                                                    equation = generateEquation()
                                                    firstNum1 = firstNum
                                                    secondNum1 = secondNum
                                                    resultNum1 = resultNum
//                                        accurateCounter++
                                                    rightGameAnswersOp++
                                                } else {
                                                    equation = generateEquation()
                                                    firstNum1 = firstNum
                                                    secondNum1 = secondNum
                                                    resultNum1 = resultNum

                                                    if (index == selectedButtonIndex) {
//                                            isShaking = true
                                                    }
                                                }


                                            },
                                        contentAlignment = Alignment.Center,


                                        ) {

                                        val op = when (operator) {
                                            "+" -> {

                                                painterResource(R.drawable.add)
                                            }

                                            "*" -> {
                                                painterResource(R.drawable.add)
                                            }

                                            "/" -> {
                                                painterResource(R.drawable.add)

                                            }

                                            "x" -> {
                                                painterResource(R.drawable.add)


                                            }

                                            else -> {
                                                painterResource(R.drawable.add)
                                            }
                                        }
                                        Text(
                                            modifier = Modifier.fillMaxSize(),
                                            color = BirdColor4,
                                            text = operator, textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.ExtraBold,

                                            fontSize = 53.sp
                                        )
                                    }
                                }
                            }
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

var firstNum = 0
var secondNum = 0
var resultNum = 0

fun generateEquation(): String {
    val number1 = Random.nextInt(1, 10)
    val number2 = Random.nextInt(1, 10)
    val operator = operators.random()
    val equation = "$number1 $operator $number2"
//    val result = evaluateEquation(number1, number2, operator)
    val result = evaluateEquation(equation)
    firstNum = number1
    secondNum = number2
    resultNum = result

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
        OperationGame {bool,rightAnswer,total ->}
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