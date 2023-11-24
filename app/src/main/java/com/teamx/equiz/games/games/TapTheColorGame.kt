package com.teamx.equiz.games.games

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TapTheColorGame(content: @Composable () -> Unit) {
    content()
    TouchTheNumbersGameScreenTap()
}

var linkListAddedTap = LinkedList<Int>()

@Preview
@Composable
fun AscendingObjectsTap() {
    val maxCount = 8
    var currentCount by remember { mutableStateOf<Int>(0) }

    val listAdded by remember { mutableStateOf<ArrayList<Int>>(arrayListOf()) }
//    val linkListAddedTap by remember { mutableStateOf<LinkedList<Int>>(LinkedList()) }

//    val colorState by remember { mutableStateOf<Color>(Color.Black) }
//    val colorStateList by remember { mutableStateOf<ArrayList<Color>>(arrayListOf(Color.Black)) }
//    linkListAddedTap.clear()

    LaunchedEffect(Unit) {
        repeat(maxCount) { count ->
            delay(190L)
//            colorStateList.add(colorState)
            currentCount = count + 1

        }
    }

//    if (currentCount == maxCount) {
    val link = LinkedList<Int>()

    linkListAddedTap.forEach {
        link.push(it)
    }
    Log.d("123123", "AscendingObjectsTap:linkListAddedTapabb $linkListAddedTap")
    linkListAddedTap.clear()
    Log.d("123123", "AscendingObjectsTap:linkListAddedTapabb $link")
    if (link.size >= 1) {
        Log.d("123123", "AscendingObjectsTap:1212 $linkListAddedTap")
        for (i in link) {
            linkListAddedTap.push(i)
        }
    } else {
        Log.d("123123", "AscendingObjectsTap:12 $linkListAddedTap")
        for (i in 0..maxCount) {
            if (Random.nextBoolean()) {
                linkListAddedTap.push(i)
            }
        }
    }
    /*  } else if (currentCount <= 1) {
          linkListAddedTap.push(0)
      }*/
    Log.d("123123", "AscendingObjectsTap:linkListAddedTapabbbbbb $linkListAddedTap")
//    val randInt = Random.nextInt(1, 3)
    /*    val items2 = (0 until currentCount).map {
            LeaderListItemTap(
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

        var items1 by remember { mutableStateOf<List<LeaderListItemTap>>(items2) }

        items1 = items2*/

//    Log.d("123123", "AscendingObjectsTap: ${items1.size}")
    Log.d("123123", "AscendingObjectsTap:linkListAddedTap ${linkListAddedTap.size}")
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

                      Log.d("123123", "AscendingObjectsTap: $")
                      items1[it].color = Color.Transparent
                  }

              }*/
//        listAdded.clear()
        /* if (bool) {
             for (i in 0..currentCount) {
                 listAdded.add(i)
                 linkListAddedTap.push(i)
             }
         }*/
        for (i in 0..currentCount) {/* if (i == (currentCount - 1)) {
                 bool = false
             }*/

            var temp = linkListAddedTap
            Log.d("123123", "AscendingObjectsTap:temp ${temp.first}")
            AnimatedObjectTap(i, temp.first/*, colorStateList[i]*/) {

                temp.pop()
                linkListAddedTap = temp
                Log.d("123123", "AscendingObjectsTap:linkListAddedTap $linkListAddedTap")/*  if (listAdded[it] == i) {

                      } else {

                      }*/
//                colorStateList[it] = Color.Transparent
            }
        }
//        }
    }
}

@Composable
fun AnimatedObjectTap(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(Color.Black) }

    Surface(
        color =
        if (linkListAddedTap.contains(number)/* % 2 == 0*/) {
            colorState
        } else {
            Color.Transparent
        }, shape = RectangleShape, modifier = Modifier
            .size(85.dp)
            .offset(
                y = /*(-number * 60).dp*//* if ((number *//*+ 1*//*) % 3 == 0) {
                    (number * 90).dp
                } else {
                    ((number % 3) * 90).dp
                }*/

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
                enabled = linkListAddedTap.contains(number)
            ) {
                if (colorState == Color.Transparent) {
                    Log.d("123123", "AnimatedObjectTapWrong2:$number ::$itemCompared ")
                    return@clickable
                } else if (number == linkListAddedTap.first) {
                    colorState = Color.Transparent
                    onClick(itemCompared)
                    Log.d("123123", "AnimatedObjectTapWrong1:$number ::$itemCompared ")
                } else {
                    Log.d("123123", "AnimatedObjectTapWrong2:$number ::$itemCompared ")
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

data class LeaderListItemTap(
    var name: Int,
    var height: Dp,
    var color: Color,
    var gamesUID: LeaderEnum,
)

@Composable
fun LeaderColorBox(item: LeaderListItemTap, onClick: (gamesUID: LeaderEnum) -> Unit) {
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

//tap the color
data class NumberBoxTap(val number: Int, val color: Color, var boolCheck: Boolean = false)

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TouchTheNumbersGameScreenTap() {
    var score by remember { mutableStateOf(0) }
    var boxes by remember { mutableStateOf(generateBoxesTap()) }
    var arrTap by remember { mutableStateOf(arrayListOf<Int>()) }
    var boxesTemp by remember { mutableStateOf(boxes.sortedWith(Comparator.comparingInt { it.number })) }
    var restart by remember { mutableStateOf(false) }
    var timerCheck by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = timerCheck) {
        delay(2500)
        timerCheck = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tap the Color",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(boxesTemp) { index, box ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .size(19.dp)
                            .background(color = checkNumberReturnColor(box.number))
                            .clickable {
                                /*  updateScoreTap(boxes, box, index) { i ->
                                      score++
                                      restart = true
                                      val arr = ArrayList<NumberBoxTap>()
                                      boxes.forEach {
                                          if (i != it.number) {
                                              arr.add(it)
                                          }
                                      }
                                      boxes = arr
                                      if (boxes.isEmpty()) {
                                          restart = false
                                      }
                                  }*//* val Checker = boxes.minWith(Comparator.comparingInt { it.number })

                             if (Checker.number == box.number && !arrTap.contains(box.number)) {
                                 arrTap.add(box.number)

                                 // Correct order
                                 scoreTap++
                             } else if (!arrTap.contains(box.number)) {
                                 val last = arrTap.maxWith(Comparator.comparingInt { it })
                                 if (last < box.number) {
                                     arrTap.add(box.number)
                                 }

                                 scoreTap++
                             }*/


                                if (!restart) {
                                    boxes = generateBoxesTap()
                                    restart = true
                                }
                            }) {
                        Text(
                            text = ""/*box.number.toString()*/,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Box() {
                if (timerCheck) {


                    LazyRow(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        itemsIndexed(boxes) { index, box ->
                            Box(modifier = Modifier
                                .padding(2.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .size(64.dp)
                                .background(
                                    color = checkNumberReturnColor(box.number)

                                )
                                .clickable {

                                }) {
                                Text(
                                    text = ""/*box.number.toString()*/,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                } else {


                    LazyRow(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        itemsIndexed(boxes) { index, box ->
                            Box(modifier = Modifier
                                .padding(2.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .size(64.dp)
                                .background(
                                    color = if (arrTap.contains(box.number) || box.boolCheck || restart) {
                                        restart = false
                                        Log.d("123123", "TouchTheNumbersGameScreenTap1: ")
                                        checkNumberReturnColor(box.number)
                                    } else {
                                        restart = false
                                        Log.d("123123", "TouchTheNumbersGameScreenTap2: ")
                                        Color.DarkGray
                                    }

                                )
                                .clickable {
                                    /* updateScoreTap(boxesTemp, box, index) { i ->

                                 score++
                                 restart = true
                                 val arr = ArrayList<NumberBoxTap>()
                                 boxesTemp.forEach {
                                     if (i != it.number) {
                                         arr.add(it)
                                     } else {
                                         it.boolCheck = true
                                         boxesTemp.get(index).boolCheck = true
                                         box.boolCheck = true
                                         arr.add(it)
                                     }
                                 }
                                 boxesTemp = arr
                                 if (boxesTemp.isEmpty()) {
                                     restart = false
                                 }
                             }*/
                                    val Checker =
                                        boxes.minWith(Comparator.comparingInt { it.number })

                                    if (Checker.number == box.number && !arrTap.contains(box.number)) {
                                        arrTap.add(box.number)
                                        arrTap = arrTap
                                        box.boolCheck = true
                                        // Correct order
                                        score++
                                    } else if (!arrTap.contains(box.number)) {
                                        if (arrTap.isNotEmpty()) {
                                            val last =
                                                arrTap.maxWith(Comparator.comparingInt { it })
                                            if (last < box.number && box.number < (last + 2)) {
                                                arrTap.add(box.number)
                                                arrTap = arrTap
                                                box.boolCheck = true
                                                score++
                                            }
                                        }

                                    } else {

                                    }
                                    restart = true

                                    if (arrTap.size == boxes.size) {
                                        boxes = generateBoxesTap()
                                        boxesTemp = boxes.sortedWith(Comparator.comparingInt { it.number })
                                        arrTap.clear()
                                        restart = true
                                        timerCheck = true
                                    }




                                    Log.d("123123", "TouchTheNumbersGameScreenTap: ")
                                }) {
                                Text(
                                    text = ""/*box.number.toString()*/,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

private fun generateBoxesTap(): List<NumberBoxTap> {
    val numbers = mutableListOf<Int>()
    for (i in 1..Random.nextInt(2, 6)) {
        numbers.add(i)
    }
    numbers.shuffle()

    val colors = mutableListOf<Color>()
    val colorBox = if (Random.nextBoolean() || true) Color.Green else Color.Red
    for (i in 1..5) {
        colors.add(colorBox)
    }

    return numbers.mapIndexed { index, number ->

        NumberBoxTap(number = number, color = colors[index])
    }
}

var scoreTap = 0
var arrTap = ArrayList<Int>()

@RequiresApi(Build.VERSION_CODES.N)
private fun updateScoreTap(
    boxes: List<NumberBoxTap>, box: NumberBoxTap, index: Int, onClickAdd: (number: Int) -> Unit
) {


    // get an employee with a maximum age


//    val Checker = /*if (box.color == Color.Green) {*/
    val Checker = boxes.minWith(Comparator.comparingInt { it.number })

    /* } else {
         boxes.maxWith(Comparator.comparingInt { it.number })

     }*/


    if (Checker.number == box.number) {
        arrTap.add(Checker.number)

        // Correct order
        scoreTap++
        onClickAdd(Checker.number)
    } else if (!arrTap.contains(Checker.number)) {
        val last = arrTap.maxWith(Comparator.comparingInt { it })
        if (last > Checker.number) {
            arrTap.add(last)
        }

        onClickAdd(Checker.number)
        scoreTap--
    }
}

fun checkNumberReturnColor(index: Int): Color {
    return when (index) {
        1 -> {
            Color.Cyan
        }

        2 -> {
            Color.Green
        }

        3 -> {
            Color.Yellow
        }

        4 -> {
            Color.LightGray
        }

        5 -> {
            Color.Magenta
        }

        else -> {
            Color.Cyan
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun PreviewTouchTheNumbersGameScreenTap() {
    TouchTheNumbersGameScreenTap()
}


//
