package com.teamx.equiz.games.games

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.ui.theme.BirdColor1
import com.teamx.equiz.games.ui.theme.BirdColor3
import kotlin.random.Random

@Preview
@Composable
fun QuickEyeGame(content: @Composable () -> Unit = {}) {
    content()

    var restart by remember { mutableStateOf(true) }

    var boxes by remember { mutableStateOf(generateBoxes()) }



    Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
    Box(
        modifier = Modifier
            .padding(16.dp)

            .size(300.dp)
    ) {

        for (i in boxes.indices) {

            QuickAnimatedObject(i, boxes[i]/*boxes[0]*/) { iit ->
                Log.d("123123", "TouchTheNumbersGameScreen12: ")

                if (!restart) {
                    boxes = generateBoxes()
                    restart = true
                }
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


}

private fun generateBoxes(): List<NumberBox> {
    val numbers = mutableListOf<Int>()
    for (i in 1..9) {
        numbers.add(i)
    }
    numbers.shuffle()

    val colors = mutableListOf<Color>()
    val colorBox = if (Random.nextBoolean()) BirdColor3 else BirdColor1
    for (i in 1..9) {
        colors.add(colorBox)
    }

    return numbers.mapIndexed { index, number ->
        NumberBox(number = number, color = colors[index])
    }
}

@Composable
fun QuickAnimatedObject(number: Int, itemCompared: NumberBox, onClick: (Item: NumberBox) -> Unit) {


    Surface(color = itemCompared.color, shape = RectangleShape, modifier = Modifier
        .size(85.dp)
        .offset(
            y = if (number in 3..5) {
                (((number % 3) + 2) * 90).dp
            } else if (number > 5) {
                (((number % 3) + 2) * 90).dp
            } else {
                ((number + 2) * 90).dp
            },

            x = if (number in 3..5) {
                (2 * 70).dp
            } else if (number > 5) {
                (3 * 80).dp
            } else {
                (1 * 40).dp
            }
        )
        .clip(RoundedCornerShape(6.dp))

        .clickable {
            onClick(itemCompared)

        }

    ) {

        Text(
            text = itemCompared.number.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )


    }
}

