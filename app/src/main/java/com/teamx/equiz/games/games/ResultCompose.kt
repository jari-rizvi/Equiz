package com.teamx.equiz.games.games

import android.util.Log
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.ui.theme.GameEquizApplicationTheme
import com.teamx.equiz.ui.theme.toolbarUnique
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ChartScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Chart Image",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Chart Title",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    GameEquizApplicationTheme {
        ResultScreen(10, 7, 30, "", painterResource(id = R.drawable.weathercast_icon)) {}
    }
}

@Composable
fun ShowScoring(title: String, color: Color, score: String) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .height(55.dp)
            .border(BorderStroke(2.dp, color), shape = RoundedCornerShape(14.dp))
            .background(color = Color.White, shape = RoundedCornerShape(14.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$title", color = color, fontWeight = FontWeight.Normal)
        Text(text = score, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ShowScoring2(title: String, color: Color, score: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(55.dp)
//            .border(BorderStroke(1.dp, color), shape = RoundedCornerShape(14.dp))
            .background(color = color, shape = RoundedCornerShape(14.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$title", color = Color.White, fontWeight = FontWeight.Normal)
        Text(text = score, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ShowScoring2a(title: String, color: Color, score: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(55.dp)
//            .border(BorderStroke(1.dp, color), shape = RoundedCornerShape(14.dp))
            .background(color = Color.White, shape = RoundedCornerShape(14.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$title", color = color, fontWeight = FontWeight.Normal)
        Text(text = score, color = color, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun ShowMeantime(title: String, score: String) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.AvTimer, contentDescription = null)
        Text(modifier = Modifier.padding(6.dp), text = "$title", color = Color(0xFF9F81CA))
        Text(
            modifier = Modifier.padding(6.dp), fontWeight = FontWeight.Bold,
            text = score,
            fontSize = 14.sp,
            color = Color(0xFF9F81CA)
        )
    }
}

@Preview
@Composable
fun BottomResult() {
    Column(
        modifier = Modifier
            .height(100.dp)
            .width(230.dp)
            .clip(RoundedCornerShape(6))
            .background(Color(0xFF9F81CA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Battle Record",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceAround) {

            Column(
                modifier = Modifier/*.border(BorderStroke(2.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                .background(color = Color.White, shape = RoundedCornerShape(14.dp))*/,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Highest", color = Color.White)
                Text(text = "0", color = Color.White)
            }


            Column(
                modifier = Modifier/*.border(BorderStroke(2.dp, Color.Red), shape = RoundedCornerShape(14.dp))
                .background(color = Color.White, shape = RoundedCornerShape(14.dp))*/,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Lowest", color = Color.White)
                Text(text = "0", color = Color.White)
            }
        }
    }

}

@Composable
fun BottomResult2(onClick: (int: Int) -> Unit) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .width(130.dp)
            .clip(RoundedCornerShape(6))
            .background(Color(0xFF9F81CA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Score", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)

        Image(
            modifier = Modifier.clickable(enabled = true) {
                onClick(4)
            },
            painter = painterResource(id = R.drawable.padlockrain), contentDescription = null
        )
    }

}

@Composable
fun BottomButtons(onContinueClicked: (int: Int) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6))

            .padding(vertical = 12.dp)

            .fillMaxWidth(), Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = { onContinueClicked(1) },
            shape = RoundedCornerShape(19.dp),
            modifier = Modifier.width(130.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF9F81CA))
        ) {
            Text(text = "Retry")

        }
        Button(
            onClick = { onContinueClicked(2) },

            modifier = Modifier.width(130.dp),
            shape = RoundedCornerShape(19.dp),
            colors = ButtonDefaults.buttonColors(Color.White)

        ) {
            Text(text = "Back", color = Color.Black)

        }
        Button(
            shape = RoundedCornerShape(26.dp),
            onClick = { onContinueClicked(3) },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color(0xFF9F81CA))

        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null)

        }
    }

}

@Composable
fun BottomButtons2(onContinueClicked: (int: Int) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6))

            .padding(vertical = 12.dp)

            .fillMaxWidth(), Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = { onContinueClicked(1) },
            shape = RoundedCornerShape(19.dp),
            modifier = Modifier.width(130.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF9F81CA))
        ) {
            Text(text = "Continue")

        }
        Button(
            onClick = { onContinueClicked(2) },

            modifier = Modifier.width(130.dp),
            shape = RoundedCornerShape(19.dp),
            colors = ButtonDefaults.buttonColors(Color.White)

        ) {
            Text(text = "Back", color = Color.Black)

        }
        Button(
            shape = RoundedCornerShape(26.dp),
            onClick = { onContinueClicked(3) },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(Color(0xFF9F81CA))

        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null)

        }
    }

}

@Composable
fun BackButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.clickable { onClick() }, contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .height(56.dp)
                .padding(start = 15.dp)

        )
    }
}


@Composable
fun ResultScreen(
    total: Int,
    right: Int,
    time: Int,
    gameName: String,
    painter: Painter,
    onContinueClicked: (i: Int) -> Unit
) {

    var IsShareDialogTrue by remember { mutableStateOf(false) }

    var percentage = if (total == 0) {
        0f
    } else {
        ((right.toDouble() / total)).toFloat()
    }
//    val accurateCount: Float = 33f
//    val inaccurateCount: Float = 67f
    val accurateCount: Float = percentage
    val inaccurateCount: Float = 100f - percentage

    val totalCount = accurateCount + inaccurateCount

//    val accuracyPercentage: Float = (accurateCount / totalCount).toFloat()
    val accuracyPercentage: Float = percentage/*(accurateCount / totalCount).toFloat()*/

    var shouldShowOnboarding2 by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding2) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xffEFF4F8)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {


                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .background(color = Color(0xFF9F81CA)),
                    contentAlignment = Alignment.CenterStart
                ) {

                    BackButton(onClick = { onContinueClicked(2) }
                    )
                    Text(
                        text = "$gameName",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 17.sp
                    )

                }
                TitleHeader(
                    painter = painter, title = gameName
                )




                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    Arrangement.SpaceEvenly,
                ) {
                    DualColorCircularProgressBar2(accuracyPercentage)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShowScoring("Correct", Color(0xFF9F81CA), "${right.toInt()}")
                        ShowScoring("Incorrect", Color(0xFFC62E27), "${(total - right).toInt()}")

                    }
                    val to = if (total == 0) {
                        1
                    } else {
                        total
                    }
//                    ShowMeantime("Mean Time", " ${time / (to)}s")
                    ShowMeantime("Mean Time", " ${20/total}s")


                    /* Row(
                         modifier = Modifier
                             .padding(top = 32.dp)
                             .fillMaxWidth(), Arrangement.SpaceAround
                     ) {
                         BottomResult()
                         BottomResult2(){at->
                             onContinueClicked(at)
                         }
                     }*/
                    BottomButtons { i ->
                        if (i == 3) {
                            IsShareDialogTrue = true
                        } else {
                            shouldShowOnboarding2 = false
                            IsShareDialogTrue = false
                            onContinueClicked(i)
                        }
                    }
                }
            }

            if (IsShareDialogTrue) {
                dialogShareGame(total, right, time, gameName, painter) {

                    if (it == 3) {
                        IsShareDialogTrue = false
                    } else if (it == 21) {
                        onContinueClicked(it)
                    } else if (it == 22) {
                        onContinueClicked(it)
                    } else if (it == 23) {
                        onContinueClicked(it)
                    }
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
    } else {
//        HighOrLowGameScreen({ shouldShowOnboarding2 = true })
    }
}

@Composable
fun ResultScreen2(
    total: Int,
    right: Int,
    time: Int,
    gameName: String,
    painter: Painter,
    onContinueClicked: (i: Int) -> Unit
) {

    var IsShareDialogTrue by remember { mutableStateOf(false) }

    var percentage = if (total == 0) {
        0f
    } else {
        ((right.toDouble() / total)).toFloat()
    }
//    val accurateCount: Float = 33f
//    val inaccurateCount: Float = 67f
    val accurateCount: Float = percentage
    val inaccurateCount: Float = 100f - percentage

    val totalCount = accurateCount + inaccurateCount

//    val accuracyPercentage: Float = (accurateCount / totalCount).toFloat()
    val accuracyPercentage: Float = percentage/*(accurateCount / totalCount).toFloat()*/

    var shouldShowOnboarding2 by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding2) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xffEFF4F8)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {


                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .background(color = Color(0xFF9F81CA)),
                    contentAlignment = Alignment.CenterStart
                ) {

                    BackButton(onClick = { onContinueClicked(2) }
                    )
                    Text(
                        text = "$gameName",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 17.sp
                    )

                }
                TitleHeader(
                    painter = painter, title = gameName
                )




                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    Arrangement.SpaceEvenly,
                ) {
                    DualColorCircularProgressBar2(accuracyPercentage)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShowScoring("Correct", Color(0xFF9F81CA), "${right.toInt()}")
                        ShowScoring("Incorrect", Color(0xFFC62E27), "${(total - right).toInt()}")

                    }
                    val to = if (total == 0) {
                        1
                    } else {
                        total
                    }
//                    ShowMeantime("Mean Time", " ${time / (to)}s")
                    ShowMeantime("Mean Time", " ${20/total}s")


                    /* Row(
                         modifier = Modifier
                             .padding(top = 32.dp)
                             .fillMaxWidth(), Arrangement.SpaceAround
                     ) {
                         BottomResult()
                         BottomResult2(){at->
                             onContinueClicked(at)
                         }
                     }*/
                    BottomButtons2 { i ->
                        if (i == 3) {
                            IsShareDialogTrue = true
                        } else {
                            shouldShowOnboarding2 = false
                            IsShareDialogTrue = false
                            onContinueClicked(i)
                        }
                    }
                }
            }

            if (IsShareDialogTrue) {
                dialogShareGame(total, right, time, gameName, painter) {

                    if (it == 3) {
                        IsShareDialogTrue = false
                    } else if (it == 21) {
                        onContinueClicked(it)
                    } else if (it == 22) {
                        onContinueClicked(it)
                    } else if (it == 23) {
                        onContinueClicked(it)
                    }
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
    } else {
//        HighOrLowGameScreen({ shouldShowOnboarding2 = true })
    }
}

@Composable
fun TitleHeader(
    painter: Painter = painterResource(id = R.drawable.concentration_icon_c),
    title: String = "Concentration"
) {
    Row(
        modifier = Modifier.padding(22.dp)
    ) {

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier

                .size(66.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(5.dp)
                .clickable {

                }

        )
        Text(
            text = "$title",
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 13.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF9F81CA)
        )
    }
}


@Composable
fun CircularChart1(
    values: List<Float> = listOf(65f, 60f, 55f),
    colors: List<Color> = listOf(
        Color(0xFFbe1358), Color(0xFFe75874), Color(0xFFfbcbc9)
    ),
    backgroundCircleColor: Color = Color.LightGray.copy(alpha = 0.3f),
    legend: List<String> = listOf("Mango", "Apple", "Melon"),
    size: Dp = 280.dp,
    thickness: Dp = 16.dp,
    gapBetweenCircles: Dp = 42.dp
) {

    // Convert each value to angle
    val sweepAngles = values.map {
        360 * it / 100
    }

    Canvas(
        modifier = Modifier.size(size)
    ) {

        var arcRadius = size.toPx()

        for (i in values.indices) {

            arcRadius -= gapBetweenCircles.toPx()

            drawCircle(
                color = backgroundCircleColor,
                radius = arcRadius / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
            )

            drawArc(
                color = colors[i],
                startAngle = -90f,
                sweepAngle = sweepAngles[i],
                useCenter = false,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                size = Size(arcRadius, arcRadius),
                topLeft = Offset(
                    x = (size.toPx() - arcRadius) / 2, y = (size.toPx() - arcRadius) / 2
                )
            )

        }

    }

    Spacer(modifier = Modifier.height(32.dp))

    Column {
        for (i in values.indices) {
            DisplayLegend(color = colors[i], legend = legend[i])
        }
    }

}

@Composable
fun DisplayLegend(color: Color, legend: String) {

    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = legend, color = Color.Black
        )
    }
}


/*
@Preview
@Composable
fun PieChart() {
    var animateChart by remember { mutableStateOf(false) }
    val chartValues = listOf(30f, 50f, 20f) // Example chart values

    Column {
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val radius = size.width / 2
                var startAngle = 0f

                for (value in chartValues) {
                    val sweepAngle =animateFloatAsState(
                        targetValue = if (animateChart) value else 0f,
                        animationSpec = if (animateChart) {
                            tween(durationMillis = 1000, easing = LinearEasing)
                        } else {
                            snap()
                        }
                    ).value

                    drawArc(
                        color = Color.Blue,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(centerX - radius, centerY - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = 100f)
                    )

                    startAngle += sweepAngle
                }
            }
        }

        Button(
            onClick = { animateChart = !animateChart },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = if (animateChart) "Stop Animation" else "Start Animation")
        }
    }
}*/


@Keep
data class ChartSlice(val value: Float, val color: Color)

@Composable
fun PieChart(chartData: List<ChartSlice>) {
    var animatedFraction by remember { mutableStateOf(0f) }

    val animatedValues = chartData.map {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        animatedValues.forEachIndexed { index, animatable ->
            launch {
                animatable.animateTo(
                    targetValue = chartData[index].value,
                    animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier

                .size(200.dp)
                .padding(16.dp)

        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val radius = size.width / 2

                var startAngle = 0f
                val totalValue = chartData.sumByDouble { it.value.toDouble() }.toFloat()

                chartData.forEachIndexed { index, slice ->
                    val sweepAngle = animatedValues[index].value / totalValue * 360

                    drawArc(
                        color = slice.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(centerX - radius, centerY - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = 80f)
                    )

                    startAngle += sweepAngle
                }
            }
        }

        /* Button(
             onClick = { *//* Do something *//* },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Button")
        }*/
    }
}

@Preview
@Composable
fun DualColorCircularProgressBar2(progressVal: Float = 0.05f) {
    var progress by remember { mutableFloatStateOf(progressVal) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "Accuracy",
                color = toolbarUnique,
                fontSize = 12.sp,
                fontStyle = FontStyle.Normal, fontWeight = FontWeight.ExtraBold,
            )
            Text(
                text = "${(progressVal * 100).toInt()}%",
                color = toolbarUnique,
                fontSize = 26.sp,
                fontStyle = FontStyle.Normal, fontWeight = FontWeight.ExtraBold,
            )
        }

        CircularProgressIndicator(
            progress = .33f,
            modifier = Modifier
                .size(155.dp)
                .rotate(0f)

            /*.background(Color.Transparent)*/,
            color = Color(0xFF9F81CA)/*BirdColor1*/,

            strokeWidth = 32.dp
        )
        CircularProgressIndicator(
            progress = .33f,
            modifier = Modifier
                .size(155.dp)
                .rotate(118f)
            /*.background(Color.Transparent)*/,
            color = Color(0xFF9F81CA)/*BirdColor1*/,

            strokeWidth = 35.dp
        )
        CircularProgressIndicator(
            progress = .35f,
            modifier = Modifier
                .size(155.dp)
                .rotate(118f + 118f)
            /*.background(Color.Transparent)*/,
            color = Color(0xFF9F81CA)/*BirdColor1*/,

            strokeWidth = 36.dp
        )
        CircularProgressIndicator(
            progress = progress, // Remaining progress
            modifier = Modifier
                .size(155.dp)

                .rotate(190f)
            /*.background(Color.Transparent)*/,
            color = Color(0xFF9F81CA)/*Color.Transparent*/, strokeWidth = 28.dp

        )
        CircularProgressIndicator(
            progress = 1f - progress,
            modifier = Modifier
                .size(155.dp)
                .rotate(315f)
            /*.background(Color.Transparent)*/,
            color = Color(0xFFF7D1D1)/*BirdColor1*/,

            strokeWidth = 32.dp
        )
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Column(modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
        Text(
            text = stringResource(title).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center

        )
        content()
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }



    Surface(modifier) {
        if (shouldShowOnboarding) {

//                HomeScreen(Modifier.padding(padding))
            Column(
                modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))

                HomeSection(title = R.string.app_name) {

                    OnBoardingScreen(onContinueClicked = { shouldShowOnboarding = false })
                }

                Spacer(Modifier.height(16.dp))

            }

        } else {
            ResultScreen(
                10,
                7,
                30,
                "",
                painterResource(id = R.drawable.weathercast_icon),
                onContinueClicked = { shouldShowOnboarding = true })
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit, modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Equiz Game!")
        ElevatedButton(
            modifier = Modifier.padding(vertical = 24.dp), onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }

}


/*
@Composable
fun dpToPx(dp: Dp): Float {
    val density = LocalDensity.current.density
    return dp.value * density
}


@Composable
fun SwipeAbleButton(
    onSwipe: () -> Unit, content: String, backgroundColor: Color, buttonHeight: Dp = 56.dp
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isSwiping by remember { mutableStateOf(false) }

    val fadeAlpha by animateFloatAsState(
        targetValue = if (isSwiping) 0f else 1f, animationSpec = tween(durationMillis = 300)
    )

    Box(modifier = Modifier
        .width(120.dp)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    isSwiping = true
                },
                onDragEnd = {
                    isSwiping = false
                    if (offsetX < -buttonHeight.toPx() *//*|| velocity > 800*//*
) {
                        onSwipe()
                    } else {
                        offsetX = 0f
                    }
                },
                onDragCancel = {
                    isSwiping = false
                    offsetX = 0f
                }) { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.y
            }
        }) {
        Box(
            modifier = Modifier
                .height(buttonHeight)
                .background(Color.Cyan)
                .offset(y = offsetX.dp)
        ) {
            Button(
                onClick = { *//* Handle button click *//*
 },
                modifier = Modifier
                    .fillMaxWidth()

                    .background(Color.Blue)
                    .height(buttonHeight),

                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(text = content)
            }
        }

        if (isSwiping) {
            Button(
                onClick = { *//* Handle swipe action click *//*
 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight)
                    .background(Color.Black)
                    .alpha(fadeAlpha),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Remove")
            }
        }
    }
}


@Preview
@Composable
fun foody() {
    var scoring by remember { mutableStateOf(0f) }
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White), Arrangement.Center, Alignment.CenterHorizontally
    ) {

        SwipeAbleButton(
            onSwipe = { scoring++ },
            content = "Swipe to Add Count",
            backgroundColor = Color.Yellow
        )

        Text(text = "Score:$scoring")
    }
}*/
@Preview
@Composable
fun dialogShareGame(
    total: Int = 10,
    right: Int = 7,
    time: Int = 0,
    gameName: String = "Concentration",
    painter: Painter = painterResource(id = R.drawable.iconbg),
    onContinueClicked: (i: Int) -> Unit = {}
) {


    var percentage = if (total == 0) {
        0f
    } else {
        ((right.toDouble() / total)).toFloat()
    }
//    val accurateCount: Float = 33f
//    val inaccurateCount: Float = 67f
    val accurateCount: Float = percentage
    val inaccurateCount: Float = 100f - percentage

    val totalCount = accurateCount + inaccurateCount

//    val accuracyPercentage: Float = (accurateCount / totalCount).toFloat()
    val accuracyPercentage: Float = percentage/*(accurateCount / totalCount).toFloat()*/
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .background(Color(0xffEFF4F8), shape = RoundedCornerShape(14.dp))
                    .border(
                        BorderStroke(4.dp, Color(0xFF9F81CA)),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Share",
                    color = Color(0xFF9F81CA),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Share This Results",
                    textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(12.dp))



                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .height(450.dp)
                        .border(BorderStroke(6.dp, Color.White), shape = RoundedCornerShape(2.dp))

                        .background(color = Color(0xffEFF4F8), shape = RoundedCornerShape(2.dp))
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    Arrangement.SpaceEvenly,
                ) {
                    TitleHeader(
                        painter = painter, title = gameName
                    )
                    DualColorCircularProgressBar2(accuracyPercentage)
                    val to = if (right == 0) {
                        1
                    } else {
                        right
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShowScoring2a("Incorrect", Color(0xFF9F81CA), "${(total - right).toInt()}")
                        ShowScoring2("Correct", Color(0xFF9F81CA), "${right.toInt()}")

                    }
//                    ShowMeantime("Mean Time", " ${time / to}s")
                    ShowMeantime("Mean Time", " ${20/total}s")

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        painter = painterResource(id = R.drawable.iconbg),
                        contentDescription = "bg"
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                Log.d("123", "dialogShareGame: ")

                            },
                        painter = painterResource(id = R.drawable.x_social),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {

                                Log.d("123", "dialogShareGame: ")
                            },
                        painter = painterResource(id = R.drawable.vector__2_),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                Log.d("123", "dialogShareGame: ")

                            },
                        painter = painterResource(id = R.drawable.social),
                        contentDescription = null
                    )

                }
            }
            Icon(
                modifier = Modifier
                    .padding(7.dp)
                    .clickable { onContinueClicked(3) }
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.cancel_share),
                contentDescription = null
            )
        }
    }
}











