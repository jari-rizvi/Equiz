package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor1
import com.teamx.equiz.games.ui.theme.BirdColor2
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.BirdColor4
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random


var rightGameAnswersBird = 0
var wrongGameAnswersBird = 0
@Preview
@Composable
fun BirdWatchingGame(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit={_,_,_->}) {
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
                isTimeUp = true
            }
        }.start()
    }


    if (isGameOver) {


        content(true, rightGameAnswersBird, wrongGameAnswersBird)
        rightGameAnswersBird = 0
        wrongGameAnswersBird = 1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersBird, wrongGameAnswersBird)
                rightGameAnswersBird = 0
                wrongGameAnswersBird = 1
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xffEFF4F8)),
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "Bird Watching",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                   /* Icon(imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "BackButton",
                        tint = Color.White,
                        modifier = Modifier.clickable(true) {
                            content(false, rightGameAnswersBird, (rightGameAnswersBird + wrongGameAnswersBird))
                        }

                    )*/
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                BirdAscendingObjects()
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

//

var iteration = 0

@Preview
@Composable
fun BirdAscendingObjects() {
//    val maxCount = 8
    val maxCount by remember {
        mutableStateOf(
            if (iteration % 1 == 0) {
                8
            } else {
                8

            }
        )
    }
//    iteration = 0

//    val maxCount = 4
    val birdLinkListAdded by remember { mutableStateOf(LinkedList<BirdListItem>()) }
    var restart by remember { mutableStateOf(true) }
    val link = LinkedList<BirdListItem>()
    if (restart) {
//        restart = true

        birdLinkListAdded.forEach {
            link.push(it)
        }
        Log.d("123123", "AscendingObjects:birdLinkListAdded $birdLinkListAdded")
        birdLinkListAdded.clear()
        Log.d("123123", "AscendingObjects:birdLinkListAdded $link")

        Log.d("123123", "AscendingObjects:12 $birdLinkListAdded")
        for (i in 0..maxCount) {
            val randomNum = Random.nextInt(0, 4)
            birdLinkListAdded.push(
                BirdListItem(
                    i,
                    valueColor = randomNum,
                    12.sdp,
                    checkNumberReturnColor2(BirdEnum.values()[randomNum]),
                )
            )
        }
//        }

        Log.d("123123", "AscendingObjects:birdLinkListAddedab $birdLinkListAdded")

        Log.d("123123", "AscendingObjects:birdlinkListAdded ${birdLinkListAdded.size}")
        ///
        var rotationState by remember { mutableStateOf(0f) }


        LaunchedEffect(Unit) {
            repeat(180) { count ->
                delay(10L)
//            colorStateList.add(colorState)
                rotationState = 180f

            }
        }
        // Animate the rotationState from 0f to 360f repeatedly
        val rotation by animateFloatAsState(
            targetValue = rotationState, animationSpec = repeatable(
                iterations = 1, animation = tween(5000)
            ), label = ""
        )
        ///
    }
    Box(
        modifier = Modifier
            .padding(16.sdp)
            .fillMaxSize(), contentAlignment = Alignment.Center
//            .graphicsLayer(rotationY = rotation), contentAlignment = Alignment.Center
    ) {

        //don't remove this comment
        /* for (i in birdLinkListAdded) {



             BirdAnimatedObject(i) { it ->

                 val clickedCount = birdLinkListAdded.count { a -> a.valueColor == it.valueColor }
                 var maxCount2 = birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                     .filter { it.value >= 0 }.values.max()
                 if (clickedCount == maxCount2) {
                     restart = false
                     restart = true
                     iteration++

                     rightGameAnswersBird++
                 } else {
                     wrongGameAnswersBird++
                 }
 //                Log.d("123123", "AscendingObjects:birdLinkListAdded $it")
                 Log.d(
                     "123123",
                     "@@@@@$iteration@@@@" + birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                         .filter { it.value >= 0 }.values.max()
                 )

                 val aia = birdLinkListAdded.groupingBy { it.valueColor }
                     .eachCount()
                     .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                         .eachCount().filter { it.value >= 1 }.values.max())
                 Log.d(
                     "123123",
                     "@@@@@" + BirdEnum.values()[birdLinkListAdded.groupingBy { it.valueColor }
                         .eachCount()
                         .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                             .eachCount().filter { it.value >= 1 }.values.max())]
                 ).toString()

                 Log.d(
                     "123123",
                     "@@@@@" + birdLinkListAdded.groupingBy { it.valueColor }
                         .eachCount()
                         .filter { it.value >= 0 }.values
                 )
                 Log.d(
                     "123123",
                     "@@@@@" + BirdEnum.values()[it.valueColor].toString()
                 )
                 Log.d(
                     "123123",
                     "@@@@@" + birdLinkListAdded.count { a -> a.valueColor == it.valueColor }
                 )
             }


         }*/

        Column {
            repeat(3) { column ->

                Row {
                    repeat(3) { row ->
                        val index = row * 3 + column
                        val indexElement = birdLinkListAdded.get(index)
                        BirdAnimatedObjectTy(indexElement) { it ->
                            wrongGameAnswersBird++
                            val clickedCount =
                                birdLinkListAdded.count { a -> a.valueColor == it.valueColor }
                            var maxCount2 =
                                birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                                    .filter { it.value >= 0 }.values.max()
                            if (clickedCount == maxCount2) {
                                restart = false
                                restart = true
                                iteration++

                                rightGameAnswersBird++
                            } else {

                            }
//                Log.d("123123", "AscendingObjects:birdLinkListAdded $it")
                            Log.d(
                                "123123",
                                "@@@@@$iteration@@@@" + birdLinkListAdded.groupingBy { it.valueColor }
                                    .eachCount()
                                    .filter { it.value >= 0 }.values.max()
                            )

                            val aia = birdLinkListAdded.groupingBy { it.valueColor }
                                .eachCount()
                                .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                                    .eachCount().filter { it.value >= 1 }.values.max())
                            Log.d(
                                "123123",
                                "@@@@@" + BirdEnum.values()[birdLinkListAdded.groupingBy { it.valueColor }
                                    .eachCount()
                                    .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                                        .eachCount().filter { it.value >= 1 }.values.max())]
                            ).toString()

                            Log.d(
                                "123123",
                                "@@@@@" + birdLinkListAdded.groupingBy { it.valueColor }
                                    .eachCount()
                                    .filter { it.value >= 0 }.values
                            )
                            Log.d(
                                "123123",
                                "@@@@@" + BirdEnum.values()[it.valueColor].toString()
                            )
                            Log.d(
                                "123123",
                                "@@@@@" + birdLinkListAdded.count { a -> a.valueColor == it.valueColor }
                            )
                        }
                    }
                }
            }
        }


    }
}


@Composable
fun BirdAnimatedObject(
    itemCompared: BirdListItem,
    onClick: (Item: BirdListItem) -> Unit
) {


    Surface(
        color = itemCompared.color,
        shape = RectangleShape,
        modifier = Modifier
            .size(85.dp)
            .offset(
                y = if (itemCompared.name in 3..5) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else if (itemCompared.name > 5) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else {
                    ((itemCompared.name + 2) * 90).dp
                }, x = if (itemCompared.name in 3..5) {
                    (2 * 70).dp
                } else if (itemCompared.name > 5) {
                    (3 * 80).dp
                } else {
                    (1 * 40).dp
                }
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
//                enabled = birdlinkListAdded.contains(number)
            ) {
                onClick(itemCompared)/*   if (itemCompared.color == Color.Transparent) {
                       Log.d("123123", "AnimatedObjectWrong2:${itemCompared.name} ::$itemCompared ")
                       return@clickable
                   } else if (itemCompared.name == birdlinkListAdded.first.name) {

                       onClick(itemCompared)
                       Log.d("123123", "AnimatedObjectWrong1:$itemCompared.name ::$itemCompared ")
                   } else {
                       Log.d("123123", "AnimatedObjectWrong2:$itemCompared.name ::$itemCompared ")
                   }*/
            }) {

        Text(
            text = /*itemCompared.name.toString() +*/"11",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Transparent,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )
    }
}

@Composable
fun BirdAnimatedObjectTy(
    itemCompared: BirdListItem,
    onClick: (Item: BirdListItem) -> Unit
) {


    Surface(
        color = itemCompared.color,
        shape = RectangleShape,
        modifier = Modifier
            .size(99.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(6.dp))

            .clickable(
//                enabled = birdlinkListAdded.contains(number)
            ) {
                onClick(itemCompared)/*   if (itemCompared.color == Color.Transparent) {
                       Log.d("123123", "AnimatedObjectWrong2:${itemCompared.name} ::$itemCompared ")
                       return@clickable
                   } else if (itemCompared.name == birdlinkListAdded.first.name) {

                       onClick(itemCompared)
                       Log.d("123123", "AnimatedObjectWrong1:$itemCompared.name ::$itemCompared ")
                   } else {
                       Log.d("123123", "AnimatedObjectWrong2:$itemCompared.name ::$itemCompared ")
                   }*/
            }) {

        Text(
            text = /*itemCompared.name.toString() +*/"11",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Transparent,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )
    }
}

@Composable
fun BirdAnimatedObject2(
    itemCompared: BirdListItem,
    onClick: (Item: BirdListItem) -> Unit
) {


    Surface(
        color = itemCompared.color,
        shape = RectangleShape,
        modifier = Modifier
            .size(35.dp)
            .offset(

                y = if (itemCompared.name in 3..5) {
                    (((itemCompared.name % 3) + 2) * 45).dp
                } else if (itemCompared.name > 5) {
                    (((itemCompared.name % 3) + 2) * 45).dp
                } else {
                    ((itemCompared.name + 2) * 45).dp
                }, x = if (itemCompared.name in 3..5) {
                    (2 * 40).dp
                } else if (itemCompared.name > 5) {
                    (3 * 40).dp
                } else {
                    (1 * 40).dp
                }
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
//                enabled = birdlinkListAdded.contains(number)
            ) {
                onClick(itemCompared)/*   if (itemCompared.color == Color.Transparent) {
                       Log.d("123123", "AnimatedObjectWrong2:${itemCompared.name} ::$itemCompared ")
                       return@clickable
                   } else if (itemCompared.name == birdlinkListAdded.first.name) {

                       onClick(itemCompared)
                       Log.d("123123", "AnimatedObjectWrong1:$itemCompared.name ::$itemCompared ")
                   } else {
                       Log.d("123123", "AnimatedObjectWrong2:$itemCompared.name ::$itemCompared ")
                   }*/
            }) {

        Text(
            text = /*itemCompared.name.toString() +*/"11",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Transparent,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )
    }
}

@Keep
data class BirdListItem(
    var name: Int,
    var valueColor: Int,
    var height: Dp,
    var color: Color
)


fun checkNumberReturnColor2(index: BirdEnum): Color {
    return when (index) {
        BirdEnum.CYAN -> {
            BirdColor3
        }

        BirdEnum.VOILET -> {
            BirdColor4
        }


        BirdEnum.RED -> {
            BirdColor1

        }

        BirdEnum.GREEN -> {
            BirdColor2

        }

        /* BirdEnum.BLUE -> {
             Color.Blue
         }
           BirdEnum.GRAY -> {
             Color.LightGray
         }
         BirdEnum.MAGENTA -> {
             Color.Magenta
         }

         BirdEnum.ORANGE -> {
             Purple200
         }

         BirdEnum.PINK -> {
             Pinky
         }

         BirdEnum.VIOLET -> {
             Teal200
         }

         BirdEnum.WHITE -> {
             Color.White
         }*/

        else -> {
            BirdColor4
        }
    }
}

enum class BirdEnum {
    RED, GREEN, CYAN, VOILET, /*, BLUE, MAGENTA, ORANGE, PINK, VIOLET, WHITE, GRAY*/
}
//
