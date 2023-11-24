package com.teamx.equiz.games.games.ui_components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .padding(2.dp)
                .height(30.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Yellow)
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
    fun DiagonalGradientCard5(
        modifier: Modifier = Modifier, content: @Composable () -> Unit = {}
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Blue, // Top color
                            Color.Red   // Bottom color
                        ), start = Offset.Zero, end = Offset.Infinite
                    )
                )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Blue, // Top color
                                Color.Red   // Bottom color
                            )
                        )
                    )
                }
                content()
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