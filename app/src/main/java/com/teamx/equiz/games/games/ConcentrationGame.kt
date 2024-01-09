package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import com.teamx.equiz.games.ui.theme.BirdColor3
import com.teamx.equiz.games.ui.theme.BirdColor4
import com.teamx.equiz.games.ui.theme.DeceptionBlack

var rightGameAnswersConcen = 1
var totalGameAnswersConcen = 1
var checker = 0
val concentrationModels3 = ArrayList<ArrayList<ConcentrationModel>>(arrayListOf())

@Preview
@Composable
fun ConcentrationGame(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(20L) }
    var isTimeUp by remember { mutableStateOf(false) }
    var timerRunning by remember { mutableStateOf(true) }
    var startMemorized by remember { mutableStateOf(false) }
    var startMemorized2 by remember { mutableStateOf(false) }


    val concentrationModels:/*by remember { mutableStateOf<*/ArrayList<ConcentrationModel>/*>(*/ =
        arrayListOf() /*)}*/


    val concentrationModels2 = arrayListOf(0, 1, 2, 3, 4, 5)

    concentrationModels.clear()


    for (count in concentrationModels2) {

        concentrationModels.add(
            ConcentrationModel(
                count.toString(),
                count.toString(),
                count.toString(),
                EnumConcentration.values()[count % 3]
            )
        )
    }



    for (counter in 0..12) {


        concentrationModels3.add(concentrationModels)
    }

    LaunchedEffect(startMemorized2) {
//        checker++
        Log.d("123123", "ConcentrationGame11111$checker ")



    }

    LaunchedEffect(true) {


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

        TimeUpDialogCompose { i ->
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
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
            ) {

                BackButton(onClick = { content(false, 0, 0) }
                )
                Text(
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            if (startMemorized) {
                ConcentrationObjects(concentrationModels3[checker]) {
                    checker++

                    concentrationModels3.get(checker).forEach {
                        it.IsFlipped = false
                    }


                    rightGameAnswersConcen++
                    startMemorized = false
//                    startMemorized2 = !startMemorized2


                }
            } else {
                ConcentrationObjects2(concentrationModels3.get(checker)) {
                    startMemorized = true
                }

            }

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

var concentLinkListAdded2 = ArrayList<ConcentrationModel>()
var checkListAns = ArrayList<EnumConcentration>()

@Preview
@Composable
fun ConcentrationObjects(
    arr: ArrayList<ConcentrationModel> = arrayListOf(),
    onClick: () -> Unit = {}
) {
    /* val maxCount = 5*/
    val concentrationModels by remember { mutableStateOf<ArrayList<ConcentrationModel>>(arr) }


    var startAgain by remember { mutableStateOf(true) }
//
    LaunchedEffect(startAgain) {

        checkListAns.clear()
        removed.clear()

        Log.d("123123", "ConcentrationObjects:$concentrationModels ")

    }


    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {


        concentrationModels.forEachIndexed { i, item ->

            ConcentrationObject(
                i, concentrationModels[i]
            ) {
                concentrationModels3.shuffle()
                onClick()
                startAgain = !startAgain
                Log.d("123123", "ConcentrationObjects:linkListAdded2 $concentLinkListAdded2")
            }
        }
    }
}

@Preview
@Composable
fun ConcentrationObjects2(
    arr: ArrayList<ConcentrationModel> = arrayListOf(),
    onClick: () -> Unit = {}
) {
    val maxCount = 5
    val concentrationModels by remember { mutableStateOf<ArrayList<ConcentrationModel>>(arr) }
//    val concentrationModels2 by remember {
//        mutableStateOf<ArrayList<Int>>(
//            arrayListOf(
//                0,
//                1,
//                2,
//                3,
//                4,
//                5
//            )
//        )
//    }


    var startAgain by remember { mutableStateOf(true) }
//    concentrationModels.clear()
//    for (count in concentrationModels2) {
//
//
//        concentrationModels.add(
//            ConcentrationModel(
//                count.toString(),
//                count.toString(),
//                count.toString(),
//                EnumConcentration.values()[count % 3]
//            )
//        )
//    }
//
    LaunchedEffect(startAgain) {

        checkListAns.clear()
        removed.clear()

        Log.d("123123", "ConcentrationObjects:$concentrationModels ")

    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(270.dp), contentAlignment = Alignment.Center
        ) {


            concentrationModels.forEachIndexed { i, item ->

                ConcentrationObject2(
                    i, concentrationModels[i]
                ) {
                    startAgain = !startAgain
//                    onClick()
                    Log.d("123123", "ConcentrationObjects:linkListAdded2 $concentLinkListAdded2")
                }
            }
        }
        Box(
            modifier = Modifier
                .height(45.dp)
                .width(200.dp)
                .background(shape = RoundedCornerShape(12.dp), color = BirdColor4)
                .clickable {
                    onClick()
                }, contentAlignment = Alignment.Center
        ) {
            Text(text = "Memorized", fontSize = 16.sp,
                fontWeight = FontWeight.Companion.ExtraBold,
                color = Color.White)
        }
    }
//    for (count in 0..maxCount) {
    /* for (count in 0..maxCount) {


             concentrationModels.add(
                 ConcentrationModel(
                     count.toString(),
                     count.toString(),
                     count.toString(),
                     EnumConcentration.values()[count % 3]
                 )
             )
         }*/


}

@Composable
fun ConcentrationObject(
    number: Int, concentrationModels: ConcentrationModel,
    onClick: (Item: ConcentrationModel) -> Unit
) {
    var colorState by remember { mutableStateOf<Color>(DeceptionBlack) }


    Surface(
        color = if (concentLinkListAdded2.contains(
                ConcentrationModel(
                    number.toString(), number.toString(), number.toString(),

                    EnumConcentration.values()[number % 3]
                )
            )/* % 2 == 0*/) {
            colorState
        } else {
            colorState
        }, shape = RectangleShape, modifier = Modifier
            .height(85.dp)
            .width(70.dp)
            .offset(
                y = if (number > 1) {
                    (-(number % 2) * 90).dp
                } else {
                    (-number * 90).dp
                }, x = if (number in 2..3) {
                    (0 * 90).dp
                } else if (number > 3) {
                    (1 * 90).dp
                } else {
                    (-1 * 90).dp
                }
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = concentLinkListAdded2.contains(
                    ConcentrationModel(
                        number.toString(),
                        number.toString(),
                        number.toString(),
                        EnumConcentration.values()[number % 3]
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
                        EnumConcentration.values()[number % 3]
                    ) == concentLinkListAdded2.get(0)
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

        SpinningBox(concentrationModels.asdEnum, concentrationModels) {
            onClick(it)
        }

    }
}

@Composable
fun ConcentrationObject2(
    number: Int, concentrationModels: ConcentrationModel,
    onClick: (Item: ConcentrationModel) -> Unit
) {
    var colorState by remember { mutableStateOf<Color>(DeceptionBlack) }


    Surface(
        color = if (concentLinkListAdded2.contains(
                ConcentrationModel(
                    number.toString(), number.toString(), number.toString(),

                    EnumConcentration.values()[number % 3]
                )
            )/* % 2 == 0*/) {
            colorState
        } else {
            colorState
        }, shape = RectangleShape, modifier = Modifier
            .height(85.dp)
            .width(70.dp)
            .offset(
                y = if (number > 1) {
                    (-(number % 2) * 90).dp
                } else {
                    (-number * 90).dp
                }, x = if (number in 2..3) {
                    (0 * 90).dp
                } else if (number > 3) {
                    (1 * 90).dp
                } else {
                    (-1 * 90).dp
                }
            )
            .clip(RoundedCornerShape(6.dp))

            .clickable(
                enabled = concentLinkListAdded2.contains(
                    ConcentrationModel(
                        number.toString(),
                        number.toString(),
                        number.toString(),
                        EnumConcentration.values()[number % 3]
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
                        EnumConcentration.values()[number % 3]
                    ) == concentLinkListAdded2.get(0)
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

        SpinningBox2(concentrationModels.asdEnum, concentrationModels) {
            onClick(it)
        }

    }
}

@Keep
data class ConcentrationModel(
    var name: String,
    var key: String,
    var value: String,
    var asdEnum: EnumConcentration,
    var IsFlipped: Boolean = false
)


/*@Composable
fun Modifier.flip(horizontal: Boolean = false, vertical: Boolean = false): Modifier {
    return this.then(
        Modifier.graphicsLayer(
            scaleX = if (horizontal) -1f else 1f, scaleY = if (vertical) -1f else 1f
        )
    )
}*/


/*@Composable
fun FlippedImage() {
//    val imageBitmap = remember { loadImageResource(R.drawable.your_image_resource).value }
//    val painter = remember { BitmapPainter(imageBitmap.asImageBitmap()) }

    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .flip(horizontal = true) // Use .flip(horizontal = true) for horizontal flip
        // .flip(vertical = true) for vertical flip
    )
}
@Composable
fun FlippableImage2() {
//    val imageBitmap = remember { loadImageResource(R.drawable.your_image_resource).value }
//    val painter = remember { BitmapPainter(imageBitmap.asImageBitmap()) }

    // State to track the flip animation progress (0f: unflipped, 1f: fully flipped)
    val isFlippedState = remember { mutableStateOf(false) }
    val isFlipped by animateFloatAsState(targetValue = if (isFlippedState.value) 1f else 0f)

    // Track the update of the isFlippedState to prevent animation restarts during recomposition
    val updatedIsFlippedState = rememberUpdatedState(isFlipped)

    Image(
        painter =  painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (isFlipped) 0f else 1f) // Hide the unflipped image during animation
            .flip(horizontal = isFlipped) // Use .flip(horizontal = true) for horizontal flip
        // .flip(vertical = true) for vertical flip
    ) {
        // Listen for click events to trigger the flip animation
        it.onClick {
            // Toggle the isFlippedState to trigger the animation
            isFlippedState.value = !updatedIsFlippedState.value
        }
    }
}*/
@Composable
fun FlippableImage(asdEnum: EnumConcentration) {
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
var removed = ArrayList<ConcentrationModel>()
@Composable
fun SpinningBox(
    asdEnum: EnumConcentration = EnumConcentration.DIAMOND, concentrationModels: ConcentrationModel,
    onClick: (Item: ConcentrationModel) -> Unit
) {
//    var rotationState by remember { mutableStateOf(0f) }
    var alphaValue by remember { mutableStateOf(0f) }
    // Animate the rotationState from 0f to 360f repeatedly
//    val rotation by animateFloatAsState(
//        targetValue = rotationState, animationSpec = repeatable(
//            iterations = 1, animation = tween(10)
//        )
//    )

    var isFlipped by remember { mutableStateOf(concentrationModels.IsFlipped) }

    LaunchedEffect(Unit) {
//        repeat(180) { count ->
//        delay(5000L)

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
            id = if ((isFlipped && checkListAns.isNotEmpty()) || (isFlipped && removed.isEmpty())) {
                concentrationCheckStringReturnDrawable(asdEnum)
            } else {
                concentrationCheckStringReturnDrawable(EnumConcentration.BACK)
            }
        ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = concentrationCheckStringReturnDrawable(EnumConcentration.FRONT)),
                    contentScale = ContentScale.FillBounds
                )
                .clickable(
                    enabled = (!isFlipped && !removed.contains(concentrationModels))
                ) {

                    if (checkListAns.isEmpty()) {
                        Log.d("123123", "SpinningBox: ")
                        checkListAns.add(asdEnum)
                        isFlipped = !isFlipped
                    } else if (checkListAns.contains(asdEnum) && (checkListAns.get(checkListAns.size - 1) == asdEnum)) {
                        Log.d("123123", "SpinningBox2: ")
                        checkListAns.add(asdEnum)
                        removed.add(concentrationModels)
                        if (checkListAns.size == 6) {


//                            startAgain = !startAgain
//                            isFlipped = false
                            checkListAns.clear()
                            onClick(concentrationModels)
                        } else {
                            isFlipped = !isFlipped
                        }
                    } else if (checkListAns.size % 2 == 0 && (checkListAns.get(checkListAns.size - 1) != asdEnum)) {
                        Log.d("123123", "SpinningBox3: ")
                        checkListAns.add(asdEnum)
                        isFlipped = !isFlipped
                    } else {
                        Log.d("123123", "SpinningBox4: ")
//                        checkListAns.clear()
//                        isFlipped = false
                    }
                        totalGameAnswersConcen++

//                    onClick(concentrationModels)
                })
//                .graphicsLayer(rotationY = rotationY))
    }

}

@Composable
fun SpinningBox2(
    asdEnum: EnumConcentration = EnumConcentration.DIAMOND, concentrationModels: ConcentrationModel,
    onClick: (Item: ConcentrationModel) -> Unit
) {
//    var rotationState by remember { mutableStateOf(0f) }
    var alphaValue by remember { mutableStateOf(0f) }
    // Animate the rotationState from 0f to 360f repeatedly
//    val rotation by animateFloatAsState(
//        targetValue = rotationState, animationSpec = repeatable(
//            iterations = 1, animation = tween(10)
//        )
//    )
    var isFlipped by remember { mutableStateOf(true) }

//    var startAgain by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
//        repeat(180) { count ->
//        delay(5000L)
//            colorStateList.add(colorState)
//            rotationState = rotationState + 1

//        isFlipped = false
//        }
    }


//    val imageRes = if (isFlipped) R.drawable.your_flipped_image else R.drawable.your_unflipped_image
//    val imageBitmap = painterResource(id = imageRes).value.asImageBitmap()

    val rotationY: Float by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = ""
    )
    Surface(
        color = BirdColor4,
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .graphicsLayer(rotationY = rotationY)
    ) {
        // Content of the spinning box (optional)
//        RotatableImage(rotation)
//        FlippableImage(asdEnum)
        Image(painter = painterResource(
            id = if ((isFlipped && checkListAns.isNotEmpty()) || (isFlipped && removed.isEmpty())) {
                concentrationCheckStringReturnDrawable(asdEnum)
            } else {
                concentrationCheckStringReturnDrawable(asdEnum)
//                concentrationCheckStringReturnDrawable(EnumConcentration.BACK)
            }
        ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = concentrationCheckStringReturnDrawable(EnumConcentration.FRONT)),
                    contentScale = ContentScale.FillBounds
                )
                .clickable(
                    enabled = if ((!isFlipped && !removed.contains(concentrationModels)) && false) {
                        true
                    } else {
                        false
                    }
                ) {

                    if (checkListAns.isEmpty()) {
                        Log.d("123123", "SpinningBox: ")
                        checkListAns.add(asdEnum)
                        isFlipped = !isFlipped
                    } else if (checkListAns.contains(asdEnum) && (checkListAns.get(checkListAns.size - 1) == asdEnum)) {
                        Log.d("123123", "SpinningBox2: ")
                        checkListAns.add(asdEnum)
                        removed.add(concentrationModels)
                        if (checkListAns.size == 6) {


//                            startAgain = !startAgain
//                            isFlipped = false
                            checkListAns.clear()
                            onClick(concentrationModels)
                        } else {
                            isFlipped = !isFlipped
                        }
                    } else if (checkListAns.size % 2 == 0 && (checkListAns.get(checkListAns.size - 1) != asdEnum)) {
                        Log.d("123123", "SpinningBox3: ")
                        checkListAns.add(asdEnum)
                        isFlipped = !isFlipped
                    } else {
                        Log.d("123123", "SpinningBox4: ")
//                        checkListAns.clear()
//                        isFlipped = false
                    }

//                    onClick(concentrationModels)
                })
//                .graphicsLayer(rotationY = rotationY))
    }

}

fun concentrationCheckStringReturnDrawable(str: EnumConcentration): Int {
    return when (str) {
        EnumConcentration.STAR -> {

            R.drawable.shapes_star_concen
        }

        EnumConcentration.DIAMOND -> {

            R.drawable.diamond_concen
        }

        EnumConcentration.PENTAGON -> {

            R.drawable.shapes_penta_concen
        }

        EnumConcentration.BACK -> {

            R.drawable.purplecard_blank_concen
        }

        EnumConcentration.FRONT -> {

            R.drawable.cards_front_flip
        }


        else -> {
            R.drawable.diamond_concen
        }
    }
}

enum class EnumConcentration {
    STAR, DIAMOND, PENTAGON, BACK, FRONT
}

@Preview
@Composable
fun PreviewFlippedImage() {
//    FlippableImage()
//    SpinningBox()
}

