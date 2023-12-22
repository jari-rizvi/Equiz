package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import kotlin.random.Random

@Preview
@Composable
fun QuickEyeGame(content:  () -> Unit = {}) {
    var isGameOver by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(60L) }

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
            }

            override fun onFinish() {
                isGameOver = true
            }
        }.start()
    }


    if (isGameOver) {
//        content()
    }

    var restart by remember { mutableStateOf(true) }

//    var boxes by remember { mutableStateOf(generateBoxes()) }



    Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
        /* Box(
             modifier = Modifier
                 .padding(16.dp)

                 .size(300.dp)
         ) {

     //        for (i in boxes.indices) {
     //
     //
     //
     //        }

         }

             Image(
                 modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(),
                 painter = painterResource(id = R.drawable.iconbg),
                 contentDescription = "bg"
             )*/
        QuickCardCalculationGameScreen() { /*content() */ }
    }


}


enum class QuickCardColor {
    GREEN, RED
}

private fun generateCards(number: Int): List<QuickCardModel> {
    val numbers = mutableListOf<QuickCardModel>()
    for (i in 1..number) {

        val str = Random.nextInt(1, 90)

        numbers.add(
            QuickCardModel(
                if (Random.nextBoolean() && (str in 65..90)) {
                    str.toChar().toString()
                } else {
                    str.toString()
                },
                QuickCardColor.values()[Random.nextInt(0, 2)]
            )
        )

    }
    numbers.shuffle()

    return numbers
}

private fun quickGenerateCardsOption(quickAnswer: String): List<QuickOptionCards> {
    val numbers = mutableListOf<QuickOptionCards>()
    val totalOptions = 12
    numbers.add(QuickOptionCards(quickAnswer, quickAnswer))
    val ranNum = (1..99).shuffled().take(totalOptions)
    for (i in 1..(totalOptions - 1)) {
//        val ranNum = Random.nextInt(1, 9);

        if (Random.nextBoolean()) {
            if (Random.nextBoolean() && (ranNum[i] >= 65 && ranNum[i] <= 90)) {

                numbers.add(QuickOptionCards((ranNum[i].toChar()).toString(), quickAnswer))
            } else {
                numbers.add(QuickOptionCards(ranNum[i].toString(), quickAnswer))
            }
        } else {
            numbers.add(QuickOptionCards(0.toString(), quickAnswer))

        }

    }
    numbers.shuffle()

    return numbers
}

private var quickCards = listOf<QuickCardModel>()
private var quickOptionCards = listOf<QuickOptionCards>()
var quickAnswer = ""
val quickSelectedCards = arrayListOf<String>()

@Composable
fun QuickCardCalculationGameScreen(content: () -> Unit) {


    var changeable by remember { mutableStateOf(true) }
    var gameStarted by remember { mutableStateOf(false) }
    if (changeable) {

        quickCards = generateCards(Random.nextInt(1, 2))
        quickSelectedCards.clear()


        quickCards.forEach { card ->

            if (card.color == QuickCardColor.GREEN) {
                quickSelectedCards.add(card.value)
            } else if (card.color == QuickCardColor.RED) {
                quickSelectedCards.add(card.value)
            }
        }
        quickAnswer = quickSelectedCards.get(0)
        quickOptionCards = quickGenerateCardsOption(quickAnswer)
        changeable = false
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFE1E1E1)),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            if (!gameStarted) {


            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                quickCards.forEach { card ->

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(70.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(id = /*if (card.color == QuickCardColor.GREEN) R.drawable.cardbule_cardcal else*/ R.drawable.cardbule_cardcal),
                            contentDescription = ""
                        )
                        Text(
                            text = card.value.toString(), color = Color.White,
                            fontWeight = FontWeight.ExtraBold, fontSize = 36.sp
                        )


                    }
                }
            }


            /*    Button(
                    onClick = { *//*resetGame()*//*
                    changeable = false
                    gameStarted = true
                }, modifier = Modifier
                    .padding(8.dp)
                    .width(120.dp)
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9F81CA)*//*if (card.color == QuickCardColor.GREEN) neoGreen else neoRed*//*
                )
            ) {
                Text(text = "Memorized")
            }*/


            Log.d("123123", "CardCalculationGameScreen: ")


//            } else {


            Spacer(modifier = Modifier.height(16.dp))



            if (quickOptionCards.size == 12) {
                Row {
                    repeat(4) { column ->
                        Column(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            repeat(3) { row ->
                                val index = column * 3 + row
                                val it = quickOptionCards[index]
//                                    quickOptionCards.forEach { it ->
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 6.dp)
                                        .width(70.dp)
                                        .height(110.dp)
                                        .clickable(enabled = true) {

                                            if (checkQuickAnswer(
                                                    quickAnswer.toString(),
                                                    it.value.toString()
                                                )
                                            ) {
                                                changeable = true
                                                gameStarted = false
                                            }

                                        },
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = painterResource(
                                            id =
                                            if (it.value.toString() == "0") {
                                                R.drawable.cardcolor_quick
                                            } else {
                                                R.drawable.cardwhite_quick
                                            },

                                            ),
                                        contentDescription = ""
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),

                                        text = if (it.value.toString() == "0") {
                                            ""
                                        } else {
                                            it.value.toString()
                                        },
                                        color = Color.Black,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 29.sp,
                                        textAlign = TextAlign.Center
                                    )

                                }


                            }
                        }
                    }
                }
//                    }

            } else {


                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    quickOptionCards.forEach { it ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(70.dp)
                                .height(130.dp)
                                .clickable(enabled = true) {

                                    if (checkQuickAnswer(
                                            quickAnswer.toString(),
                                            it.value.toString()
                                        )
                                    ) {
                                        changeable = true
                                        gameStarted = false
                                    }

                                },
                            contentAlignment = Alignment.Center
                        ) {

                            Image(
                                modifier = Modifier.fillMaxHeight(),
                                painter = painterResource(id = R.drawable.cardshow_cardcal),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),

                                text = it.value.toString(),
                                color = Color(0xff9F81CA), fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp, textAlign = TextAlign.Center
                            )
                            /*   Button(
                                   onClick = {
                                       if (checkquickAnswer(quickAnswer, it.value)) {
                                           changeable = true
                                           gameStarted = false
                                       }
                                   },
                                   modifier = Modifier,
                                   colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                   shape = RoundedCornerShape(8.dp),
   //                                border = BorderStroke(1.dp, Color.Black)
                               ) {

                               }*/
                        }


                    }
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


private data class QuickCardModel(val value: String, val color: QuickCardColor)
private data class QuickOptionCards(val value: String, val rightquickAnswer: String)

//@Composable
private fun checkQuickAnswer(actualQuickAnswer: String, comparedQuickAnswer: String): Boolean {

    val isCorrect = comparedQuickAnswer == actualQuickAnswer

    Log.d("123123", "checkQuickAnswer: $isCorrect $comparedQuickAnswer $comparedQuickAnswer")

    val resultMessage = if (isCorrect) "Correct!" else "Wrong!"

    println(resultMessage)

    return isCorrect
}

//@Composable





