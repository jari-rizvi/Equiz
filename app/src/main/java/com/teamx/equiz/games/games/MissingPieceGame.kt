package com.teamx.equiz.games.games

import android.os.CountDownTimer
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose


class MissingPieceGame {
}

//Missing Piece
enum class Shape {
    FIRST_Q,
    SECOND_Q,
    THIRD_Q,
    FOUR_Q,
    FIVE_Q
}

var rightGameAnswersMiss = 1
var gameAnswersTotalMiss = 1


@Preview
@Composable
fun MissingPieceGameScreen(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { _, _, _ -> }) {
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }


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
                if (timeLeft < 5) {
                    isAlert = true
                }
            }

            override fun onFinish() {
                isGameOver = true
            }
        }.start()
    }


    if (isGameOver) {
        content(true, rightGameAnswersMiss, gameAnswersTotalMiss)
    }
    var score by remember { mutableStateOf(0) }
    var currentShapes by remember { mutableStateOf(generateShapes()) }
    var missingShapeIndex by remember { mutableStateOf(generateMissingShapeIndex()) }

    var isTimeUp by remember { mutableStateOf(false) }
    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersMiss, gameAnswersTotalMiss)
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /*    Text(
                        text = "Missing Piece",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )*/

                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    currentShapes.forEachIndexed { index, shape ->
                        if (index == missingShapeIndex) {
                            Box(
                                modifier = Modifier
                                    .size(250.dp)
                                   /* .border(
                                        border = BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(4.dp)
                                    )*/
//                                    .background(colorForShape(shape))
                            ) {
                                Image(
                                    painter = painterForShape(shape),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()

                                )
                            }
                        } else {
                            /* Box(
                                 modifier = Modifier
                                     .size(60.dp)
                                     .padding(4.dp)
                                     .background(colorForShape(shape))

                             ) {
                                 Image(
                                     painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                     contentDescription = null,
                                     modifier = Modifier
                                         .size(150.dp)

                                 )
                             }*/
                        }
                    }
                }
                Row {


                    currentShapes.forEachIndexed { index, shape ->
                        Image(
                            painter = painterForShapeOption(shape = shape),
                            /*onClick = {
                                if (index == missingShapeIndex) {
                                    score++
                                } else {
                                    score = 0
                                }
                                currentShapes = generateShapes()
                                missingShapeIndex = generateMissingShapeIndex()
                            }*/
                            modifier = Modifier
                                .size(60.dp)
                                .padding(vertical = 8.dp)
                                .clickable(true) {
                                    if (index == missingShapeIndex) {
                                        rightGameAnswersMiss++
                                        score++
                                    } else {
                                        score = 0
                                    }
                                    gameAnswersTotalMiss++
                                    currentShapes = generateShapes()
                                    missingShapeIndex = generateMissingShapeIndex()
                                }, contentDescription = ""
                        )/* {
                        Text(text = shape.toString())
                    }*/


                    }
                }
                /* Text(
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

private fun generateShapes(): List<Shape> {
    val shapes = Shape.values().toList()
    return shapes.shuffled()
}

private fun generateMissingShapeIndex(): Int {
    return (0 until Shape.values().size).random()
}

private fun colorForShape(shape: Shape): Color {
    return when (shape) {
        Shape.FIRST_Q -> Color.Blue
        Shape.SECOND_Q -> Color.Red
        Shape.THIRD_Q -> Color.Green
        Shape.FOUR_Q -> Color.Black
        Shape.FIVE_Q -> Color.Yellow
    }
}

@Composable
private fun painterForShape(shape: Shape): Painter {
    return when (shape) {
        Shape.FIRST_Q -> painterResource(id = R.drawable.one_q_miss)
        Shape.SECOND_Q -> painterResource(id = R.drawable.two_q_miss)
        Shape.THIRD_Q -> painterResource(id = R.drawable.three_q_miss)
        Shape.FOUR_Q -> painterResource(id = R.drawable.four_q_miss)
        Shape.FIVE_Q -> painterResource(id = R.drawable.five_q_miss)
    }
}

@Composable
private fun painterForShapeOption(shape: Shape): Painter {
    return when (shape) {
        Shape.FIRST_Q -> painterResource(id = R.drawable.piece_two_q_miss)
        Shape.SECOND_Q -> painterResource(id = R.drawable.piece_three_q_miss)
        Shape.THIRD_Q -> painterResource(id = R.drawable.piece_four_q_miss)
        Shape.FOUR_Q -> painterResource(id = R.drawable.piece_one_q_miss)
        Shape.FIVE_Q -> painterResource(id = R.drawable.piece_five_q_miss)
    }
}

//Missing Piece