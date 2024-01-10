package com.teamx.equiz.games.games


import android.os.CountDownTimer
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.DeceptionBlack
import com.teamx.equiz.games.ui.theme.DeceptionPink
import com.teamx.equiz.games.ui.theme.DeceptionPurple
import com.teamx.equiz.games.ui.theme.DeceptionYellow
import com.teamx.equiz.ui.theme.DeceptionBlue
import kotlin.random.Random

@Keep
data class ColorBox(val colorName: ColorBundle, val color: Color)

enum class ColorBundle {
    Green, Blue, Black, Purple/*, PINK*/
}


var rightGameAnswersColor = 1
var totalGameAnswersColor = 1




@Composable
fun TouchTheColorGameScreen(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {
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
        content(true, rightGameAnswersColor, totalGameAnswersColor)
    }
    var score by remember { mutableStateOf(0) }
    var spanCount by remember { mutableStateOf(2) }
    var asGridCells by remember { mutableStateOf(GridCells.Fixed(2)) }
    var boxes by remember { mutableStateOf(generateBoxes()) }
    var restart by remember { mutableStateOf(true) }

    if (isGameOver) {


        content(true, rightGameAnswersColor, (totalGameAnswersColor))

    }
    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersColor, (totalGameAnswersColor))
            }
        }


    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {

            Box(modifier = Modifier
                .height(48.dp)
                .background(color = Color(0xFF9F81CA)),contentAlignment =Alignment.CenterStart)  {

                BackButton(onClick = { content(false,0,0) }
                )
                Text(
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth()

                        ,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }

            Column(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {


                Text(
                    text = "",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth().padding(horizontal=22.dp),
                    verticalArrangement = Arrangement.Center,
                    columns = asGridCells,
                ) {


                    itemsIndexed(boxes) { index, box ->


                        Box(
                            modifier = Modifier
                                .padding(14.dp)
                                .clip(
                                    RoundedCornerShape(9.dp)
                                )
                                .size(140.dp)

                                .background(color = box.color)


                                .clickable {
                                    totalGameAnswersColor++
                                    updateScore(boxes, box, index) { i, bool ->
                                        rightGameAnswersColor++
                                        score++
                                        restart = true
                                        val arr = ArrayList<ColorBox>()
                                        boxes.forEach {
                                            if (i != it.colorName) {
                                                arr.add(it)
                                            }
                                        }
                                        boxes = arr
                                        if (bool) {
                                            restart = false
                                        }

                                    }
                                    if (!restart) {
                                        boxes = generateBoxes()
                                        restart = true
                                    }
                                },


                            ) {

                            Text(

                                modifier = Modifier.align(Alignment.Center),
                                color = if (box.colorName.toString()
                                        .equals(ColorBundle.Blue.toString()) && box.color == Color.White
                                ) {
                                    DeceptionBlack
                                } else if (box.color == Color.White) {
                                    DeceptionBlack
                                } else {

                                    Color.White
                                },
                                text = box.colorName.toString(),fontSize = 28.sp,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            /*}*/
                        }
                    }

                }



                /*Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )*/
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

var deceptionNum: ColorBundle? = null
private fun generateBoxes(): List<ColorBox> {
    val numbers = mutableListOf<ColorBundle>()
    for (i in ColorBundle.values()) {
        numbers.add(i)
    }
    numbers.shuffle()

    val deceptionNumber = Random.nextInt(1, 3)

    var colorBox = Color.Blue
    val colors = mutableListOf<Color>()
    for (i in numbers.indices) {
        if (deceptionNumber == i) {
            deceptionNum = numbers[deceptionNumber]
            colorBox = when (numbers[deceptionNumber]) {

                ColorBundle.Green -> {
                    DeceptionBlack
                }

                ColorBundle.Blue -> {
                    DeceptionYellow
                }

                ColorBundle.Black -> {
                    Color.White

                }

                ColorBundle.Purple -> {
                    DeceptionPink
                }

//                ColorBundle.PINK -> {
//                    DeceptionPurple
//                }

                else -> {
                    DeceptionPurple
                }
            }
        } else {
            colorBox = when (numbers[i]) {

                ColorBundle.Green -> {
                    DeceptionYellow
                }

                ColorBundle.Blue -> {
                    DeceptionBlue
                }

                ColorBundle.Black -> {
                    DeceptionBlack

                }

                ColorBundle.Purple -> {
                    DeceptionPurple
                }

                else -> {
                    DeceptionPurple
                }
            }
        }




        colors.add(colorBox)
    }

    return numbers.mapIndexed { index, number ->
        ColorBox(colorName = number, color = colors[index])
    }
}

var colorScore = 0
private fun updateScore(
    boxes: List<ColorBox>,
    box: ColorBox,
    index: Int,
    onClickAdd: (number: ColorBundle, bool: Boolean) -> Unit
) {



    val selectedColors = mutableListOf<ColorBundle>()

    for (i in ColorBundle.values()) {
        if (i != box.colorName && box.color == Color.Green) {
            selectedColors.add(i)
        } else if (i > box.colorName && box.color == Color.Red) {
            selectedColors.add(i)
        }
    }

//
//    val sortedColors = selectedColors.sorted()
    if (deceptionNum == box.colorName) {
        // Correct order
        score++
        onClickAdd(box.colorName, true)
    } else {
        // Incorrect order
//        score = 0
        score--
    }
}


@Preview
@Composable
fun PreviewTouchTheColorGameScreen() {
    TouchTheColorGameScreen() {bool,rightAnswer,total ->}
}
