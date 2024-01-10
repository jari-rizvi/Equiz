package com.teamx.equiz.games.games


import android.os.CountDownTimer
import androidx.annotation.Keep
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.DeceptionBlack
import com.teamx.equiz.games.ui.theme.DeceptionPink
import com.teamx.equiz.games.ui.theme.DeceptionPurple
import com.teamx.equiz.games.ui.theme.DeceptionYellow
import kotlin.random.Random

@Keep
data class ShapeBox(val colorName: ShapeBundle, val color: Color)

enum class ShapeBundle {
    YELLOW_HEXAGON, WHITE_TRIANGLE, BLACK_SQUARE, PURPLE_CIRCLE/*, PINK*/
}

var rightGameAnswersDecep = 0
var totalGameAnswersDecep = 1

@Composable
fun TouchTheShapesGameScreen(content: (boolean: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }
    rightGameAnswers = 1
    wrongGameAnswers = 1

    var timeLeft by remember { mutableStateOf(20L) }
    var isTimeUp by remember { mutableStateOf(false) }
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
        content(true, rightGameAnswersDecep, totalGameAnswersDecep)
          rightGameAnswersDecep = 0
          totalGameAnswersDecep = 1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersDecep, totalGameAnswersDecep)
                rightGameAnswersDecep = 0
                totalGameAnswersDecep = 1
            }
        }


    } else {


        var score by remember { mutableStateOf(0) }
        var spanCount by remember { mutableStateOf(2) }
        var asGridCells by remember { mutableStateOf(GridCells.Fixed(2)) }
        var boxes by remember { mutableStateOf(generateBoxes()) }
        var restart by remember { mutableStateOf(true) }
//    var strName by remember { mutableStateOf("") }
//    var strName = ""


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
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//            Text(
//                text = "Shapes Deception",
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
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
                                    totalGameAnswersDecep++
                                    updateScore(boxes, box, index) { i, bool ->
                                        rightGameAnswersDecep++
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


                            Box(
                                modifier = Modifier.padding(5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(95.dp), onDraw = {

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

                                            if (deceptionNumShape == ShapeBundle.WHITE_TRIANGLE) {
                                                drawTriangle(color = box.color)
                                            } else {
                                                drawSquare(color = box.color)
                                            }
                                        }

                                        ShapeBundle.BLACK_SQUARE -> {
                                            DeceptionBlack
//                                    drawSquare(color = box.color)

                                            if (deceptionNumShape == ShapeBundle.BLACK_SQUARE) {
                                                drawSquare(color = box.color)
                                            } else {
                                                drawTriangle(color = box.color)
                                            }

                                        }

                                        ShapeBundle.PURPLE_CIRCLE -> {
                                            DeceptionPurple
//                                    drawCircle(color = box.color)


                                            if (deceptionNumShape == ShapeBundle.PURPLE_CIRCLE) {
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
                                    text = when (box.colorName) {


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
                                    },
                                    /*box.colorName.toString()*/
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }


                        }
                    }

                }


//            Text(
//                text = "Score: $score",
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(top = 16.dp)
//            )
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
    TouchTheShapesGameScreen {bool,rightAnswer,total -> }
}


@Composable
fun TriangleComposable() {
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White/*MaterialTheme.colorScheme.primary*/
    ) {
        Column {
            Box(modifier = Modifier.padding(5.dp), contentAlignment = Alignment.BottomCenter) {

                Canvas(modifier = Modifier.size(65.dp), onDraw = {
                    drawRoundedTriangle(color = Color.Red)
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

fun DrawScope.drawRoundedTriangle(color: Color) {
    /*val width = size.width
    val height = size.height
    val cornerRadius = CornerRadius(size.minDimension / 10f) // Adjust the radius as needed

    val path = android.graphics.Path().apply {
        moveTo(width / 2f, 0f)
        lineTo(0f, height)
        lineTo(width, height)
        close()
    }

    drawRoundRect(
        color = color,
        topLeft = Offset(0f, 0f),
        size = Size(width, height),
        cornerRadius = cornerRadius,
        style = Fill
    )*/
    /* val path = Path().apply {
         moveTo(size.width / 2f, 0f)
         lineTo(0f, size.height)
         lineTo(size.width, size.height)
         close()
     }

     // Calculate the corner radius to round the edges
     val cornerRadius = size.width / 10f // Adjust the radius as needed

     // Draw the rounded triangle
     drawPath(
         path = path,
         color = color,
         style = Fill,
         topLeftRadius = CornerRadius(cornerRadius),
         topRightRadius = CornerRadius(cornerRadius),
         bottomLeftRadius = CornerRadius(cornerRadius),
         bottomRightRadius = CornerRadius(cornerRadius)
     )*/

    /*  val path = Path().apply {
          moveTo(size.width / 2f, 0f)
          lineTo(0f, size.height)
          lineTo(size.width, size.height)
          close()
      }

      // Calculate the corner radius to round the edges
      val cornerRadius = size.width / 10f // Adjust the radius as needed

      // Apply rounded corners manually
      path.apply {
          addRoundRect(
              RoundRect(
                  left = 0f,
                  top = 0f,
                  right = size.width,
                  bottom = size.height,
                  cornerRadius = CornerRadius(cornerRadius)
              )
          )
      }

      // Draw the rounded triangle
      drawPath(
          path = path,
          color = color,
  //        style = Fill
      )*/

    val path = Path().apply {
        val triangleSize = size.width / 2
        val cornerRadius = triangleSize / 5 // Adjust the radius as needed

        moveTo(size.width / 2f, 0f)
        lineTo(0f, size.height)
        lineTo(size.width, size.height)
        close()

        // Apply rounded corners manually
        addRoundRect(
            RoundRect(
                left = size.width / 2f - triangleSize / 2,
                top = 0f,
                right = size.width / 2f + triangleSize / 2,
                bottom = cornerRadius,
                cornerRadius = CornerRadius(cornerRadius)
            )
        )
    }

    // Draw the rounded triangle
    drawPath(
        path = path,
        color = color,
        style = Fill
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