package com.teamx.equiz.games.games

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class ColorSwitchGame {}

//color switch

/*enum class GameColor(val color: Color) {
    RED(Color.Red),
    BLUE(Color.Blue),
    GREEN(Color.Green),
    YELLOW(Color.Yellow)
}

@Composable
fun ColorSwitchGameScreen() {
    var score by remember { mutableStateOf(0) }
    var currentColor by remember { mutableStateOf(generateRandomColor()) }
    var isColorMatched by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Color Switch",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(currentColor.color)
                .clickable { onColorClicked() }
        )

        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

private fun generateRandomColor(): GameColor {
    val colors = GameColor.values()
    return colors.random()
}

private fun onColorClicked() {
    // Perform color matching logic
    // Update score and currentColor state accordingly
}

@Preview
@Composable
fun PreviewColorSwitchGameScreen() {
    ColorSwitchGameScreen()
}*/


enum class GameColor(val color: Color) {
    RED(Color.Red), BLUE(Color.Blue), GREEN(Color.Green), YELLOW(Color.Yellow)
}

@Composable
fun ColorSwitchGameScreen(content: @Composable () -> Unit) {
    var score by remember { mutableStateOf(0) }
    var total by remember { mutableStateOf(0) }
    var boo by remember { mutableStateOf(false) }
    var targetColor by remember { mutableStateOf(generateRandomColor()) }
    var isColorMatched by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!boo) {
            TimerComposable({ _, _ -> boo = true }, score, total - score)
            Text(
                text = "Color Switch",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(modifier = Modifier
                .size(200.dp)
                .background(targetColor.color)
                .clickable {
                    total++
                    if (isColorMatched) {
                        score++
                        targetColor = generateRandomColor()
                        isColorMatched = false
                    }
                })

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GameColor.values().forEach { gameColor ->
                    ColorButton(color = gameColor.color, onColorSelected = { selectedColor ->
                        isColorMatched = (selectedColor == targetColor)
                    })
                }
            }

            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {

            Text(
                text = "Your Final Score is : $score",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Wrong Score is : ${total - score}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            IconButton(
                onClick = {
                    boo = false
                    score = 0
                    total = 0
                },
            ) {

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .padding(top = 13.dp)
                )
            }

        }


//        AnimatedObject()
//        ClickableColorObject()
        MovingObject()
    }
}

@Composable
fun ColorButton(color: Color, onColorSelected: (GameColor) -> Unit) {
    Box(modifier = Modifier
        .size(60.dp)
        .background(color)
        .clickable { onColorSelected(getGameColorByColor(color)) })
}

private fun generateRandomColor(): GameColor {
    val colors = GameColor.values()
    return colors.random()
}

private fun getGameColorByColor(color: Color): GameColor {
    return GameColor.values().firstOrNull { it.color == color } ?: GameColor.RED
}

/*@Preview
@Composable
fun PreviewColorSwitchGameScreen() {
    ColorSwitchGameScreen()
}*/

//color switch

@Composable
fun TimerComposable(onTimerExpired: (score: Int, wrong: Int) -> Unit, score: Int, wrong: Int) {
    val timerExpired = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        delay(5000) // Wait for 10 seconds
        timerExpired.value = true
        onTimerExpired(score, wrong)
    }

    // Render your UI here based on the timerExpired state
}


@Composable
fun AnimatedObject() {
    var position by remember { mutableStateOf(IntOffset.Zero) }

    LaunchedEffect(Unit) {
        val animSpec = tween<Float>(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = animSpec
        ) { value, _ ->
            position = IntOffset(0/*(value * 200).toInt()*/, (value * 200).toInt())
        }
    }
    Row {

        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(-position.x.dp, -position.y.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(position.x.dp, position.y.dp)
                .background(Color.Blue)
        )
    }
}

@Preview
@Composable
fun PreviewAnimatedObject() {
    AnimatedObject()
}

@Composable
fun ClickableColorObject() {
    var color by remember { mutableStateOf(Color.Blue) }
    val updatedColor = rememberUpdatedState(color)

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(updatedColor.value)
            .clickable {
                color = if (color == Color.Blue) {
                    Color.Red
                } else {
                    Color.Blue
                }
            }
    )
}

@Preview
@Composable
fun PreviewClickableColorObject() {
    MaterialTheme {
        ClickableColorObject()
    }
}


@Composable
fun MovingObject() {
    val screenWidth = 300f // Set your screen width here
    val objectSize = 50.dp

    val animatedOffsetX = animateFloatAsState(
        targetValue = screenWidth as Float,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        Modifier
            .background(Color.Red)
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .size(objectSize)
                .offset(x = animatedOffsetX.value.dp)
                .background(Color.Blue)
        )
    }
}

@Preview
@Composable
fun PreviewMovingObject() {
    MaterialTheme {
        MovingObject()
    }
}
