package com.teamx.equiz.games.games.ui_components

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.BackButton

import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.BirdColor4
import com.teamx.equiz.games.ui.theme.DeceptionBlack
import kotlinx.coroutines.delay

var rightGameAnswersConcen = 1
var totalGameAnswersConcen = 1

@Preview
@Composable
fun MatchConcentrationGame(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

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
                if (timeLeft < 5) {
                    isAlert = true
                }
            }

            override fun onFinish() {
                isTimeUp = true
            }
        }.start()
    }


    if (isGameOver) {
        content(true, rightGameAnswersConcen, (totalGameAnswersConcen))
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersConcen, (totalGameAnswersConcen))
            }
        }


    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
            Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth()

                        .align(alignment = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }

            ConcentrationObjects()

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.iconbg),
                contentDescription = "bg"
            )
            if (isAlert) {
                GameAlertingTime()
            }
        }
    }


}

var linkListAdded2 = ArrayList<ConcentrationModel>()
var checkListAns = ArrayList<MatchEnumConcentration>()

@Preview
@Composable
fun ConcentrationObjects() {
    val maxCount = 5
    val concentrationModels by remember { mutableStateOf<ArrayList<ConcentrationModel>>(arrayListOf()) }/*
        LaunchedEffect(Unit) {
            repeat(maxCount) { count ->*/
    for (count in 0..maxCount) {


        concentrationModels.add(
            ConcentrationModel(
                count.toString(),
                count.toString(),
                count.toString(),
                MatchEnumConcentration.values()[count % 3]
            )
        )
    }

    /*}
}*/

//    val link = ArrayList<ConcentrationModel>()

//    linkListAdded2.forEach {
//        link.add(it)
//    }
    /*  Log.d("123123", "ConcentrationObjects:linkListAdded2abb $linkListAdded2")
      linkListAdded2.clear()
      Log.d("123123", "ConcentrationObjects:linkListAdded2abb $link")
      if (link.size >= 1) {
          Log.d("123123", "ConcentrationObjects:1212 $linkListAdded2")
          for (i in link) {
              linkListAdded2.add(i)
          }
      } else {
          Log.d("123123", "ConcentrationObjects:12 $linkListAdded2")
          for (i in linkListAdded2) {
  //            if (Random.nextBoolean()) {
              linkListAdded2.add(i)
  //            }
          }
      }*/


    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        for (i in 0..maxCount) {/* if (i == (currentCount - 1)) {
                 bool = false
             }*/

//            var temp = linkListAdded2
            ConcentrationObject(
                i, concentrationModels[i]/*temp.get(0)*//*, colorStateList[i]*/
            ) {

                Log.d("123123", "ConcentrationObjects:linkListAdded2 $linkListAdded2")/*  if (listAdded[it] == i) {

                      } else {

                      }*/
//                colorStateList[it] = Color.Transparent
            }


        }
//        }
    }
}

@Composable
fun ConcentrationObject(
    number: Int, concentrationModels: ConcentrationModel,/*itemCompared: ConcentrationModel,*/
    onClick: (Item: ConcentrationModel) -> Unit
) {
    var colorState by remember { mutableStateOf<Color>(DeceptionBlack) }

    Surface(color = if (linkListAdded2.contains(
            ConcentrationModel(
                number.toString(), number.toString(), number.toString(),

                MatchEnumConcentration.values()[number % 3]
            )
        )/* % 2 == 0*/) {
        colorState
    } else {
        colorState
    }, shape = RectangleShape, modifier = Modifier
        .height(85.dp)
        .width(70.dp)
        .offset(
            y = /*(-number * 60).dp*//* if (number in 2..3) {
                 (-(number % 3) * 90).dp
             } else*/ if (number > 1) {
                (-(number % 2) * 90).dp
            } else {
                (-number * 90).dp
            }, x = if (number in 2..3) {
                (/*-number*/0 * 90).dp
            } else if (number > 3) {
                (/*-number*/1 * 90).dp
            } else {
                (/*-number*/-1 * 90).dp
            }/*(-number * 60).dp*/
        )
        .clip(RoundedCornerShape(6.dp))

        .clickable(
            enabled = linkListAdded2.contains(
                ConcentrationModel(
                    number.toString(),
                    number.toString(),
                    number.toString(),
                    MatchEnumConcentration.values()[number % 3]
                )
            )
        ) {
            if (colorState == Color.Transparent && false) {
                Log.d("123123", "ConcentrationObjectWrong2:$number ::$/itemCompared ")
                return@clickable
            } else if (ConcentrationModel(
                    number.toString(),
                    number.toString(),
                    number.toString(),
                    MatchEnumConcentration.values()[number % 3]
                ) == linkListAdded2.get(0)
            ) {
                colorState
//                onClick(itemCompared)
//                Log.d("123123", "ConcentrationObjectWrong1:$number ::$itemCompared ")
            } else {
//                Log.d("123123", "ConcentrationObjectWrong2:$number ::$itemCompared ")
            }
        }
//            .graphicsLayer(alpha = 1f - number * 0.2f)

    ) {
        /*  Text(
              text = ""*//*number.toString()*//*,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )*/
        SpinningBox(concentrationModels.asdEnum)

    }
}

@Keep
data class ConcentrationModel(
    var name: String, var key: String, var value: String, var asdEnum: MatchEnumConcentration
)


@Composable
fun FlippableImage(asdEnum: MatchEnumConcentration) {
    var isFlipped by remember { mutableStateOf(false) }

//    val imageRes = if (isFlipped) R.drawable.your_flipped_image else R.drawable.your_unflipped_image
//    val imageBitmap = painterResource(id = imageRes).value.asImageBitmap()

    val rotationY: Float by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f)

    Image(painter = painterResource(id = concentrationCheckStringReturnDrawable(asdEnum)),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxSize()
            .background(BirdColor3)

            .clickable { isFlipped = !isFlipped }
            .graphicsLayer(rotationY = rotationY))
}

@Composable
fun RotatableImage(rotationZ: Float) {
    var isFlipped by remember { mutableStateOf(false) }

//    val imageRes = if (isFlipped) R.drawable.your_flipped_image else R.drawable.your_unflipped_image
//    val imageBitmap = painterResource(id = imageRes).value.asImageBitmap()

    val rotationY: Float by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f)

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickable { isFlipped = !isFlipped }
            .graphicsLayer(rotationZ = rotationZ)
    )
}

var isFlippy = false

@Composable
fun SpinningBox(asdEnum: MatchEnumConcentration = MatchEnumConcentration.TWO_MATCH) {
    var rotationState by remember { mutableStateOf(0f) }

    // Animate the rotationState from 0f to 360f repeatedly
    val rotation by animateFloatAsState(
        targetValue = rotationState, animationSpec = repeatable(
            iterations = 1, animation = tween(10)
        )
    )
    var isFlipped by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        repeat(180) { count ->
            delay(5000L)
//            colorStateList.add(colorState)
            rotationState = rotationState + 1

            isFlipped = false
        }
    }


//    val imageRes = if (isFlipped) R.drawable.your_flipped_image else R.drawable.your_unflipped_image
//    val imageBitmap = painterResource(id = imageRes).value.asImageBitmap()

    val rotationY: Float by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f)
    Surface(
        color = BirdColor4,
        modifier = Modifier
            .padding(2.dp)
            .fillMaxSize()
            .graphicsLayer(rotationY = rotationY)
    ) {
        // Content of the spinning box (optional)
//        RotatableImage(rotation)
//        FlippableImage(asdEnum)
        Image(painter = painterResource(
            id = if (isFlipped) concentrationCheckStringReturnDrawable(
                asdEnum
            ) else concentrationCheckStringReturnDrawable(MatchEnumConcentration.FOUR_MATCH)
        ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(
                        id = concentrationCheckStringReturnDrawable(
                            MatchEnumConcentration.FIVE_MATCH
                        )
                    ),
                    contentScale = ContentScale.FillBounds
                )
                .clickable(enabled = !isFlipped) {
                    isFlipped = !isFlipped
                    if (checkListAns.isEmpty()) {
                        checkListAns.add(asdEnum)
                    } else if (checkListAns.get(checkListAns.size - 1) == asdEnum && checkListAns.size % 2 != 0) {
                        checkListAns.add(asdEnum)

                    } else {
                        checkListAns.clear()
                        isFlipped = false
                    }


                })
//                .graphicsLayer(rotationY = rotationY))
    }

}

fun concentrationCheckStringReturnDrawable(str: MatchEnumConcentration): Int {
    return when (str) {
        MatchEnumConcentration.ONE_MATCH -> {

            R.drawable.numbers_pinkone
        }

        MatchEnumConcentration.TWO_MATCH -> {

            R.drawable.numbers_pinktwo
        }

        MatchEnumConcentration.THREE_MATCH -> {

            R.drawable.numbers_pinkthree
        }

        MatchEnumConcentration.FOUR_MATCH -> {

            R.drawable.numbers_pinkfour
        }

        MatchEnumConcentration.FIVE_MATCH -> {

            R.drawable.numbers_pinkfive
        }

        MatchEnumConcentration.SIX_MATCH -> {

            R.drawable.numbers_pinksix
        }

        MatchEnumConcentration.SEVEN_MATCH -> {

            R.drawable.numbers_pinkseven
        }

        MatchEnumConcentration.EIGHT_MATCH -> {

            R.drawable.numbers_pinkeight
        }

        MatchEnumConcentration.NINE_MATCH -> {

            R.drawable.numbers_pinknine
        }

        MatchEnumConcentration.TEN_MATCH -> {

            R.drawable.numbers_pinkten
        }

        else -> {
            R.drawable.numbers_pinkthree
        }
    }
}

/*EnumConcentration.BACK -> {

    R.drawable.cards_back_flip
}

EnumConcentration.FRONT -> {

    R.drawable.cards_front_flip
}*/
enum class MatchEnumConcentration {
    ONE_MATCH, TWO_MATCH, THREE_MATCH, FOUR_MATCH, FIVE_MATCH, SIX_MATCH, SEVEN_MATCH, EIGHT_MATCH, NINE_MATCH, TEN_MATCH
}

@Preview
@Composable
fun PreviewFlippedImage() {
//    FlippableImage()
//    SpinningBox()
}

