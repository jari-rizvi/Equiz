package com.teamx.equiz.games.games

import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
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

@Composable
fun TouchTheNumbersGameScreen(content: @Composable () -> Unit = {}) {
    var score by remember { mutableStateOf(0) }
    var boxes by remember { mutableStateOf(generateBoxes()) }
    var restart by remember { mutableStateOf(true) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFE1E1E1)),
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Text(
                text = "Score: $score",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Touch the Numbers",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
*//*LazyRow(
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

                    .size(300.dp), contentAlignment = Alignment.Center
            ) {

                /*for (i in boxes.indices) {

                    TouchAnimatedObject(i, boxes[i]*//*boxes[0]*//*) { iit ->
                        Log.d("123123", "TouchTheNumbersGameScreen12: ")
                        updateScore(boxes, iit*//*boxes[i]*//**//*box*//*, i) { i2 ->
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

                }*/

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center

                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(3) { column ->
                                val index = row * 3 + column



                                TouchAnimatedObject(index, boxes[index]) { iit ->
                                    Log.d("123123", "TouchTheNumbersGameScreen12: ")
                                    updateScore(boxes, iit, index) { i2 ->
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


            }


    }

      Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.iconbg),
                contentDescription = "bg"
            )
//        if (isAlert) {
//            GameAlertingTime()
//        }
        }
}

private fun generateBoxes(): List<NumberBox> {
    val numbers = mutableListOf<Int>()
    for (i in 0..8) {
        numbers.add(i)
    }
    numbers.shuffle()

    val colors = mutableListOf<Color>()
    val colorBox = if (Random.nextBoolean()) BirdColor3 else BirdColor4
    for (i in 0..8) {
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



@Composable
fun PreviewTouchTheNumbersGameScreen() {
    TouchTheNumGamePlus {bool,rightAnswer,total ->}
}


//Touch The Number


@Preview
@Composable
fun TouchTheNumGamePlus(content:  (bool:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit = {bool,rightAnswer,total ->}) {

    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1
    var isTimeUp by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(10L) }

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
            Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

                BackButton(onClick = { content(false,0,0) }
                )
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

            NumAscendingObjects2()

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
fun followTheLeaderPlot2() {

}

var numLinkListAdded32 = LinkedList<Int>()


@Composable
fun NumAscendingObjects2() {

    val maxCount = 8
    var currentCount by remember { mutableStateOf(0) }

    var changable by remember { mutableStateOf(true) }

    var gameStarted by remember { mutableStateOf(false) }

    if (changable) {
//        LaunchedEffect(Unit) {
//            repeat(maxCount) { count ->
//                delay(190L)
//            }
//        }


        val link = LinkedList<Int>()

        numLinkListAdded32.forEach {
            link.push(it)
        }

        numLinkListAdded32.clear()

        Log.d("123123", "AscendingObjects:linkListAddedabb $link")
        if (link.size >= 1) {
            Log.d("123123", "AscendingObjects:1212 $numLinkListAdded32")
            for (i in link) {
                numLinkListAdded32.push(i)
            }
        } else {
            Log.d("123123", "AscendingObjects:12 $numLinkListAdded32")
            for (i in 0..maxCount) {
                if (Random.nextBoolean()) {
                    numLinkListAdded32.push(i)
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(10)

            changable = false

        }
    }


    Box(
        modifier = Modifier
            .padding(16.dp)
//            .rotate(90f)
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
//            .rotate(90f)
                .wrapContentSize()
        ) {

            for (i in 0..maxCount) {
                val temp = numLinkListAdded32
//            Log.d("123123", "AscendingObjects:temp ${temp.first}")
                NumAnimatedObject2(i, temp.first) {
                    temp.remove(it)
                    numLinkListAdded32 = temp

                    if (linkListSpin67.isEmpty()) {
                        changable = true
                    }
                    Log.d("123123", "AscendingObjects:linkListAdded $numLinkListAdded32")


                }
            }
        }


    }
}

@Composable
fun NumAnimatedObject2(number: Int, itemCompared: Int, onClick: (Item: Int) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor4) }

    Surface(
        color = if (numLinkListAdded32.contains(number)) {
            colorState = BirdColor4
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
                enabled = numLinkListAdded32.contains(number)
            ) {
                if (colorState == Color.Transparent) {

                    Log.d("123123", "AnimatedObjectWrong2:$number ::$itemCompared ")
                    return@clickable
                } else if (number == numLinkListAdded32.first) {
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
            modifier = Modifier/*.rotate(-90f)*/,
            text = (number + 1).toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (numLinkListAdded32.contains(number)) {
                Color.White
            } else Color.Transparent,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}


@Composable
fun TouchAnimatedObject(number: Int, itemCompared: NumberBox, onClick: (Item: NumberBox) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(BirdColor3) }

    Box(modifier = Modifier
        .padding(6.dp)
        .background(
            color = itemCompared.color, shape = RoundedCornerShape(6.dp)
        )
        .size(85.dp)/*.offset(
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
            )*/
        .clip(RoundedCornerShape(6.dp))

        .clickable {
            onClick(itemCompared)/*   if (colorState == BirdColor1) {
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
        , contentAlignment = Alignment.Center) {

        Text(
            text = itemCompared.number.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}


