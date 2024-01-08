package com.teamx.equiz.games.games.learningy


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.teamx.equiz.ui.theme.BirdColor4
import kotlin.random.Random


@Preview
@Composable
fun MatchingGamingScene() {

    val arr = remember { generateArrList() }
    val arr2 = remember { arrayListOf<Int>() }
    var changeable by remember { mutableStateOf(true) }

    Log.d("123123", "MatchingGamingScenearr2:$arr2 ")
    Log.d("123123", "MatchingGamingScenearr:$arr ")

    LaunchedEffect(changeable) {

    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column {
            repeat(2) { column ->
                Row() {
                    repeat(3) { row ->
                        val index = row * 2 + column
                        var colorState = mutableStateOf(
                            if (arr2.contains(index)) {
                                BirdColor4
                            } else {
                                Color.White
                            }
                        )
                        Box(modifier = Modifier
                            .padding(5.dp)
                            .size(80.dp)
                            .clickable(true) {


                                if (colorState.value == BirdColor4) {
                                    colorState.value = Color.White
                                    arr2.remove(index)
                                } else if (arr2.isEmpty()) {
                                    arr2.add(index)
                                    colorState.value = BirdColor4
                                } else if (!arr2.contains(index) && arr[arr2[0]] == arr[index]) {
//                                    arr2.add(index)
                                    colorState.value = BirdColor4
                                    Log.d(
                                        "123123", "MatchingGamingScene1:${
                                            arr[arr2[0]]
                                        } "
                                    )
                                    Log.d(
                                        "123123", "MatchingGamingScene2:${
                                            arr[index]
                                        } "
                                    )
                                    arr2.removeAt(0)
                                    arr2.clear()
                                    colorState.value = BirdColor4
                                    arr.clear()
                                    arr.addAll(generateArrList())
                                    changeable = !changeable
                                    rightGameAnswersMatc++
                                    totalGameAnswersMatc++
                                } else {
                                    totalGameAnswersMatc++

                                    arr2.removeAt(0)
                                    arr2.clear()
                                    colorState.value = BirdColor4
                                    arr.clear()
                                    arr.addAll(generateArrList())
                                    changeable = !changeable
                                }


                            }
                            .background(
                                shape = RoundedCornerShape(12.dp),
                                color = colorState.value/*if (arr2.contains(index)) {
                                    BirdColor4
                                } else {
                                    Color.White
                                }*/
                            ),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "${arr[index]}",
                                fontSize = 35.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (colorState.value == Color.White) {
                                    BirdColor4
                                } else {
                                    Color.White

                                }
                            )
                        }

                    }
                }
            }
        }

    }


}
var rightGameAnswersMatc = 0
var totalGameAnswersMatc = 1
@Preview
@Composable
fun ViewMatching(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { _, _, _ -> }) {

    var arr = remember { generateRandomNum() }
    var ischange by remember { mutableStateOf(false) }


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


        content(true, rightGameAnswersMatc, (totalGameAnswersMatc))
        rightGameAnswersMatc = 0
        totalGameAnswersMatc = 1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersMatc, (totalGameAnswersMatc))
                rightGameAnswersMatc = 0
                totalGameAnswersMatc = 1
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)),
                contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = {
                    content(false, 0, 0)
                    rightGameAnswersMatc = 0
                    totalGameAnswersMatc = 1
                }
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

            MatchingGamingScene()


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

fun areElementsEqualAtIndex(list: ArrayList<Int>): Boolean {
    return list.size >= 2 && list[0] == list[1]
}

fun findIndicesOfIdenticalElements(list: ArrayList<Int>): ArrayList<Int> {
    val indices = arrayListOf<Int>()

    for (i in 0 until list.size - 1) {
        if (list[i] == list[i + 1]) {
            indices.add(i)
            indices.add(i + 1)
        }
    }

    return indices
}

fun generateArrList(): ArrayList<Int> {
    var arr = arrayListOf<Int>()
    arr.clear()
    for (i in 0..4) {

        arr.add(Random.nextInt(0, 100))

    }

    arr.add(arr.get(0))
    arr.shuffle()
    return arr
}



