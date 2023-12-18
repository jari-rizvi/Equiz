package com.teamx.equiz.games.games

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.DeceptionBlack
import com.teamx.equiz.games.ui.theme.DeceptionPink
import com.teamx.equiz.games.ui.theme.DeceptionPurple
import com.teamx.equiz.games.ui.theme.DeceptionYellow
import kotlin.random.Random


data class ShapeBox(val colorName: ShapeBundle, val color: Color)

enum class ShapeBundle {
    YELLOW_HEXAGON, WHITE_TRIANGLE, BLACK_SQUARE, PURPLE_CIRCLE/*, PINK*/
}


@Composable
fun TouchTheShapesGameScreen(content: () -> Unit) {
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
//                isGameOver = true
            }
        }.start()
    }


    if (isGameOver) {
        content()
    }
    var score by remember { mutableStateOf(0) }
    var spanCount by remember { mutableStateOf(2) }
    var asGridCells by remember { mutableStateOf(GridCells.Fixed(2)) }
    var boxes by remember { mutableStateOf(generateBoxes()) }
    var restart by remember { mutableStateOf(true) }
//    var strName by remember { mutableStateOf("") }
//    var strName = ""
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BirdColor3
            ),

        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Shapes Deception",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.width(250.dp),
            verticalArrangement = Arrangement.Center,
            columns = asGridCells,
        ) {


            itemsIndexed(boxes) { index, box ->


                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .clip(
                            RoundedCornerShape(19.dp)
                        )
                        .height(80.dp)
                        .width(67.dp)
                        .background(color = Color.Transparent/*box.color*/)
                        .border(BorderStroke(1.dp, Color.Transparent))


                        .clickable {
                            updateScore(boxes, box, index) { i, bool ->
                                score++
                                restart = true
                                val arr = ArrayList<ShapeBox>()
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
                        }, contentAlignment = Alignment.Center


                ) {

                    /*   Text(

                           modifier = Modifier.align(Alignment.Center),
                           color = if (box.colorName.toString()
                                   .equals(ShapeBundle.WHITE_TRIANGLE.toString()) && box.color == Color.White
                           ) {
                               DeceptionBlack
                           } else if (box.color == Color.White) {
                               DeceptionBlack
                           } else {

                               Color.White
                           },
                           text = box.colorName.toString(),
                           style = MaterialTheme.typography.bodyLarge,
                       )*//*}*/


                    Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.size(65.dp), onDraw = {

                            when (box.colorName) {


                                ShapeBundle.YELLOW_HEXAGON -> {
                                    DeceptionYellow


                                    if (deceptionNumShape == ShapeBundle.YELLOW_HEXAGON) {
                                        drawHexagon(color = box.color)

                                    } else {
                                        drawCircle(color = box.color)
                                    }


                                }

                                ShapeBundle.WHITE_TRIANGLE -> {
                                    Color.White
//                                    drawTriangle(color = box.color)

                                    if (deceptionNumShape == ShapeBundle.YELLOW_HEXAGON) {
                                        drawTriangle(color = box.color)
                                    } else {
                                        drawSquare(color = box.color)
                                    }
                                }

                                ShapeBundle.BLACK_SQUARE -> {
                                    DeceptionBlack
//                                    drawSquare(color = box.color)

                                    if (deceptionNumShape == ShapeBundle.YELLOW_HEXAGON) {
                                        drawSquare(color = box.color)
                                    } else {
                                        drawTriangle(color = box.color)
                                    }

                                }

                                ShapeBundle.PURPLE_CIRCLE -> {
                                    DeceptionPurple
//                                    drawCircle(color = box.color)


                                    if (deceptionNumShape == ShapeBundle.YELLOW_HEXAGON) {
                                        drawCircle(color = box.color)
                                    } else {
                                        drawHexagon(color = box.color)
                                    }
                                }

                                else -> {
                                    DeceptionPurple
//                                    drawHexagon(color = box.color)


                                    if (deceptionNumShape == ShapeBundle.YELLOW_HEXAGON) {
                                        drawHexagon(color = box.color)
                                    } else {
                                        drawCircle(color = box.color)
                                    }

                                }
                            }


                        })

                        Text(

                            modifier = Modifier.align(Alignment.Center),
                            color = if (box.colorName.toString()
                                    .equals(ShapeBundle.WHITE_TRIANGLE.toString()) && box.color == Color.White
                            ) {
                                DeceptionBlack
                            } else if (box.color == Color.White) {
                                DeceptionBlack
                            } else {

                                Color.White
                            },
                            text =     when (box.colorName) {


                                ShapeBundle.YELLOW_HEXAGON -> {
                                    DeceptionYellow

                                    "Hexagon"



                                }

                                ShapeBundle.WHITE_TRIANGLE -> {
                                    Color.White
//                                    drawTriangle(color = box.color)
                                     "Triangle"

                                }

                                ShapeBundle.BLACK_SQUARE -> {
                                    DeceptionBlack
//                                    drawSquare(color = box.color)
                                    "Square"


                                }

                                ShapeBundle.PURPLE_CIRCLE -> {
                                    DeceptionPurple
//                                    drawCircle(color = box.color)
                                   "Circle"


                                }

                                else -> {
                                    DeceptionPurple
//                                    drawHexagon(color = box.color)
                                   "Hexagon"



                                }
                            }/*box.colorName.toString()*/,
                            style = MaterialTheme.typography.bodySmall,
                        )
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

var deceptionNumShape: ShapeBundle? = null
private fun generateBoxes(): List<ShapeBox> {
    val numbers = mutableListOf<ShapeBundle>()
    for (i in ShapeBundle.values()) {
        numbers.add(i)
    }
    numbers.shuffle()

    val deceptionNumber = Random.nextInt(1, 3)

    var colorBox = Color.White
    val colors = mutableListOf<Color>()
    for (i in numbers.indices) {
        if (deceptionNumber == i) {
            deceptionNumShape = numbers[deceptionNumber]
            colorBox = when (numbers[deceptionNumber]) {

                ShapeBundle.YELLOW_HEXAGON -> {
                    DeceptionBlack
                }

                ShapeBundle.WHITE_TRIANGLE -> {
                    DeceptionYellow
                }

                ShapeBundle.BLACK_SQUARE -> {
                    Color.White

                }

                ShapeBundle.PURPLE_CIRCLE -> {
                    DeceptionPink
                }


                else -> {
                    DeceptionPurple
                }
            }
        } else {
            colorBox = when (numbers[i]) {

                ShapeBundle.YELLOW_HEXAGON -> {
                    DeceptionYellow
                }

                ShapeBundle.WHITE_TRIANGLE -> {
                    Color.White
                }

                ShapeBundle.BLACK_SQUARE -> {
                    DeceptionBlack

                }

                ShapeBundle.PURPLE_CIRCLE -> {
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
        ShapeBox(colorName = number, color = colors[index])
    }
}

var shapeScore = 0
private fun updateScore(
    boxes: List<ShapeBox>,
    box: ShapeBox,
    index: Int,
    onClickAdd: (number: ShapeBundle, bool: Boolean) -> Unit
) {


    val selectedColors = mutableListOf<ShapeBundle>()

    for (i in ShapeBundle.values()) {
        if (i != box.colorName && box.color == Color.Green) {
            selectedColors.add(i)
        } else if (i > box.colorName && box.color == Color.Red) {
            selectedColors.add(i)
        }
    }

//
//    val sortedColors = selectedColors.sorted()
    if (deceptionNumShape == box.colorName) {
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
fun PreviewTouchTheShapesGameScreen() {
    TouchTheShapesGameScreen {}
}


@Composable
fun TriangleComposable() {
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White/*MaterialTheme.colorScheme.primary*/
    ) {
        Column {
            Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.BottomCenter) {

                Canvas(modifier = Modifier.size(65.dp), onDraw = {
                    drawTriangle(color = Color.Black)
                })
                Text(text = "Triangle", color = Color.White)
            }

            Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(65.dp), onDraw = {
                    drawHexagon(color = Color.Black)
                })
                Text(text = "Hexagon", color = Color.White)
            }
            Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(65.dp), onDraw = {
                    drawSquare(color = Color.Black)
                })
                Text(text = "Sqaure", color = Color.White)
            }
            Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(65.dp), onDraw = {
                    drawCircle(color = Color.Black)
                })
                Text(text = "Circle", color = Color.White)
            }
        }
    }
}


fun DrawScope.drawTriangle(color: Color) {
    val path = Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        close()
    }

    drawPath(
        path = path, color = color, style = Fill
    )
}

fun DrawScope.drawHexagon(color: Color) {
    val path = Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(0f, size.height / 4f)
        lineTo(0f, size.height - (size.height / 4f))
        lineTo(size.width / 2f, size.height)
        lineTo(size.width, size.height - (size.height / 4f))
        lineTo(size.width, size.height / 4f)
        close()
    }

    drawPath(
        path = path, color = color, style = Fill
    )
}

fun DrawScope.drawSquare(color: Color) {
    val path = Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        lineTo(size.width, 0f)

        close()
    }

    drawPath(
        path = path, color = color, style = Fill
    )
}

fun DrawScope.drawCircle(color: Color) {
    val radius = size.minDimension / 2f
    val center = Offset(size.width / 2f, size.height / 2f)

    drawCircle(
        color = color, center = center, radius = radius, style = Fill
    )
}

@Preview
@Composable
fun TriangleComposablePreview() {
    TriangleComposable()
}