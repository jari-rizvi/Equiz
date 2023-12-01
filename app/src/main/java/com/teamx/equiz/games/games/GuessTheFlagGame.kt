package com.teamx.equiz.games.games


import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teamx.equiz.R


@Composable
fun GuessTheFlagGame(content: @Composable () -> Unit={}) {
    var score by remember { mutableStateOf(0) }
    var currentFlagIndex by remember { mutableStateOf(0) }
    var guessedCountry by remember { mutableStateOf(TextFieldValue()) }
    var options by remember { mutableStateOf(listOf<String>()) }
    var isOptionSelected by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(60L) }
    var timerRunning by remember { mutableStateOf(true) }

    val flags = listOf(
        R.drawable.usa_flag,
        R.drawable.uk_flag,
        R.drawable.france_flag
        // Add more flags as needed
    )

    fun generateOptions() {
        val correctCountry = getCountryName(currentFlagIndex)
        val tempList = mutableListOf(correctCountry)

        while (tempList.size < 3) {
            val randomCountry = getCountryName((0 until flags.size).random())
            if (!tempList.contains(randomCountry)) {
                tempList.add(randomCountry)
            }
        }

        options = tempList.shuffled()
    }

    fun checkAnswer() {
        if (guessedCountry.text.equals(getCountryName(currentFlagIndex), ignoreCase = true)) {
            score++
        }
        currentFlagIndex++
        if (currentFlagIndex >= flags.size) {
            currentFlagIndex = 0
        }
        guessedCountry = TextFieldValue()
        generateOptions()
        isOptionSelected = false
    }

    LaunchedEffect(true) {
        generateOptions()

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

    LaunchedEffect(guessedCountry.text) {
        if (isOptionSelected) {
            checkAnswer()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = flags[currentFlagIndex]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )

        options.forEach { country ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        guessedCountry = TextFieldValue(country)
                        isOptionSelected = true
                    }
            ) {
                RadioButton(
                    selected = guessedCountry.text.equals(country, ignoreCase = true),
                    onClick = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Text(text = country)
            }
        }

        Button(
            onClick = { checkAnswer() },
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text("Next Flag")
                Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
            }
        }

        Text("Score: $score", style = MaterialTheme.typography.bodySmall)

        if (isGameOver) {
            Dialog(
                onDismissRequest = { isGameOver = false },

                content = {
                    Text("Game Over")
                    Text("Your score is $score")
                    Button(
                        onClick = {
                            score = 0
                            currentFlagIndex = 0
                            guessedCountry = TextFieldValue()
                            generateOptions()
                            isGameOver = false
                            timeLeft = 60
                            timerRunning = true
                        }
                    ) {
                        Text("Retry")
                    }
                }
            )
        }
    }
}

fun getCountryName(index: Int): String {
    return when (index) {
        0 -> "United States"
        1 -> "United Kingdom"
        2 -> "France"
        // Add more country names as needed
        else -> ""
    }
}

@Preview(showBackground = true)
@Composable
fun FlagGamePreview() {
    GuessTheFlagGame()
}
