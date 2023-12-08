package com.teamx.equiz.games.games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamx.equiz.R
import com.teamx.equiz.games.ui.theme.neoGreen
import com.teamx.equiz.games.ui.theme.neoRed
import kotlin.random.Random

class CardCalculationGame {}

//calculations

/*enum class CardColor {
    GREEN, RED
}

@Composable
fun CardCalculationGameScreen() {
    val cards = listOf(
        Card(7, CardColor.GREEN),
        Card(2, CardneoRed)
    )
    val selectedCards = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Card Calculation",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cards.forEach { card ->
                Button(
                    onClick = {
                        if (card.color == CardColor.GREEN) {
                            selectedCards.add(card.value)
                        } else if (card.color == CardneoRed) {
                            selectedCards.add(-card.value)
                        }
                    },
                    modifier = Modifier.padding(8.dp).width(50.dp).height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (card.color == CardColor.GREEN) Color.Green else neoRed
                    ),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = card.value.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected Cards: ${selectedCards.joinToString(", ")}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { selectedCards.clear() },
            modifier = Modifier.padding(8.dp).width(50.dp).height(70.dp)
        ) {
            Text(text = "Clear")
        }
    }
}

data class Card(val value: Int, val color: CardColor)

@Preview
@Composable
fun PreviewCardCalculationGameScreen() {
    CardCalculationGameScreen()
}*/

enum class CardColor {
    GREEN, RED
}

private fun generateCards(): List<CardModel> {
    val numbers = mutableListOf<CardModel>()
    for (i in 1..Random.nextInt(2, 4)) {


        numbers.add(CardModel(Random.nextInt(1, 9), CardColor.values()[Random.nextInt(0, 2)]))

    }
    numbers.shuffle()

    return numbers
}

private fun generateCardsOption(answer: Int): List<OptionCards> {
    val numbers = mutableListOf<OptionCards>()
    val totalOptions = Random.nextInt(2, 4)
    numbers.add(OptionCards(answer, answer))
    val ranNum = (0..99).shuffled().take(totalOptions)
    for (i in 1..(totalOptions - 1)) {
//        val ranNum = Random.nextInt(1, 9);
        numbers.add(OptionCards(ranNum[i], answer))

    }
    numbers.shuffle()

    return numbers
}

@Composable
fun CardCalculationGameScreen(content:  () -> Unit) {
    val cards = generateCards()/*listOf(
        Card(7, CardColor.GREEN), Card(2, CardneoRed)
    )*/


    val selectedCards = remember { mutableStateListOf<Int>() }


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
        Text(
            text = "Card Calculation",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cards.forEach { card ->
                if (card.color == CardColor.GREEN) {
                    selectedCards.add(card.value)
                } else if (card.color == CardColor.RED) {
                    selectedCards.add(-card.value)
                }
                Button(
                    onClick = {
                        /*if (card.color == CardColor.GREEN) {
                            selectedCards.add(card.value)
                        } else if (card.color == CardColor.RED) {
                            selectedCards.add(-card.value)
                        }*/
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(50.dp)
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (card.color == CardColor.GREEN) neoGreen else neoRed
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(text = card.value.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected Cards: ${selectedCards.joinToString(", ")}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        val answer = selectedCards.sum()
        println("$answer")
        Text(
            text = "Choose the correct answer:", style = MaterialTheme.typography.bodyLarge
        )
        val optionCards = generateCardsOption(answer)

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            optionCards.forEach {

                Button(
                    onClick = { checkAnswer(answer, it.value) },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(70.dp)
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = it.value.toString(), color = Color.Gray)
                }
            }

            /*Button(
                onClick = { checkAnswer(answer, answer) },
                modifier = Modifier
                    .padding(8.dp)
                    .width(70.dp)
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
//                Text(text = answer.toString())
                Text(text = (answer).toString(), color = Color.Gray)
            }

            Button(
                onClick = { checkAnswer(answer, answer - 1) },
                modifier = Modifier
                    .padding(8.dp)
                    .width(70.dp)
                    .height(70.dp)
                    .width(50.dp)
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {

                Text(text = (answer - 1).toString(), color = Color.Gray)
            }*/
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { resetGame() }, modifier = Modifier
                .padding(8.dp)
                .height(70.dp)
        ) {
            Text(text = "Reset")
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

data class CardModel(val value: Int, val color: CardColor)
data class OptionCards(val value: Int, val rightAnswer: Int)

//@Composable
fun checkAnswer(actualAnswer: Int, comparedAnswer: Int) {

    val isCorrect = comparedAnswer == actualAnswer
    val resultMessage = if (isCorrect) "Correct!" else "Wrong!"

    println(resultMessage)
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
    CardCalculationGameScreen() {}
}
//calculations