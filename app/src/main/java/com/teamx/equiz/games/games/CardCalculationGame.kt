package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.Keep
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlin.random.Random


enum class CardColor {
    GREEN, RED
}

private fun generateCards(number: Int): List<CardModel> {
    val numbers = mutableListOf<CardModel>()
    for (i in 1..number) {


        numbers.add(CardModel(Random.nextInt(1, 19), CardColor.values()[Random.nextInt(0, 2)]))

    }
    numbers.shuffle()

    return numbers
}

private fun generateCardsOption(answer: Int): List<OptionCards> {
    val numbers = mutableListOf<OptionCards>()
    val totalOptions = Random.nextInt(2, 5)
    numbers.add(OptionCards(answer, answer))
    val ranNum = (0..99).shuffled().take(totalOptions)
    for (i in 1..(totalOptions - 1)) {
//        val ranNum = Random.nextInt(1, 9);
        numbers.add(OptionCards(ranNum[i], answer))

    }
    numbers.shuffle()

    return numbers
}

var cards = listOf<CardModel>()
var optionCards = listOf<OptionCards>()
var answer = 0
val selectedCards = arrayListOf<Int>()
var rightGameAnswersCardCal = 0
var totalGameAnswersCardCal = 1

@Composable
fun CardCalculationGameScreen(content: (boolean: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {


    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var isTimeUp by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(20L) }

    var timerRunning by remember { mutableStateOf(true) }
    LaunchedEffect(true) {
//        generateOptions()

        // Start the timer
        object : CountDownTimer(timeLeft * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                  if (timerRunning) {
                    timeLeft = millisUntilFinished / 1000
                }
                if (timeLeft<5){
                    isAlert = true
                }
            }

            override fun onFinish() {
                isTimeUp = true
            }
        }.start()
    }


    if (isGameOver) {


        content(true, rightGameAnswersCardCal, totalGameAnswersCardCal)
        rightGameAnswersCardCal=0
        totalGameAnswersCardCal=1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersCardCal, totalGameAnswersCardCal)
                rightGameAnswersCardCal=0
                totalGameAnswersCardCal=1
            }
        }


    }else{
        var changeable by remember { mutableStateOf(true) }
        var gameStarted by remember { mutableStateOf(false) }
        if (changeable) {

            cards = generateCards(Random.nextInt(2, 4))
            selectedCards.clear()


            cards.forEach { card ->

                if (card.color == CardColor.GREEN) {
                    selectedCards.add(card.value)
                } else if (card.color == CardColor.RED) {
                    selectedCards.add(-card.value)
                }
            }
            answer = selectedCards.sum()
            optionCards = generateCardsOption(answer)

        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {
            Box(modifier = Modifier.height(48.dp).background(color = Color(0xFF9F81CA)),contentAlignment =Alignment.CenterStart)  {

                BackButton(onClick = { content(false,0,0) }
                )
                Text(
                    text = "Training",
                    modifier = Modifier
                        .fillMaxWidth()

                        ,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!gameStarted) {


                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        cards.forEach { card ->

                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(70.dp)
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Image(
                                    painter = painterResource(id = if (card.color == CardColor.GREEN) R.drawable.cardbule_cardcal else R.drawable.cardred_cardcal),
                                    contentDescription = ""
                                )
                                Text(
                                    text = card.value.toString(), color = Color.White,
                                    fontWeight = FontWeight.ExtraBold, fontSize = 36.sp
                                )

                                /*  Button(
                                      onClick = {
                                          *//*if (card.color == CardColor.GREEN) {
                                        selectedCards.add(card.value)
                                    } else if (card.color == CardColor.RED) {
                                        selectedCards.add(-card.value)
                                    }*//*

                                },
                                modifier = Modifier.fillMaxSize()
                                    .background(Color.Transparent),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent*//*if (card.color == CardColor.GREEN) neoGreen else neoRed*//*
                                ),
                                shape = RoundedCornerShape(8.dp),
//                                border = BorderStroke(1.dp, Color.Black),
                            ) {

                            }*/
                            }
                        }
                    }




                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /*resetGame()*/
                            changeable = false
                            gameStarted = true
                        }, modifier = Modifier
                            .padding(8.dp)
                            .width(120.dp)
                            .height(35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9F81CA)/*if (card.color == CardColor.GREEN) neoGreen else neoRed*/
                        )
                    ) {
                        Text(text = "Memorized")
                    }
                    /* Text(
                         text = "Selected Cards: ${selectedCards.joinToString(", ")}",
                         style = MaterialTheme.typography.bodyLarge
                     )*/

                    Log.d("123123", "CardCalculationGameScreen: ")


                } else {
                    /*  Text(
                          text = "Selected Cards: ${selectedCards.joinToString(", ")}",
                          style = MaterialTheme.typography.bodyLarge
                      )*/

                    Spacer(modifier = Modifier.height(16.dp))

                    /* Text(
                         text = "Choose the correct answer:", style = MaterialTheme.typography.bodyLarge
                     )*/

                    if (optionCards.size == 4) {
                        Column {
                            repeat(2) { column ->
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(2) { row ->
                                        val index = row * 2 + column
                                        val it = optionCards[index]
//                                    optionCards.forEach { it ->
                                        Box(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .width(70.dp)
                                                .height(130.dp)
                                                .clickable(enabled = true) {

                                                    totalGameAnswersCardCal++
                                                    if (checkAnswer(answer, it.value)) {
                                                        changeable = true
                                                        gameStarted = false
                                                        rightGameAnswersCardCal++
                                                    }

                                                },
                                            contentAlignment = Alignment.Center
                                        ) {

                                            Image(
                                                painter = painterResource(id = R.drawable.cardshow_cardcal),
                                                contentDescription = ""
                                            )
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),

                                                text = it.value.toString(),
                                                color = Color(0xff9F81CA),
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 26.sp,
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
                            optionCards.forEach { it ->
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .width(70.dp)
                                        .height(130.dp)
                                        .clickable(enabled = true) {
                                            totalGameAnswersCardCal++
                                            if (checkAnswer(answer, it.value)) {
                                                changeable = true
                                                gameStarted = false
                                                rightGameAnswersCardCal++
                                            }

                                        },
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(modifier = Modifier.fillMaxHeight(),
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
                                               if (checkAnswer(answer, it.value)) {
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
            if (isAlert) {
                GameAlertingTime()
            }
        }
    }







}

@Keep
data class CardModel(val value: Int, val color: CardColor)


@Keep
data class OptionCards(val value: Int, val rightAnswer: Int)

//@Composable
fun checkAnswer(actualAnswer: Int, comparedAnswer: Int): Boolean {

    val isCorrect = comparedAnswer == actualAnswer
    val resultMessage = if (isCorrect) "Correct!" else "Wrong!"

    println(resultMessage)

    return isCorrect
    // Display the result message or perform other actions based on the answer check
    // For example, you can show a toast message, update the score, or navigate to the next level.
}

//@Composable
fun resetGame() {
    // Reset the game state
    // For example, clear the selected cards and generate new cards
}

@Preview
@Composable
fun PreviewCardCalculationGameScreen() {
    CardCalculationGameScreen() {
        bool,rightAnswer,total ->
    }
}
//calculations


fun returnCardBg() {}