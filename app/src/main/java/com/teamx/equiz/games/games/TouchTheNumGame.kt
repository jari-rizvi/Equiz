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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.games.ui.theme.BirdColor1
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.BirdColor4
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random

class TouchTheNumGame {}
//Touch The Number


data class NumberBox(val number: Int, val color: Color)

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun TouchTheNumbersGameScreen(content: @Composable () -> Unit = {}) {
    var score by remember { mutableStateOf(0) }
    var boxes by remember { mutableStateOf(generateBoxes()) }
    var restart by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BirdColor4),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Score: $score", color= Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Touch the Numbers",
            color= Color.White,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        /*LazyRow(
            modifier = Modifier
                .size(300.dp)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(boxes) { index, box ->
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(64.dp)


                    .background(color = box.color)
                    .clickable {
                        updateScore(boxes, box, index) { i ->
                            score++
                            restart = true
                            val arr = ArrayList<NumberBox>()
                            boxes.forEach {
                                if (i != it.number) {
                                    arr.add(it)
                                }
                            }
                            boxes = arr
                            if (boxes.isEmpty()) {
                                restart = false
                            }
                        }
                        if (!restart) {
                            boxes = generateBoxes()
                            restart = true
                        }
                    }) {
                    Text(
                        text = box.number.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }*/

        Box(
            modifier = Modifier
                .padding(16.dp)

                .size(300.dp)
        ) {

            for (i in boxes.indices) {

                TouchAnimatedObject(i, boxes[i]/*boxes[0]*/) {iit->
                    Log.d("123123", "TouchTheNumbersGameScreen12: ")
                    updateScore(boxes, iit/*boxes[i]*//*box*/, i) { i2 ->
                        Log.d("123123", "TouchTheNumbersGameScreen122: ")

                        score++
                        restart = true
                        val arr = ArrayList<NumberBox>()
                        boxes.forEach {
                            if (i2 != it.number) {
                                arr.add(it)
                            }
                        }
                        boxes = arr
                        if (boxes.isEmpty()) {
                            restart = false
                        }
                    }
                    if (!restart) {
                        boxes = generateBoxes()
                        restart = true
                    }
                }

            }
        }


    }
}

private fun generateBoxes(): List<NumberBox> {
    val numbers = mutableListOf<Int>()
    for (i in 1..9) {
        numbers.add(i)
    }
    numbers.shuffle()

    val colors = mutableListOf<Color>()
    val colorBox = if (Random.nextBoolean()) BirdColor3 else BirdColor1
    for (i in 1..9) {
        colors.add(colorBox)
    }

    return numbers.mapIndexed { index, number ->
        NumberBox(number = number, color = colors[index])
    }
}

var score = 0
@RequiresApi(Build.VERSION_CODES.N)
private fun updateScore(boxes: List<NumberBox>, box: NumberBox, index: Int, onClickAdd: (number: Int) -> Unit) {


    // get an employee with a maximum age
    Log.d("123123", "updateScore:${box.color == BirdColor3} ")

    val Checker = if (box.color == BirdColor3) {
        boxes.minWith(Comparator.comparingInt { it.number })
    } else {
        boxes.maxWith(Comparator.comparingInt { it.number })

    }

    val selectedNumbers = mutableListOf<Int>()
    for (i in index until index + 5) {
    Log.d("123123", "updateScore:@${i} @@$index @@@${box.number}")
        if (i < box.number && box.color == BirdColor3) {
            selectedNumbers.add(i)
        } else if (i > box.number && box.color == BirdColor1) {
            selectedNumbers.add(i)
        }
    }

//
//    val sortedNumbers = selectedNumbers.sorted()
    if (Checker.number == box.number) {
        // Correct order
        score++
        onClickAdd(Checker.number)
    } else {
        // Incorrect order
//        score = 0
        score--
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun PreviewTouchTheNumbersGameScreen() {
    TouchTheNumbersGameScreen() {}
}


//Touch The Number


@Preview
@Composable
fun FollowTheLeaderGame2(content: @Composable () -> Unit={}) {
    content()
    AscendingObjects2()
}

@Composable
fun followTheLeaderPlot2() {

}

var linkListAdded32 = LinkedList<Int>()

@Preview
@Composable
fun AscendingObjects2() {
    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        repeat(maxCount) { count ->
            delay(190L)
            currentCount = count + 1
        }
    }


    val link = LinkedList<Int>()

    linkListAdded32.forEach {
        link.push(it)
    }
    Log.d("123123", "AscendingObjects:linkListAddedabb $linkListAdded")
    linkListAdded32.clear()
    Log.d("123123", "AscendingObjects:linkListAddedabb $link")
    if (link.size >= 1) {
        Log.d("123123", "AscendingObjects:1212 $linkListAdded32")
        for (i in link) {
            linkListAdded32.push(i)
        }
    } else {
        Log.d("123123", "AscendingObjects:12 $linkListAdded32")
        for (i in 0..maxCount) {
            if (Random.nextBoolean()) {
                linkListAdded32.push(i)
            }
        }
    }

    Log.d("123123", "AscendingObjects:linkListAddedabbbbbb $linkListAdded32")

    Log.d("123123", "AscendingObjects:linkListAdded ${linkListAdded32.size}")
    ///
    var rotationState by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        repeat(180) { count ->
            delay(10L)
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
    ) {

        for (i in 0..currentCount) {
            val temp = linkListAdded32
            Log.d("123123", "AscendingObjects:temp ${temp.first}")
            AnimatedObject2(i, temp.first ) {
                temp.pop()
                linkListAdded32 = temp
                Log.d("123123", "AscendingObjects:linkListAdded $linkListAdded32")


            }
        }
    }
}

@Composable
fun AnimatedObject2(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor3) }

    Surface(
        color =
        if (linkListAdded32.contains(number)/* % 2 == 0*/) {
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
                enabled = if (linkListAdded32.contains(number)/* % 2 == 0*/) {
                    true
                } else {
                    false
                }
            ) {
                if (colorState == Color.Transparent) {
                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                    return@clickable
                } else if (number == linkListAdded32.first) {
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
fun TouchAnimatedObject(number: Int, itemCompared: NumberBox, onClick: (Item: NumberBox) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor3) }

    Surface(color = itemCompared.color
        /*if (true*//*linkListAdded.contains(number)*//**//* % 2 == 0*//*) {
        colorState
    } else {
        BirdColor1
    }*/, shape = RectangleShape, modifier = Modifier
            .size(85.dp)
            .offset(
                y = if (number in 3..5) {
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
//            enabled = linkListAdded.contains(number)
            ) {
                onClick(itemCompared)
                /*   if (colorState == BirdColor1) {
                Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                return@clickable
            } else if (number == linkListAdded.first) {
                colorState = Color.Transparent
                onClick(itemCompared)
                Log.d("123123", "AnimatedObjectWrong1:$number ::$itemCompared ")
            } else {
                Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
            }*/
            }
//            .graphicsLayer(rotationZ = rotation)

    ) {

        Text(
            text = itemCompared.number.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}