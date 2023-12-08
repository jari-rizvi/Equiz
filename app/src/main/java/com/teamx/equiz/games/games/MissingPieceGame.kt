package com.teamx.equiz.games.games

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R


class MissingPieceGame {
}

//Missing Piece
enum class Shape {
    TRIANGLE,
    SQUARE,
    CIRCLE
}

@Composable
fun MissingPieceGameScreen(content: () -> Unit) {
    var isGameOver by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(60L) }

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
            }

            override fun onFinish() {
                isGameOver = true
            }
        }.start()
    }


    if (isGameOver) {
        content()
    }
    var score by remember { mutableStateOf(0) }
    var currentShapes by remember { mutableStateOf(generateShapes()) }
    var missingShapeIndex by remember { mutableStateOf(generateMissingShapeIndex()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFE1E1E1)),
    ) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Text(
            text = "Missing Piece",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentShapes.forEachIndexed { index, shape ->
                if (index == missingShapeIndex) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(4.dp)
                            .border(
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                } else {
                    Box(
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
                    }
                }
            }
        }

        currentShapes.forEachIndexed { index, shape ->
            Button(
                onClick = {
                    if (index == missingShapeIndex) {
                        score++
                    } else {
                        score = 0
                    }
                    currentShapes = generateShapes()
                    missingShapeIndex = generateMissingShapeIndex()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = shape.toString())
            }
        }

        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
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

private fun generateShapes(): List<Shape> {
    val shapes = Shape.values().toList()
    return shapes.shuffled()
}

private fun generateMissingShapeIndex(): Int {
    return (0 until Shape.values().size).random()
}

private fun colorForShape(shape: Shape): Color {
    return when (shape) {
        Shape.TRIANGLE -> Color.Blue
        Shape.SQUARE -> Color.Red
        Shape.CIRCLE -> Color.Green
    }
}

//Missing Piece