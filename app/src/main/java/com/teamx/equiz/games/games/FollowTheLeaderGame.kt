package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.learningy.items
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

var rightGameAnswersFollow = 1
var wrongGameAnswersFollow = 0
@Composable
fun FollowTheLeaderGame(content: (boolean:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit) {
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


        content(true, rightGameAnswersFollow, wrongGameAnswersFollow)

    }
    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersFollow, wrongGameAnswersFollow)
            }
        }


    }else{
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
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .background(color = Color(0xFF9F81CA)),
                    contentAlignment = Alignment.CenterStart
                ) {

                    BackButton(onClick = {content(false,0,0) }/*onContinueClicked*/)
                    Text(
                        text = "Training",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 17.sp
                    )

                }

                AscendingObjects()
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

@Composable
fun followTheLeaderPlot() {

}

var linkListAdded = LinkedList<Int>()

@Preview
@Composable
fun AscendingObjects() {
    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }
    var currentCount2 by remember { mutableStateOf(1) }



    LaunchedEffect(Unit) {
        repeat(maxCount) { count ->
            delay(290L)
            currentCount = count + 1

        }


    }

    val link = LinkedList<Int>()

    linkListAdded.forEach {
        link.push(it)
    }
    linkListAdded.clear()
    if (link.size >= 1) {
        for (i in link) {
            linkListAdded.push(i)
        }
    } else {
        for (i in 0..maxCount) {
            if (Random.nextBoolean()) {
                linkListAdded.push(i)
            }
        }
    }

    ///

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        /*for (i in 0..currentCount) {

            var temp = linkListAdded
            Log.d("123123", "AscendingObjects:temp ${temp.first}")
            AnimatedObject(i, temp.first) {

                temp.pop()
                linkListAdded = temp
                Log.d("123123", "AscendingObjects:linkListAdded $linkListAdded")
            }
        }*/
        if (linkListAdded.isNotEmpty()) {

            val column = currentCount
            val rows = currentCount


            /*  Column {
                  repeat(3) { column ->
                      Row() {
                          repeat(3) { row ->
                              var temp = linkListAdded
                              val indexValue = row * 3 + column

                              AnimatedObject(indexValue, temp.first) {

                                  temp.pop()
                                  linkListAdded = temp
                                  if (linkListAdded.isEmpty()) {
                                      currentCount2 = currentCount2 + 1
                                      currentCount = 0
                                  }

                                  Log.d("123123", "AscendingObjects:linkListAdded $linkListAdded")
                              }

                          }
                      }
                  }
              }
  */


             LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
                 columns = GridCells.Fixed(3),
                 contentPadding = PaddingValues(16.dp)
             ) {
                 items(currentCount) { item ->

                    var temp = linkListAdded
                    val indexValue = item

                    AnimatedObject(indexValue, temp.first) {

                        temp.pop()
                        linkListAdded = temp
                        if (linkListAdded.isEmpty()) {
                            currentCount2 = currentCount2 + 1
                            currentCount = 0
                        }

                        Log.d("123123", "AscendingObjects:linkListAdded $linkListAdded")
                    }


                }
            }


        }
    }
}

@Composable
fun AnimatedObject(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(Color(0xFF9F81CA)) }

    Surface(
        color =
        if (linkListAdded.contains(number)) {
            Color(0xFF9F81CA)
        } else {
            Color.Transparent
        }, shape = RectangleShape, modifier = Modifier
            .padding(5.dp)
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
                    (2 * 70).dp
                } else if (number > 5) {
                    (3 * 80).dp
                } else {
                    (1 * 40).dp
                }
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = linkListAdded.contains(number)
            ) {
                if (colorState == Color.Transparent && false) {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                    return@clickable
                } else if (number == linkListAdded.first) {
                    colorState = Color.Transparent
                    onClick(itemCompared)
                    Log.d("123123", "AnimatedObjectWrong1:$number ::$itemCompared ")
                } else {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                }
            }

    ) {

        Text(
            text = "",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}


@Keep
data class LeaderListItem(
    var name: Int,
    var height: Dp,
    var color: Color,
    var gamesUID: LeaderEnum,
)

@Composable
fun LeaderColorBox(item: LeaderListItem, onClick: (gamesUID: LeaderEnum) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(4.dp)
            .size(item.height)
            .clip(RoundedCornerShape(10.dp))
            .background(item.color)
//            .offset(y = (-item.name.toInt() * 60).dp)
            .clickable {
                onClick(item.gamesUID)
            }/*, contentAlignment = Alignment.Center*/


    ) {
        Text(

            text = item.name.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive
        )
    }
}

enum class LeaderEnum {
    FollowTheLeaderScreen, Transparent
}