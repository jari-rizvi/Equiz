package com.teamx.equiz.games.games.learningy

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamx.equiz.games.games.learningy.musiclearning.correctSound
import com.teamx.equiz.games.games.learningy.musiclearning.incorrectSound
import com.teamx.equiz.games.games.rightGameAnswersSpin
import com.teamx.equiz.games.games.wrongGameAnswersSpin
import com.teamx.equiz.ui.theme.BirdColor4
import kotlin.random.Random

class CheckingSpin {
}

var counterp = 0

@Preview
@Composable
fun SpinObstacles() {
    var isComplex by remember { mutableStateOf(false) }
    var arrVal by remember { mutableStateOf(generateRanArr()) }
    val context = LocalContext.current
    var rotationState by remember { mutableFloatStateOf(90f) }
    Box(contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isComplex) {

                LaunchedEffect(rotationState) {
                    rotationState = 90f
                }
                val rotation by animateFloatAsState(
                    targetValue = rotationState, animationSpec = repeatable(
                        iterations = 1, animation = tween(3000)
                    ), label = ""
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .rotate(rotation)
                ) {
                    Column {
                        repeat(3) { column ->
                            Row() {
                                repeat(3) { row ->
                                    val index = row * 3 + column
                                    var colorState by remember { mutableStateOf<Color>(Color.Gray) }

                                    Box(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(85.dp)
                                            .background(
                                                color = colorState, shape = RoundedCornerShape(6.dp)
                                            )
                                            .clip(RoundedCornerShape(6.dp))

                                            .clickable(
                                                enabled = true
                                            ) {

                                                if (arrVal.contains(index)) {

                                                    var temp = counterp
                                                    temp += 1
                                                    counterp = temp

                                                    colorState = if (colorState == BirdColor4) {
                                                        Color.Gray
                                                    } else {
                                                        BirdColor4
                                                    }
                                                    if (counterp == arrVal.size) {

                                                        arrVal = generateRanArr()
                                                        isComplex = false

                                                        rightGameAnswersSpin++
                                                        wrongGameAnswersSpin++
                                                        correctSound(context)
                                                    }

                                                } else {
                                                        incorrectSound(context)
                                                    wrongGameAnswersSpin++
                                                    arrVal = generateRanArr()
                                                    isComplex = false
                                                }

                                            }, contentAlignment = Alignment.Center

                                    ) {


                                    }


                                }
                            }
                        }
                    }
                }

            } else {

                Box(

                ) {
                    Column {
                        repeat(3) { column ->
                            Row() {
                                repeat(3) { row ->
                                    val index = row * 3 + column


                                    Box(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(85.dp)
                                            .background(
                                                color = if (arrVal.contains(index)) {
                                                    BirdColor4
                                                } else {
                                                    Color.Gray
                                                },
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .clip(RoundedCornerShape(6.dp))

                                            .clickable(
                                                enabled = false
                                            ) {


                                            }, contentAlignment = Alignment.Center

                                    ) {


                                    }


                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(45.dp)
                        .width(200.dp)
                        .background(
                            shape = RoundedCornerShape(12.dp),
                            color = com.teamx.equiz.games.ui.theme.BirdColor4
                        )
                        .clickable {
                            isComplex = true
                            rotationState = 0f
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Memorized"/*: $score*/, fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.bodyLarge, color = Color.White,
                        modifier = Modifier
                    )
                }
            }
        }
    }


}

fun generateRanArr(): ArrayList<Int> {
    var arr = arrayListOf<Int>()

    for (i in 0..8) {
        if (Random.nextBoolean()) {
            arr.add(i)
        }
    }
    if (arr.isEmpty()) {
        arr.add(4)
    }
    counterp = 0
    return arr
}




