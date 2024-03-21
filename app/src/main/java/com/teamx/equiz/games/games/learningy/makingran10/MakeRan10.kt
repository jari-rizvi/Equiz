package com.teamx.equiz.games.games.learningy.makingran10

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton
import com.teamx.equiz.games.games.disableScrolling
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.learningy.musiclearning.incorrectSound
import com.teamx.equiz.games.games.learningy.unfolw.rightGameAnswersTheNum
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.ui.theme.BirdColor4
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlin.random.Random

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


var rightGameAnswersRain = 0
var wrongGameAnswersRain = 0

@Composable
fun Rain10Game(content: (boolean: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }


    var timeLeft by remember { mutableStateOf(20L) }
    val context = LocalContext.current
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
        content(true, rightGameAnswersRain, wrongGameAnswersRain)
        rightGameAnswersRain = 0
        wrongGameAnswersRain = 0
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            Log.d("123123", "Rain10Game: $rightGameAnswersRain")
            Log.d("123123", "Rain10Game: $wrongGameAnswersRain")
            if (i) {
                isGameOver = true

            } else {

                content(false, rightGameAnswersRain, wrongGameAnswersRain)
                rightGameAnswersRain = 0
                wrongGameAnswersRain = 0
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(),
                ) {


                    rain10Drops()

                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .background(color = Color(0xFF9F81CA)),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        BackButton(onClick = { content(false, 0, 0) }/*onContinueClicked*/)
                        Text(
                            text = "Make Ten",
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 17.sp
                        )
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

@Composable
fun rain10Drops() {

    val availableCards = remember { mutableStateOf(generateArray()) }

    val leftItems = remember { ArrayList<RainListItem>() }
    val midItems = remember { ArrayList<RainListItem>() }
    val rightItems = remember { ArrayList<RainListItem>() }

    availableCards.value.forEach {


        leftItems.add(RainListItem(it[0].toString(), 110.dp))
        midItems.add(RainListItem(it[1].toString(), 110.dp))
        rightItems.add(RainListItem(it[2].toString(), 110.dp))
    }

    leftItems.get(leftItems.lastIndex).isClickable = true
    midItems.get(midItems.lastIndex).isClickable = true
    rightItems.get(rightItems.lastIndex).isClickable = true


    val state = rememberLazyListState()
    state.disableScrolling(GlobalScope)


    /*
    var leftItems = (0..(173)).map {
        RainListItem(
            height = 110.dp,
            name = "$it",
        )
    }
    var midItems = (0..(173)).map {
        RainListItem(
            height = 110.dp,
            name = "$it",
        )
    }
    var rightItems = (0..(173)).map {
        RainListItem(
            height = 110.dp,
            name = "$it",
        )
    }
    leftItems = leftItems.shuffled()
    rightItems = rightItems.shuffled()
    midItems = midItems.shuffled()*/


    var leftIndexCounter by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val deletedIndexList = remember { mutableStateListOf<Int>() }
    val leftScrollState = rememberLazyListState()
    val rightScrollState2 = rememberLazyListState()
    val midScrollState2 = rememberLazyListState()

    LaunchedEffect(true) {
        leftScrollState.scrollToItem(leftItems.size)
        midScrollState2.scrollToItem(midItems.size)
        rightScrollState2.scrollToItem(rightItems.size)
        leftScrollState.scroll(MutatePriority.PreventUserInput) {}
        midScrollState2.scroll(MutatePriority.PreventUserInput) {}
        rightScrollState2.scroll(MutatePriority.PreventUserInput) {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {


            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {


                LazyColumn(
                    userScrollEnabled = false,
                    modifier = Modifier
                        .width(100.dp)

                        .heightIn(440.dp, max = 460.dp)
                        .clickable(enabled = false, null, null, {}),
                    contentPadding = PaddingValues(1.dp),
                    state = leftScrollState
                ) {

                    itemsIndexed(items = leftItems, itemContent = { index, item ->

                        AnimatedVisibility(
                            visible = !deletedIndexList.contains(index),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 500))
                        ) {
                            Log.d("AnimatedVisiy", "rain10Drops: 1")
                            drop(item = item, index = index) {
                                val iu = leftItems.lastIndex - leftIndexCounter
                                val iu2 = midItems.lastIndex - leftIndexCounter
                                val iu3 = rightItems.lastIndex - leftIndexCounter
                                if (it) {
                                    correctSound(context)
                                    rightGameAnswersRain++
                                } else {
                                    incorrectSound(context)
                                }
                                wrongGameAnswersRain++
                                deletedIndexList.add(index)

                                leftItems[iu - 1].isClickable = true
                                midItems[iu2 - 1].isClickable = true
                                rightItems[iu3 - 1].isClickable = true

                                leftIndexCounter++

                            }
                        }
                    })
                }

            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {


                LazyColumn(

                    userScrollEnabled = false,
                    modifier = Modifier
                        .width(100.dp)
                        .heightIn(440.dp, max = 460.dp)
                        .clickable(enabled = false, null, null, {}),
                    contentPadding = PaddingValues(1.dp),

                    state = midScrollState2

                ) {
                    itemsIndexed(items = midItems, itemContent = { index, item ->

                        AnimatedVisibility(
                            visible = !deletedIndexList.contains(index),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 500))
                        ) {
                            Log.d("AnimatedVisiy", "rain10Drops: 2")
                            drop(item = item, index = index) {
                                val iu = leftItems.lastIndex - leftIndexCounter
                                val iu2 = midItems.lastIndex - leftIndexCounter
                                val iu3 = rightItems.lastIndex - leftIndexCounter

                                if (it) {
                                    correctSound(context)
                                    rightGameAnswersRain++
                                } else {
                                    incorrectSound(context)

                                }
                                wrongGameAnswersRain++
                                deletedIndexList.add(index)

                                leftItems[iu - 1].isClickable = true
                                midItems[iu2 - 1].isClickable = true
                                rightItems[iu3 - 1].isClickable = true

                                leftIndexCounter++

                            }
                        }
                    })
                }
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
//        Row() {

                LazyColumn(
                    userScrollEnabled = false,
                    modifier = Modifier
                        .width(100.dp)
                        .heightIn(440.dp, max = 460.dp)
                        .clickable(enabled = false, null, null, {}),
                    contentPadding = PaddingValues(1.dp),
                    state = rightScrollState2
                ) {
                    itemsIndexed(items = rightItems, itemContent = { index, item ->

                        AnimatedVisibility(
                            visible = !deletedIndexList.contains(index),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 500))
                        ) {
                            Log.d("AnimatedVisiy", "rain10Drops: 3")
                            drop(item = item, index = index) {
                                val iu = leftItems.lastIndex - leftIndexCounter
                                val iu2 = midItems.lastIndex - leftIndexCounter
                                val iu3 = rightItems.lastIndex - leftIndexCounter

                                if (it) {
                                    correctSound(context)
                                    rightGameAnswersRain++
                                } else {
                                    incorrectSound(context)

                                }
                                wrongGameAnswersRain++
                                deletedIndexList.add(index)

                                leftItems[iu - 1].isClickable = true
                                midItems[iu2 - 1].isClickable = true
                                rightItems[iu3 - 1].isClickable = true

                                leftIndexCounter++

                            }
                        }
                    })
                }
            }
        }


    }

}

var sum = 0

@Composable
fun drop(index: Int, item: RainListItem, onClick: (boo: Boolean) -> Unit) {


    var colorState by remember { mutableStateOf(R.drawable.white_card) }

    var isBoxRight by remember { mutableStateOf(2) }
    var isEffectLaunched by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(item.height)

            .graphicsLayer {
                clip = true
                if (item.isClickable) {
                } else {
                }


            }
            /*.offset {
                if (item.isClickable) {
                    IntOffset(index, 0)
                } else {
                    IntOffset(index, -105)
                }
            }*/
            .clickable(enabled = item.isClickable, null) {
                if (sum < 0) {
                    sum = 0
                }

                if (colorState == R.drawable.purple_card) {
                    sum = sum - item.name.toInt()
                    colorState = R.drawable.white_card
                } else {
                    sum = sum + item.name.toInt()
                    colorState = R.drawable.purple_card
                }
                Log.d("123123", "drop 123123: $sum ")

                isEffectLaunched = true
//                if (sum % 10 == 0) {
//                    sum = 0
//                    onClick(true)
//
//                } else if (sum > 10) {
//                    sum = 0
//                    onClick(false)
//                }

            }/*.clickable {
                onClick()
            }*/, contentAlignment = Alignment.Center


    ) {

        if (isEffectLaunched){
            LaunchedEffect(key1 = Unit) {
                if (sum % 10 == 0) {
                    sum = 0
                    isBoxRight = 1
                    delay(200)
                    isBoxRight = 2
                    onClick(true)

                } else if (sum > 10) {
                    sum = 0
                    isBoxRight = 0
                    delay(200)
                    isBoxRight = 2
                    onClick(false)
                }
                isEffectLaunched = false
            }
        }


        Image(
            painter = painterResource(
                colorState
            ),
            contentDescription = null, modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 2.dp, // Width of the border
                    color = if (isBoxRight == 1) Color.Green else if (isBoxRight == 0) Color.Red else Color.Transparent,
                    shape = RoundedCornerShape(8.dp) // Optionally, set a shape for the border
                )
                .padding(
                    top = if (item.isClickable) {
                        0.dp
                    } else {
                        12.dp
                    }
                )
        )

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = item.name,
            color = if (colorState == R.drawable.purple_card) {
                Color.White
            } else {
                BirdColor4
            },
            textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
            fontSize = 46.sp
        )

    }
}


fun generateArray(): Array<Array<Int>> {
    val size = 45
    val array = Array(size) { Array(3) { 0 } }

    var count = 0

    for (i in 1..10) {
        for (j in 1..10) {
            for (k in 1..10) {
                if (i + j + k == 10 && count < size) {
                    array[count][0] = i
                    array[count][1] = j
                    array[count][2] = k
                    count++
                }
            }
            if (i + j == 10 && count < size) {
                array[count][0] = i
                array[count][1] = j
                array[count][2] = Random.nextInt(1, 10)
                count++
            }
        }
    }


    return array
}

@Preview
@Composable
fun previewRain10Game() {
    Rain10Game { bool, rightAnswer, total ->

    }
}

@Keep
data class RainListItem(
    var name: String,
    var height: Dp,
    var isClickable: Boolean = false,
)

 