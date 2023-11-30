package com.teamx.equiz.games.games

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.games.ui.theme.BirdColor3
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

@Composable
fun UnfollowTheLeaderGame(content: @Composable () -> Unit) {
    content()
    AscendingObjectsU2()
}


@Composable
fun followTheLeaderPlotU2() {

}

var linkListAddedU2 = LinkedList<Int>()

@Preview
@Composable
fun AscendingObjectsU2() {
    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }

    val listAdded by remember { mutableStateOf<ArrayList<Int>>(arrayListOf()) }
//    val linkListAdded by remember { mutableStateOf<LinkedList<Int>>(LinkedList()) }

//    linkListAddedU2.clear()

    LaunchedEffect(Unit) {
        repeat(maxCount) { count ->
            delay(190L)
//            colorStateList.add(colorState)
            currentCount = count + 1

        }
    }

//    if (currentCount == maxCount) {
    val link = LinkedList<Int>()

    linkListAddedU2.forEach {
        link.push(it)
    }
    Log.d("123123", "AscendingObjects:linkListAddedabb $linkListAdded")
    linkListAddedU2.clear()
    Log.d("123123", "AscendingObjects:linkListAddedabb $link")
    if (link.size >= 1) {
        Log.d("123123", "AscendingObjects:1212 $linkListAdded")
        for (i in link) {
            linkListAddedU2.push(i)
        }
    } else {
        Log.d("123123", "AscendingObjects:12 $linkListAdded")
        for (i in 0..maxCount) {
            if (Random.nextBoolean()) {
                linkListAddedU2.push(i)
            }
        }
    }
    /*  } else if (currentCount <= 1) {
          linkListAddedU2.push(0)
      }*/
    Log.d("123123", "AscendingObjects:linkListAddedabbbbbb $linkListAdded")
//    val randInt = Random.nextInt(1, 3)
    /*    val items2 = (0 until currentCount).map {
            LeaderListItem(
                height = 90.dp,
                name = it,
                gamesUID = LeaderEnum.values()[it % 2],
                color = if (it % randInt == 0) {
                    Color(0xFF2450E7).copy(alpha = 1f)
                } else {
                    Color.Transparent
                }
            )
        }

        var items1 by remember { mutableStateOf<List<LeaderListItem>>(items2) }

        items1 = items2*/

//    Log.d("123123", "AscendingObjects: ${items1.size}")
    Log.d("123123", "AscendingObjects:linkListAdded ${linkListAddedU2.size}")
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
            iterations = 1,
            animation = tween(5000)
        )
    )
    ///

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
//            .graphicsLayer(rotationY = rotation), contentAlignment = Alignment.Center
    ) {
        /*  LazyVerticalStaggeredGrid(
              columns = StaggeredGridCells.Adaptive(90.dp),
              modifier = Modifier.size(300.dp),
              contentPadding = PaddingValues(2.dp),
              horizontalArrangement = Arrangement.Center
          ) {
              items(items1.size) {
                  LeaderGrids(items1[it], it) {

                      Log.d("123123", "AscendingObjects: $")
                      items1[it].color = Color.Transparent
                  }

              }*/
//        listAdded.clear()
        /* if (bool) {
             for (i in 0..currentCount) {
                 listAdded.add(i)
                 linkListAddedU2.push(i)
             }
         }*/
        for (i in 0..currentCount) {/* if (i == (currentCount - 1)) {
                 bool = false
             }*/

            var temp = linkListAdded
            Log.d("123123", "AscendingObjects:temp ${temp.first}")
            AnimatedObject(i, temp.first/*, colorStateList[i]*/) {

                temp.pop()
                linkListAdded = temp
                Log.d("123123", "AscendingObjects:linkListAdded $linkListAdded")/*  if (listAdded[it] == i) {

                      } else {

                      }*/
//                colorStateList[it] = Color.Transparent
            }
        }
//        }
    }
}

@Composable
fun AnimatedObjectU2(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor3) }

    Surface(
        color =
        if (linkListAddedU2.contains(number)/* % 2 == 0*/) {
            colorState
        } else {
            Color.Transparent
        }, shape = RectangleShape, modifier = Modifier
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
                    (/*-number*/2 * 70).dp
                } else if (number > 5) {
                    (/*-number*/3 * 80).dp
                } else {
                    (/*-number*/1 * 40).dp
                }/*(-number * 60).dp*/
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = if (linkListAddedU2.contains(number)/* % 2 == 0*/) {
                    true
                } else {
                    false
                }
            ) {
                if (colorState == Color.Transparent) {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                    return@clickable
                } else if (number == linkListAddedU2.first) {
                    colorState = Color.Transparent
                    onClick(itemCompared)
                    Log.d("123123", "AnimatedObjectWrong1:$number ::$itemCompared ")
                } else {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                }
            }
//            .graphicsLayer(rotationZ = rotation)

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

@Composable
fun LeaderGridsU2(item: LeaderListItem, counter: Int, onClick: (Item: Int) -> Unit) {


    /*    LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(90.dp),
            modifier = Modifier.size(300.dp),
            contentPadding = PaddingValues(2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(items1) { item ->*/

    LeaderColorBoxU2(item = item) {
        onClick(counter)
    }
//        }
//    }
}

data class LeaderListItemU2(
    var name: Int,
    var height: Dp,
    var color: Color,
    var gamesUID: LeaderEnum,
)

@Composable
fun LeaderColorBoxU2(item: LeaderListItem, onClick: (gamesUID: LeaderEnum) -> Unit) {
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

enum class LeaderEnumU2 {
    FollowTheLeaderScreen, Transparent
}