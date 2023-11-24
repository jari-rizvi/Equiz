package com.teamx.equiz.games.games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
fun MissingPieceGameScreen(content: @Composable () -> Unit) {
    var score by remember { mutableStateOf(0) }
    var currentShapes by remember { mutableStateOf(generateShapes()) }
    var missingShapeIndex by remember { mutableStateOf(generateMissingShapeIndex()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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