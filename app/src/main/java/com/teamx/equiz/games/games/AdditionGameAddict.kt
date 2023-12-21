package com.teamx.equiz.games.games

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.ui.theme.BirdColor3
import com.teamx.equiz.ui.theme.BirdColor4
import kotlinx.coroutines.delay
import java.util.LinkedList


@Composable
fun AdditionAddictionGameMethod() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFE1E1E1)),
    ) {
        AddictGame()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.iconbg),
            contentDescription = "bg"
        )
    }
}

var linkListAddict67 = LinkedList<Int>()
var linkListAddict67Checker = LinkedList<Int>()

@Preview
@Composable
fun ViewAddictionGame() {
    MaterialTheme {
        AdditionAddictionGameMethod()
    }
}

@Composable
fun AddictGame() {
    var counterNum by remember { mutableStateOf(1) }

    val maxCount = 5
    var gameStarted by remember { mutableStateOf(false) }
    var changable by remember { mutableStateOf(false) }

    var counter = 0
    var randNumber by remember { mutableStateOf(6/*Random.nextInt(0, 6)*/) }
    if (changable) {

        randNumber = 7
        linkListAddict67.clear()
        for (i in 0..maxCount) {

            linkListAddict67.push(i)

        }/* for (i in linkListAddict67) {
             if (Random.nextBoolean()) {
                 linkListAddict67Checker.push(i)
             }
         }*/

        Log.d("123123", "AscendingObjects:AddictGameabbbbbb $linkListAddict67")

        Log.d("123123", "AscendingObjects:AddictGame ${linkListAddict67.size}")
        ///

         changable  = false

    }



    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier,
                text = randNumber.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Center

            ) {
                repeat(2) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { column ->
                            val index = row * 3 + column
                            Log.d("123123", "AddictGame:$index ")/*counter*/
                            AddictObject67(index, randNumber  ) {
//                                temp.remove(it)
//                            temp.pop()
//                                linkListAddict67Checker /*= temp*/
                               changable = it

                                Log.d(
                                    "123123", "AscendingObjects:AddictGame $linkListAddict67Checker"
                                )
                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    delay(1300)
                    gameStarted = false
                    changable = true
                }
            }
        }
    }
}

@Composable
fun AddictObject67(number: Int,randomNum: Int, onClick: (item: Boolean) -> Unit) {
    var colorState by remember { mutableStateOf<Color>(Color.White) }
    var colorStateTxt by remember { mutableStateOf<Color>(BirdColor3) }
//    var colorState by remember { mutableStateOf<Color>(Color.Gray) }

    Box(
        modifier = Modifier
            .padding(6.dp)
            .size(85.dp)
            .background(
                color = colorState, shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = linkListAddict67Checker.contains(number) || true
            ) {
                if (/*colorState == Color.White &&*/ !linkListAddict67Checker.contains(number)) {
                    colorState = BirdColor4
                    colorStateTxt = Color.White
                    linkListAddict67Checker.push(number)
                  val too =   if (linkListAddict67Checker.size >= 3 && randomNum == linkListAddict67Checker.sum()) {
                        Log.d("TAG", "AddictGame:Right ${linkListAddict67Checker.sum()}")
                        true
                    } else {
                        Log.d("TAG", "AddictGame:Wrong ${linkListAddict67Checker.sum()}")
                    false
                    }
                    onClick(too)


                    Log.d("123123", "AnimatedObject2:$number ::$number ")
                    return@clickable
                } else if (linkListAddict67Checker.contains(number)) {
                    linkListAddict67Checker.remove(number)
//                    colorState = Color.White
                    colorState = Color.White
                    colorStateTxt = BirdColor3
                    onClick(false)
                    Log.d("123123", "AnimatedObject1:$number ::$number ")
                } else {
                    Log.d("123123", "AnimatedObject2:$number ::$number ")
                }
            }, contentAlignment = Alignment.Center

    ) {
        Column {

            Text(
                modifier = Modifier,
                text = number.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = colorStateTxt,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }


    }
}

@Composable
fun returnNumberImage(enumNumberEnum: NumberEnum): Painter {


    return when (enumNumberEnum) {

        NumberEnum.ONE -> {

            painterResource(id = R.drawable.numbers_blueone)
        }

        NumberEnum.TWO -> {

            painterResource(id = R.drawable.numbers_bluetwo)
        }

        NumberEnum.THREE -> {

            painterResource(id = R.drawable.numbers_bluethree)
        }

        NumberEnum.FOUR -> {

            painterResource(id = R.drawable.numbers_bluefour)
        }

        NumberEnum.FIVE -> {

            painterResource(id = R.drawable.numbers_bluefive)
        }

        NumberEnum.SIX -> {

            painterResource(id = R.drawable.numbers_bluesix)
        }

        NumberEnum.SEVEN -> {

            painterResource(id = R.drawable.numbers_blueseven)
        }

        NumberEnum.EIGHT -> {

            painterResource(id = R.drawable.numbers_blueeight)
        }

        NumberEnum.NINE -> {

            painterResource(id = R.drawable.numbers_bluenine)
        }

        NumberEnum.TEN -> {
            painterResource(id = R.drawable.numbers_blueten)
        }

        NumberEnum.ZERO -> {
            painterResource(id = R.drawable.numbers_bluezero)
        }

        else -> {

            painterResource(id = R.drawable.ic_launcher_background)
        }
    }

}

fun returnEnumNumber(enumNumber: Int): NumberEnum {


    return when (enumNumber) {

        1 -> {
            NumberEnum.ONE
        }

        2 -> {
            NumberEnum.TWO
        }

        3 -> {
            NumberEnum.THREE
        }

        4 -> {
            NumberEnum.FOUR
        }

        5 -> {
            NumberEnum.FIVE
        }

        6 -> {
            NumberEnum.SIX
        }

        7 -> {
            NumberEnum.SEVEN
        }

        8 -> {
            NumberEnum.EIGHT
        }

        9 -> {
            NumberEnum.NINE
        }

        10 -> {
            NumberEnum.TEN
        }

        0 -> {
            NumberEnum.ZERO
        }

        else -> {

            NumberEnum.NINE
        }


    }
}

enum class NumberEnum {
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
}