package com.teamx.equiz.games.games.learningy

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlin.random.Random

var sumM = 0
var PresumM = 0

var rightGameAnswersNumPlus = 0
var totalGameAnswersNumPlus = 0

@Preview
@Composable
fun NumPlus(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { _, _, _ -> }) {

    var arr = remember { generateRandomNum() }
    var ischange by remember { mutableStateOf(false) }

    Log.d("123123", "NumPlusSum22:${sumM} ")
    if (PresumM == 0) {
        PresumM = sumM
    }

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


        content(true, rightGameAnswersNumPlus, totalGameAnswersNumPlus)
        rightGameAnswersNumPlus = 0
        totalGameAnswersNumPlus = 1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersNumPlus, totalGameAnswersNumPlus)
                rightGameAnswersNumPlus = 0
                totalGameAnswersNumPlus = 1
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)),
                contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = {
                    content(false, 0, 0)
                    rightGameAnswersNumPlus = 0
                    totalGameAnswersNumPlus = 1
                }
                )
                Text(
                    text = "Touch The Number Plus",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }


            if (ischange) {
                val rightAns = Random.nextInt(0, 5)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        repeat(4) {
                            val value = if (it == rightAns) {
                                PresumM
                            } else {
                                PresumM + Random.nextInt(1, 14)
                            }
                            if (it == rightAns) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(65.dp)
                                        .padding(vertical = 8.dp, horizontal = 70.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.LightGray)
                                        .clickable {
                                            totalGameAnswersNumPlus++
                                            rightGameAnswersNumPlus++
                                            ischange = false
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${value}",
                                        color = Color.Black,
                                        fontSize = 12.sp
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(65.dp)
                                        .padding(vertical = 8.dp, horizontal = 70.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.LightGray)
                                        .clickable {
                                            totalGameAnswersNumPlus++
                                            if (value == PresumM) {
                                                rightGameAnswersNumPlus++
                                            }
                                            ischange = false
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${value}",
                                        color = Color.Black,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                        }
                    }

                }

                PresumM = sumM
            } else {
                Column(
                    Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    repeat(3) { column ->
                        Row(
                            Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(3) { row ->
                                val index = row * 3 + column
                                var colorChanger = mutableStateOf(
                                    if (arr.get(index) >= 1) {
                                        Color.Red
                                    } else {
                                        Color.Transparent
                                    }
                                )

                                Box(
                                    Modifier
                                        .padding(6.dp)
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            color =
                                            if (arr.get(index) == 0) {

                                                Color.Transparent
                                            } else {

                                                colorChanger.value
                                                Color.Transparent
                                            }
                                        )
                                        .clickable(true) {
                                            colorChanger.value = Color.White

                                            arr.set(index, 0)
                                            var boo = true
                                            arr.forEach {
                                                if (it >= 1) {
                                                    boo = false
                                                }
                                            }

                                            if (boo) {


                                                ischange = true
                                                arr.clear()
                                                arr.addAll(generateRandomNum())
                                                Log.d("123123", "NumPlusSum:${arr.sum()} ")
                                                sumM = arr.sum()
                                            }
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    if (arr.get(index) != 0) {
                                        Image(
                                            modifier = Modifier.fillMaxSize(),
                                            painter = painterResource(id = R.drawable.box_numplus),
                                            contentDescription = ""
                                        )
                                    }

                                    Text(
                                        text = "${arr.get(index)}",
                                        color = if (arr.get(index) == 0) {

                                            Color.Transparent
                                        } else {

                                            Color.White
                                        },
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
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

fun generateRandomNum(): ArrayList<Int> {
    var arr = arrayListOf<Int>()

    for (i in 0..8) {
        if (Random.nextBoolean()) {
            arr.add(Random.nextInt(0, 12))
        } else {
            arr.add(0)
        }
    }

    var boo = true
    arr.forEach {
        if (it == 1) {
            boo = false
        }
    }

    if (boo) {
        arr.set(1, 1)
    }

    sumM = arr.sum()


    return arr
}
