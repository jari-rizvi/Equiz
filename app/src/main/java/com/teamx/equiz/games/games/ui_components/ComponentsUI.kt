package com.teamx.equiz.games.games.ui_components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.ui.theme.BirdColor1
import com.teamx.equiz.ui.theme.BirdColor2
import com.teamx.equiz.ui.theme.toolbarUnique
import kotlinx.coroutines.delay


class ComponentsUI {

    @Preview
    @Composable
    fun CardButtonComp(onClick: () -> Unit = {}) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .padding(2.dp)
                .width(140.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)

                .clickable(enabled = false, null) {
                    onClick()
                },

            contentAlignment = Alignment.Center


        ) {
            Text(
                modifier = Modifier.wrapContentSize(), textAlign = TextAlign.Center,

                text = "Number", style = MaterialTheme.typography.bodyMedium
            )

        }


    }

    @Preview
    @Composable
    fun CardSquareComp(onClick: () -> Unit = {}) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .clickable(enabled = false, null) {
                    onClick()
                }, contentAlignment = Alignment.Center


        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
                text = "Number",
                style = MaterialTheme.typography.bodyMedium
            )

        }


    }

    @Preview
    @Composable
    fun CardButtonRoundComp(onClick: () -> Unit = {}) {

        Box(
            modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(toolbarUnique)
                .clickable(enabled = false, null) {
                    onClick()
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
                text = "Number",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )

        }


    }

    @Preview
    @Composable
    fun DiagonalGradientCard(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
                .width(70.dp)
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Green, Color.Red
                        )
                    )
                )
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard2(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
                .padding(2.dp)
                .width(70.dp)
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Blue, // Top color
                            Color.Red   // Bottom color
                        ), startY = 0f, endY = 100f
                    )
                )
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard3(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Blue, // Top color
                                Color.Red   // Bottom color
                            )
                        ),
//                        topLeft = topLeft,
                        size = size
                    )
                }
                content()
            }
        }
    }

    @Preview
    @Composable
    fun gameAlertingTime() {

        val animationProgress = remember { Animatable(0f) }
        LaunchedEffect(true) {
            animationProgress.animateTo(
                targetValue = .75f/*1f*/,

                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
                )
            )
        }

        val alpha = animationProgress.value * 255
        val color = android.graphics.Color.argb(
            alpha.toInt(), (toolbarUnique.copy(alpha = 0.7f).red.toInt() + alpha).toInt(), 0,0
        )

        Box(
            modifier = Modifier.fillMaxSize()

        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
                    Canvas(
                        modifier = Modifier
                            .width(70.dp)
                            .fillMaxHeight()
                    ) {
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/, // Top color
                                    Color.Transparent   // Bottom color
                                )
                            )
                        )
                    }


                    Canvas(
                        modifier = Modifier
                            .width(70.dp)
                            .fillMaxHeight()
                    ) {
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent, // Top color
                                    Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/   // Bottom color
                                )
                            )
                        )
                    }

                }
                Column(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
                    Canvas(
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()

                    ) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/, // Top color
                                    Color.Transparent   // Bottom color
                                )
                            )
                        )
                    }

                    Canvas(
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                    ) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, // Top color
                                    Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/   // Bottom color
                                )
                            )
                        )
                    }
                }


            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard6(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Blue, // Start color
                                Color.Red   // End color
                            ), start = Offset(0f, size.height), end = Offset(size.width, 0f)
                        )
                    )
                }
                content()
            }
        }
    }

    @Preview
    @Composable
    fun RoundedGradientCard(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
                .padding(2.dp)
                .width(70.dp)
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard7(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
                .padding(2.dp)
                .width(75.dp)
                .height(110.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path()
                    path.moveTo(0f, size.height)
                    path.lineTo(size.width, 0f)
                    drawPath(
                        path = path, brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Blue, // Start color
                                Color.Red   // End color
                            ), start = Offset.Zero, end = Offset(size.width, size.height)
                        )
                    )

                    // Draw the diagonal line
                    drawLine(
                        color = Color.Black,
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    )

                    // Draw the triangle on the left side
                    drawPath(
                        path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(0f, size.height)
                            lineTo(size.width, size.height)
                            close()
                        }, brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Green, // Start color on the left
                                Color.Yellow   // End color on the left
                            ), start = Offset.Zero, end = Offset(size.width, size.height)
                        )
                    )

                    // Draw the triangle on the right side
                    drawPath(
                        path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            close()
                        }, brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red, // Start color on the right
                                Color.Green   // End color on the right
                            ), start = Offset.Zero, end = Offset(size.width, size.height)
                        )
                    )
                }
                content()
            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard9(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Green, // Start color on one side
                                Color.Yellow   // End color on one side
                            ), start = Offset(0f, size.height), end = Offset(size.width, 0f)
                        )
                    )

                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red, // Start color on the other side
                                Color.Green   // End color on the other side
                            ), start = Offset(size.width, size.height), end = Offset(0f, 0f)
                        )
                    )
                }
                content()
            }
        }
    }

    @Preview
    @Composable
    fun DiagonalGradientCard11(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Draw the first color
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Green, // Start color on one side
                                Color.Yellow   // End color on one side
                            ), start = Offset(0f, size.height), end = Offset(size.width, 0f)
                        )
                    )

                    // Draw the diagonal line
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, 0f),
                        strokeWidth = 4f // Adjust the width of the line if needed
                    )

                    // Draw the second color
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red, // Start color on the other side
                                Color.Green   // End color on the other side
                            ), start = Offset(0f, 0f), end = Offset(size.width, size.height)
                        )
                    )
                }
                content()
            }
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    MaterialTheme {
        ComponentsUI().CardButtonRoundComp() {
            Log.d("123123", "CardPreview: ")
        }
//        DiagonalGradientCard7 {
//
//        }
    }
}

@Preview
@Composable
fun ToolbarCompose(
    title: String = "Training",

    onClick: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = toolbarUnique)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "BackButton",
                tint = Color.White,
                modifier = Modifier.clickable(true) {
                    onClick()
                }

            )
            Text(
                modifier = Modifier.fillMaxSize(),
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

    }
}


@Preview
@Composable
fun StartUpDialogCompose(
    title: String = "Concentration",
    exampleTxt: String = "Same Shape: Swipe the same direction Different Shape: Opposite direction",
    onClick: (int: Int) -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.concentration_instruction_c)
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.LightGray),
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = toolbarUnique)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackButton",
                    tint = Color.White,
                    modifier = Modifier.clickable(true) {
                        onClick(1)
                    }

                )
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

        }

        Column(
            modifier = Modifier

                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp),
                text = "How to Play",
                color = Color(0xFF323232),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(448.dp)
                    .padding(20.dp)
                    .background(color = Color.LightGray)
                    .border(BorderStroke(6.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                    .background(color = Color.LightGray, shape = RoundedCornerShape(14.dp)),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(448.dp)
                        .border(BorderStroke(6.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 12.dp),
                            text = title,
                            color = toolbarUnique,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Image(painter = painter/*Icons.Default.ArrowBackIos*/,
                            contentDescription = "BackButton",

                            modifier = Modifier
                                .padding(top = 12.dp)
                                .clickable(true) {
                                    onClick(1)
                                }

                        )
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 12.dp),
                            text = exampleTxt,
                            color = Color(0xFF323232),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
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

            Box(
                modifier = Modifier
                    .padding(10.dp)

                    .height(40.dp)
                    .width(140.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(toolbarUnique)
                    .clickable(enabled = true) {
                        onClick(2)
                    }, contentAlignment = Alignment.Center

            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    text = "Start",
                    color = Color.White,

                    style = MaterialTheme.typography.bodyMedium
                )

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
@Preview
@Composable
fun StartUpDialogCompose2(
    title: String = "Concentration",
    exampleTxt: String = "Same Shape: Swipe the same direction Different Shape: Opposite direction",
    onClick: () -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.concentration_instruction_c)
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White),
    ) {

        Column(
            modifier = Modifier

                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(448.dp)
                    .padding(20.dp)
                    .background(color = Color.White)
                    .border(BorderStroke(6.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(14.dp)),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(448.dp)
                        .border(BorderStroke(6.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 12.dp),
                            text = title,
                            color = toolbarUnique,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Image(painter = painter/*Icons.Default.ArrowBackIos*/,
                            contentDescription = "BackButton",

                            modifier = Modifier
                                .padding(top = 12.dp)
                                .clickable(true) {
                                    onClick()
                                }

                        )
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 12.dp),
                            text = exampleTxt,
                            color = Color(0xFF323232),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
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

            Box(
                modifier = Modifier
                    .padding(10.dp)

                    .height(40.dp)
                    .width(140.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(toolbarUnique)
                    .clickable(enabled = true) {
                        onClick()
                    }, contentAlignment = Alignment.Center

            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    text = "Start",
                    color = Color.White,

                    style = MaterialTheme.typography.bodyMedium
                )

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

@Preview
@Composable
fun TimeUpDialogCompose(
    title: String = "Concentration",
    onClick: (bool: Boolean) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(200)
        onClick(true)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White),
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = toolbarUnique)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "BackButton",
                    tint = Color.White,
                    modifier = Modifier.clickable(true) {
                        onClick(false)
                    }

                )
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.times_up),
            contentDescription = "bg"
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.iconbg),
            contentDescription = "bg"
        )
    }
}

@Preview
@Composable
fun CircularProgressBar() {
    var progress by remember { mutableStateOf(0.5f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .size(100.dp)
                .rotate(180f), color = Color.White // Rotate to start from the top,


        )
    }
}

@Preview
@Composable
fun DualColorCircularProgressBar(progressVal:Float=0.5f) {
    var progress by remember { mutableStateOf(progressVal) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Circular progress indicator for the progressed part


        // Circular progress indicator for the remaining part
        CircularProgressIndicator(
            progress = 1f, // Remaining progress
            modifier = Modifier
                .size(140.dp)
                .rotate(180f)
                .background(Color.Transparent), // Rotate to start from the top
            color = BirdColor2, strokeWidth = 28.dp

        )
        CircularProgressIndicator(
            progress = progress + .25f,
            modifier = Modifier
                .size(140.dp)
                .rotate(90f)
                .background(Color.Transparent), // Rotate to start from the top
            color = BirdColor1,
            strokeWidth = 28.dp
        )
    }
}


@Preview
@Composable
fun GameAlertingTime() {

    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(true) {
        animationProgress.animateTo(
            targetValue = .75f/*1f*/,

            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
            )
        )
    }

    val alpha = animationProgress.value * 255
    val color = android.graphics.Color.argb(
        alpha.toInt(), (toolbarUnique.copy(alpha = 0.7f).red.toInt() + alpha).toInt(), 0,0
    )

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
                Canvas(
                    modifier = Modifier
                        .width(70.dp)
                        .fillMaxHeight()
                ) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/, // Top color
                                Color.Transparent   // Bottom color
                            )
                        )
                    )
                }


                Canvas(
                    modifier = Modifier
                        .width(70.dp)
                        .fillMaxHeight()
                ) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent, // Top color
                                Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/   // Bottom color
                            )
                        )
                    )
                }

            }
            Column(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
                Canvas(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()

                ) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/, // Top color
                                Color.Transparent   // Bottom color
                            )
                        )
                    )
                }

                Canvas(
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                ) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, // Top color
                                Color(color)/*toolbarUnique.copy(alpha = 0.7f)*/   // Bottom color
                            )
                        )
                    )
                }
            }


        }
    }
}