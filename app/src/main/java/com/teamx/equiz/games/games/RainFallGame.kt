package com.teamx.equiz.games.games

import android.os.CountDownTimer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose

import com.teamx.equiz.games.utils.RainGameObject
import kotlinx.coroutines.GlobalScope

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun RainFallGame(content:  (boolean:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1

    var timeLeft by remember { mutableStateOf(10L) }
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
        content(true, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
            }
        }


    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

                    BackButton(onClick = {}/*onContinueClicked*/)
                    Text(
                        text = "Training",
                        modifier = Modifier
                            .fillMaxWidth()

                            .align(alignment = Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 17.sp
                    )

                }


                rainFallDrops()

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
fun rainFallDrops() {
    val state = rememberLazyListState()
    state.disableScrolling(GlobalScope)
    var leftItems = (0..(43)).map {
        RainListItem(
            height = 110/*Random.nextInt(100, 300)*/.dp,
            name = "$it",

            color = if (it % 5 == 0) {
                Color(0xFFF44336).copy(alpha = 1f)
            } else if (it % 2 == 0) {
                Color(0xFF4CAF50).copy(alpha = 1f)
            } else {
                Color(0xFF00BCD4).copy(alpha = 1f)
            },
            gameObject = if (it % 5 == 0) {
                RainGameObject.THUNDER
            } else if (it % 2 == 0) {
                RainGameObject.BLANK
            } else {
                RainGameObject.DROP
            }
        )
    }
    var rightItems = (0..(43)).map {
        RainListItem(
            height = 110/*Random.nextInt(100, 300)*/.dp,
            name = "$it",

            color = if (it % 5 == 0) {
                Color(0xFFF44336).copy(alpha = 1f)
            } else if (it % 2 == 0) {
                Color(0xFF4CAF50).copy(alpha = 1f)
            } else {
                Color(0xFF00BCD4).copy(alpha = 1f)
            },
            gameObject = if (it % 5 == 0) {
                RainGameObject.THUNDER
            } else if (it % 2 == 0) {
                RainGameObject.BLANK
            } else {
                RainGameObject.DROP
            }
        )
    }
    leftItems = leftItems.shuffled()
    rightItems = rightItems.shuffled()

    /*rightItems =*/
    leftItems.forEachIndexed { i, t ->
        if (leftItems[i].gameObject == RainGameObject.THUNDER && rightItems[i].gameObject == RainGameObject.THUNDER) {
            leftItems.get(i).gameObject = RainGameObject.BLANK
            leftItems.get(i).color = Color(0xFF4CAF50).copy(alpha = 1f)
        }
    }
    leftItems.forEachIndexed { i, t ->
        if (leftItems[i].gameObject == RainGameObject.BLANK && rightItems[i].gameObject == RainGameObject.BLANK) {
            leftItems.get(i).gameObject = RainGameObject.DROP
            leftItems.get(i).color = Color(0xFF00BCD4).copy(alpha = 1f)
        }
    }
    leftItems.forEachIndexed { i, t ->
        if (leftItems[i].gameObject == RainGameObject.DROP && rightItems[i].gameObject == RainGameObject.DROP) {
            leftItems.get(i).gameObject = RainGameObject.THUNDER
            leftItems.get(i).color = Color(0xFFF44336).copy(alpha = 1f)
        }
    }
    var score by remember { mutableStateOf(0) }
    var boolOption by remember { mutableStateOf(false) }
    val leftBoxes by remember { mutableStateOf(leftItems) }
    val rightBoxes by remember { mutableStateOf(rightItems) }
    var leftIndexCounter by remember { mutableStateOf(0) }
    var rightIndexCounter by remember { mutableStateOf(0) }
    val deletedLeftList = remember { mutableStateListOf<RainListItem>() }
    val deletedRightList = remember { mutableStateListOf<RainListItem>() }
    val leftScrollState = rememberLazyListState()
    val rightScrollState2 = rememberLazyListState()/* val endOfListReached by remember {
         derivedStateOf {
             leftScrollState.isScrolledToEnd()
             leftScrollState.disableScrolling(GlobalScope)
         }
     }*/

//    leftScrollState.disableScrolling(GlobalScope)
//    rightScrollState2.disableScrolling(GlobalScope)
    LaunchedEffect(true) {
        rightScrollState2.scrollToItem(rightBoxes.size)
        leftScrollState.scrollToItem(leftBoxes.size)
        leftScrollState.scroll(MutatePriority.PreventUserInput, {})
        rightScrollState2.scroll(MutatePriority.PreventUserInput, {})
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
             ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom

    ) {
        Row(
            modifier = Modifier/*.fillMaxSize(*//*1f*//*)*/,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier/*.fillMaxHeight(0.9f)*/,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
//        Row() {

                LazyColumn(
//            columns = StaggeredGridCells.Adaptive(122.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .heightIn(500.dp, max = 680.dp)
                        .clickable(enabled = false, null, null, {}),
                    contentPadding = PaddingValues(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(86.dp),
                    state = leftScrollState
//        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    itemsIndexed(items = leftBoxes, itemContent = { _, item ->

                        AnimatedVisibility(
                            visible = !deletedLeftList.contains(item),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            drop(item = item) {}
                        }
                    })
                }

            }
            Column(
                modifier = Modifier/*.fillMaxHeight(0.9f)*/,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
//        Row() {

                LazyColumn(
//            columns = StaggeredGridCells.Adaptive(122.dp),
                    modifier = Modifier
                        .width(150.dp)
                        .heightIn(500.dp, max = 680.dp)
                        .clickable(enabled = false, null, null, {}),
                    contentPadding = PaddingValues(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(86.dp),
                    state = rightScrollState2
//        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(items = rightBoxes, itemContent = { _, item ->

                        AnimatedVisibility(
                            visible = !deletedRightList.contains(item),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            drop(item = item) {}
                        }
                    })
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(110.dp)
                    .clickable {
                        boolOption = true
                        val iu = leftBoxes.lastIndex - leftIndexCounter++
                        val iu2 = rightBoxes.lastIndex - rightIndexCounter++

                        if (leftBoxes[iu].gameObject == RainGameObject.DROP && (rightBoxes[iu2].gameObject == RainGameObject.THUNDER || rightBoxes[iu2].gameObject == RainGameObject.BLANK)) {
                            score++
                            deletedLeftList.add(leftBoxes[iu])
                            deletedRightList.add(rightBoxes[iu2])
                        } else if (leftBoxes[iu].gameObject == RainGameObject.BLANK && rightBoxes[iu2].gameObject == RainGameObject.THUNDER) {
//                        return@Button
                            deletedLeftList.add(leftBoxes[iu])
                            deletedRightList.add(rightBoxes[iu2])
                        } else {
                            leftIndexCounter--
                            rightIndexCounter--
//                        return@Button
//                    score--
                        }

                    }, contentAlignment = Alignment.Center
            ) {


                Image(
                    painter = painterResource(id = checkDrawableRain(boolOption)),
                    contentDescription = null,
                    modifier = Modifier.size(110.dp)

                )
            }

            /*       Box(
                       modifier = Modifier
                           .background(Color.Transparent)
                           .height(110.dp)
                           .clickable {
                               val iu = leftBoxes.lastIndex - leftIndexCounter++
                               val iu2 = rightBoxes.lastIndex - rightIndexCounter++

                               if (rightBoxes[iu2].gameObject == RainGameObject.DROP && leftBoxes[iu].gameObject == RainGameObject.DROP || (rightBoxes[iu2].gameObject == RainGameObject.BLANK && leftBoxes[iu].gameObject == RainGameObject.BLANK)) {
                                   score++
                                   deletedLeftList.add(leftBoxes[iu])
                                   deletedRightList.add(rightBoxes[iu2])
                               } else if (rightBoxes[iu2].gameObject == RainGameObject.BLANK && leftBoxes[iu].gameObject == RainGameObject.THUNDER) {
       //                    return@Button
                               } else {
       //                    score--
                               }


                           }, contentAlignment = Alignment.Center
                   ) {

                       Image(
                           painter = painterResource(id = R.drawable.umbrella_yellowrain),
                           contentDescription = null,
                           modifier = Modifier.size(110.dp)

                       )
                   }*/
            Box(
                modifier = Modifier
                    .background(Color.Transparent)

                    .height(100.dp)
                    .clickable {
                        boolOption = false
                        val iu = leftBoxes.lastIndex - leftIndexCounter++
                        val iu2 = rightBoxes.lastIndex - rightIndexCounter++

                        if (rightBoxes[iu2].gameObject == RainGameObject.DROP && (leftBoxes[iu].gameObject == RainGameObject.THUNDER || leftBoxes[iu].gameObject == RainGameObject.BLANK)) {
                            score++
                            deletedLeftList.add(leftBoxes[iu])
                            deletedRightList.add(rightBoxes[iu2])
                        } else if (rightBoxes[iu2].gameObject == RainGameObject.BLANK && leftBoxes[iu].gameObject == RainGameObject.THUNDER) {
//                    return@Button
                            deletedLeftList.add(leftBoxes[iu])
                            deletedRightList.add(rightBoxes[iu2])
                        } else {
//                    score--
                            leftIndexCounter--
                            rightIndexCounter--
                        }

                    }, contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = checkDrawableRain(!boolOption)),
                    contentDescription = null,
                    modifier = Modifier.size(110.dp)

                )
            }
        }

//        Text(text = "$score")
    }/* LaunchedEffect(endOfListReached) {
         // do your stuff
     }*/

}


@Composable
fun drop(item: RainListItem, onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(item.height)
            .padding(vertical = 16.dp)
//            .clip(RoundedCornerShape(95.dp))
//            .background(item.color)
            .clickable(enabled = false, null) {}/*.clickable {
                onClick()
            }*/, contentAlignment = Alignment.Center


    ) {
        Text(
            text = item.name, style = MaterialTheme.typography.bodySmall
        )
        Image(
            painter = painterResource(id = checkStringReturnDrawable(item.gameObject)),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
    }
}


fun checkStringReturnDrawable(str: RainGameObject): Int {
    return when (str) {
        RainGameObject.DROP -> {

            R.drawable.droprain
        }

        RainGameObject.THUNDER -> {

            R.drawable.stromrain
        }

        RainGameObject.BLANK -> {

            R.drawable.padlock_2rain
        }


        else -> {
            R.drawable.ic_launcher_background
        }
    }
}

fun checkDrawableRain(bool: Boolean): Int {

    return if (bool) {
        R.drawable.umbrella_yellowrain
    } else {
        R.drawable.umbrella_whiterain
    }
}

@Preview
@Composable
fun previewRainGame() {
    RainFallGame {bool,rightAnswer,total ->

    }
}

data class RainListItem(
    var name: String,
    var height: Dp,
    var gameObject: RainGameObject,
    var color: Color,
)