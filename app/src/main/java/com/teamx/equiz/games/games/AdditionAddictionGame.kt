package com.teamx.equiz.games.games


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.teamx.equiz.games.utils.RainGameObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@Composable
fun AdditionAddictionGame(content: @Composable () -> Unit) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFE1E1E1)),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
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
            content()
            AddingObjects()
            }
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.iconbg),
            contentDescription = "bg"
        )
    }
}

var arrList = ArrayList<Int>()
fun LazyListState.disableScrolling(scope: CoroutineScope) {
    scope.launch {
        scroll(scrollPriority = MutatePriority.PreventUserInput) {
            // Await indefinitely, blocking scrolls 
            awaitCancellation()
        }
    }
}

@Preview
@Composable
fun AddingObjects() {
    val state = rememberLazyListState()
    state.disableScrolling(GlobalScope)
    val leftItems = (0..(23)).map {
        RainListItem(
            height = 50.dp,
            name = "$it",
            color = Color(0xFFF44336)/*.copy(alpha = 0f)*/,
            gameObject = if (it % 5 == 0) {
                RainGameObject.THUNDER
            } else if (it % 2 == 0) {
                RainGameObject.BLANK
            } else {
                RainGameObject.DROP
            }
        )
    }
    val maxCount = 8
    val leftBoxes by remember { mutableStateOf(leftItems) }
    for (i in 0..maxCount) {
        arrList.add(i)
    }

    Log.d("123123", "AscendingObjects:arrList ${arrList.size}")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(50.dp)
                .offset(
                    y = 0.dp, x = 150.dp
                )

                .clickable(enabled = false, null, null) {},
            state = state,
            contentPadding = PaddingValues(1.dp),

            ) {

            itemsIndexed(items = leftBoxes, itemContent = { _, item ->

                AnimatedVisibility(
                    visible = true/*!deletedLeftList.contains(item)*/,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally(animationSpec = tween(durationMillis = 1000))
                ) {
                    ItemComposable(item = item) {}
                }
            })
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .size((87 * 3).dp)
//                .fillMaxSize()
            , contentAlignment = Alignment.Center
        ) {


            for (i in 0 until arrList.size) {
                val temp = arrList
                AnimatedAddingObject(i, temp[i]) {
                    arrList = temp
                    Log.d("123123", "AscendingObjects: $arrList")

                }
            }
        }
    }
}

@Composable
fun AnimatedAddingObject(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(Color.Black) }

    Box(
        modifier = Modifier
            .background(
                color = if (arrList.contains(number)) {
                    colorState
                } else {
                    colorState
                }, shape = RectangleShape
            )
            .size(85.dp)
            .offset(
                y = when (number) {
                    in 0..2 -> {
                        ((number - 1) * 90).dp
                    }

                    in 3..5 -> {
                        ((number - 3 - 1) * 90).dp
                    }

                    else -> {
                        ((number - 6 - 1) * 90).dp
                    }
                }, x = when (number) {
                    in 0..2 -> {
                        ((number * 0 - 1) * 90).dp
                    }

                    in 3..5 -> {
                        ((1 - 1) * 90).dp
                    }

                    else -> {
                        ((2 - 1) * 90).dp
                    }
                }/*(-number * 60).dp*/
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
//            enabled = arrList.contains(number)
            ) {
                onClick(itemCompared)/*
            if (colorState == Color.Transparent) {
                Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                return@clickable
            } else if (number == arrList.get(0)) {
                colorState = Color.Transparent
                onClick(itemCompared)
                Log.d("123123", "AnimatedObjectWrong1:$number ::$itemCompared ")
            } else {
                Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
            }
            */
            }

    ) {

        Text(
            text = number.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}


data class LeaderAddingListItem(
    var name: Int,
    var height: Dp,
    var color: Color,
    var gamesUID: LeaderAddingEnum,
)


enum class LeaderAddingEnum {
    FollowTheLeaderScreen, Transparent
}

@Composable
fun ItemComposable(item: RainListItem, onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(item.height)
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(95.dp))
            .background(item.color)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center


    ) {
        Text(
            text = item.name, style = MaterialTheme.typography.bodySmall
        )
    }
}