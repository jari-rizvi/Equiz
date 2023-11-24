package com.teamx.equiz.games.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class TenSecond {
}

/*
@Composable
fun ShapeTransformationApp() {
    var shape by remember { mutableStateOf(Shape1.Circle) }

    LaunchedEffect(Unit) {
        delay(10000) // Wait for 10 seconds
        shape = Shape1.Triangle
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasSize = LocalDensity.current.run {
                Size(size.width.toDp().value, size.height.toDp().value)
            }

            drawShape(shape, canvasSize)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { shape = Shape1.Circle },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Reset")
        }
    }
}

@Composable
private fun CanvasScope.drawShape(shape: Shape1, canvasSize: Size) {
    val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
    val radius = canvasSize.width / 2f

    when (shape) {
        Shape1.Circle -> drawCircle(Color.Red, radius, center)
        Shape1.Triangle -> drawTriangle(Color.Blue, radius, center)
    }
}

@Composable
private fun CanvasScope.drawTriangle(color: Color, size: Float, center: Offset) {
    val triangleHeight = size * 2f
    val triangleWidth = triangleHeight * (2f / 3f)
    val halfWidth = triangleWidth / 2f
    val halfHeight = triangleHeight / 2f

    val path = android.graphics.Path().apply {
        moveTo(center.x, center.y - halfHeight)
        lineTo(center.x - halfWidth, center.y + halfHeight)
        lineTo(center.x + halfWidth, center.y + halfHeight)
        close()
    }

    drawPath(android.graphics.Path(path), color)
}

enum class Shape1 {
    Circle,
    Triangle
}*/
@Preview
@Composable
fun ColorChangeApp() {


    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
        /*  .pointerInput("dragging") {
              detectDragGestures { change, dragAmount ->
                  pointerOffset += dragAmount
              }
          }
          .onSizeChanged {
              pointerOffset = Offset(it.width / 2f, it.height / 2f)
          }
          .drawWithContent {
              drawContent()
              // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
              drawRect(
                  Brush.radialGradient(
                      listOf(Color.Transparent, Color.Black),
                      center = pointerOffset,
                      radius = 100.dp.toPx(),
                  )
              )
          }*/
    ) {
        // Your composables here
        var color by remember { mutableStateOf(Color.Red) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(100) // Wait for 10 seconds
                color = if (color == Color.Red) Color.Blue else Color.Red
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .alpha(0.3f)

                    .background(brush)
                    .clip(RoundedCornerShape(12))
            )
        }
    }

}

/*@Stable
fun horizontalGradient(
    colors: List<Color>,
    startX: Float = 0.0f,
    endX: Float = Float.POSITIVE_INFINITY,
    tileMode: TileMode = TileMode.Clamp
): Brush */
val brush = Brush.verticalGradient(listOf(Color.Red, Color.Blue, Color.White))