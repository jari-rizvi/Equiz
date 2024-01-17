package com.teamx.equiz.games.games

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI


@Composable
fun CircularRotatingView() {
    var isRotating by remember { mutableStateOf(false) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var rotationDuration by remember { mutableStateOf(30000L) } // 30 seconds
    val rotationAnim = rememberInfiniteTransition()
    val rotation by rotationAnim.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = rotationDuration.toInt()),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .clip(RoundedCornerShape(150.dp))
                .clickable {
                    if (!isRotating) {
                        isRotating = true

                        rotationAngle = rotation
                    }
                }
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    val radius = size.minDimension / 2
                    val centerX = size.width / 2
                    val centerY = size.height / 2

                    val pieceCount = 16
                    val angle = (2 * PI / pieceCount).toFloat()

                    for (i in 0 until pieceCount) {
                        val startAngle = i * angle
                        val endAngle = (i + 1) * angle
                        val color = if (i % 2 == 0) Color.Red else Color.Blue

                        rotate((startAngle + endAngle) / 2 + rotationAngle) {
                            drawRect(
                                color = color,
                                size = Size(
                                    centerX - radius,/* centerY - 20.dp.toPx(),*/
                                    centerX + radius,/* centerY + 20.dp.toPx()*/
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularRotatingViewPreview() {
    CircularRotatingView()
}