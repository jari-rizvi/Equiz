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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose

var rightGameAnswersGuess = 1
var wrongGameAnswersGuess = 1

@Composable
fun GuessTheFlagGame(content: (boo: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {
    var score by remember { mutableStateOf(0) }
    var currentFlagIndex by remember { mutableStateOf(0) }
    var guessedCountry by remember { mutableStateOf(TextFieldValue()) }
    var options by remember { mutableStateOf(listOf<String>()) }
    var isOptionSelected by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }


    var timeLeft by remember { mutableStateOf(20L) }
    var timerRunning by remember { mutableStateOf(true) }
    var isTimeUp by remember { mutableStateOf(false) }


    /*val flags = listOf(
        R.drawable.usa_flag,
        R.drawable.uk_flag,
        R.drawable.uk_flag,
        R.drawable.france_flag
        // Add more flags as needed
    )*/
    val flags = listOf(
        R.drawable.brazil_31,
        R.drawable.canada_08,
        R.drawable.china_17,
        R.drawable.colombia_39,
        R.drawable.denmark_30,
        R.drawable.egypt_48,
        R.drawable.finland_37,
        R.drawable.france_23,
        R.drawable.georgia_44,
        R.drawable.germany_16,
        R.drawable.greece_47,
        R.drawable.iceland_50,
        R.drawable.india_24,
        R.drawable.indonesia_33,
        R.drawable.iran_34,
        R.drawable.iraq_35,
        R.drawable.ireland_38,
        R.drawable.japan_25,
        R.drawable.kuwait_36,
        R.drawable.malaysia_41,
        R.drawable.mexico_42,
        R.drawable.morocco_22,
        R.drawable.netherland_20,
        R.drawable.new_zealand_11,
        R.drawable.nigeria_49,
        R.drawable.norway_10,
        R.drawable.oman_18,
        R.drawable.pakistan_02,
        R.drawable.portugal_46,
        R.drawable.qatar_09,
        R.drawable.saudi_arabia_28,
        R.drawable.singapore_27,
        R.drawable.south_africa_19,
        R.drawable.spain_21,
        R.drawable.srilanka_06,
        R.drawable.sweden_12,
        R.drawable.switzerland_13,
        R.drawable.syria_14,
        R.drawable.thailand_43,
        R.drawable.turkey_07,
        R.drawable.uae_04,
        R.drawable.uk_05,
        R.drawable.usa_03,
        R.drawable.zimbabwe_26,
        R.drawable.argentina_32,
        R.drawable.australia_29,
        R.drawable.bangladesh_15,
        R.drawable.belgium_45

    )

    fun generateOptions() {
        val correctCountry = getCountryName(currentFlagIndex)
        val tempList = mutableListOf(correctCountry)

        while (tempList.size < 4) {
            val randomCountry = getCountryName((0 until flags.size).random())
            if (!tempList.contains(randomCountry)) {
                tempList.add(randomCountry)
            }
        }

        options = tempList.shuffled()
    }

    fun checkAnswer() {
        if (guessedCountry.text.equals(getCountryName(currentFlagIndex), ignoreCase = true)) {
            rightGameAnswersGuess++
            score++
        } else {
            wrongGameAnswersGuess++
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
                if (timeLeft<5){
                    isAlert = true
                }
            }

            override fun onFinish() {
                isTimeUp = true
            }
        }.start()
    }


    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(
                    false,
                    rightGameAnswersGuess,
                    (rightGameAnswersGuess + wrongGameAnswersGuess)
                )
            }
        }


    }

    LaunchedEffect(guessedCountry.text) {
        if (isOptionSelected) {
            checkAnswer()
        }
    }

    Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* Image(
             painter = painterResource(id = flags[currentFlagIndex]),
             contentDescription = null,
             contentScale = ContentScale.Crop,
             modifier = Modifier
                 .size(200.dp)
                 .padding(16.dp)
         )*/
        Text(
            text = getCountryName(currentFlagIndex), fontWeight = FontWeight.ExtraBold,
            fontSize = 23.sp
        )

        /* options.forEach { country ->
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
         }*/

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
                    repeat(2) { column ->
                        val index = row * 2 + column
                        if (options.isNotEmpty()) {
                            val country = options.get(index)
                            Log.d("123123", "AddictGame:$index ")/*counter*/
                            /*   RadioButton(
                                   selected = guessedCountry.text.equals(country, ignoreCase = true),
                                   onClick = null,
                                   modifier = Modifier
                                       .padding(end = 8.dp).clickable {
                                           guessedCountry = TextFieldValue(country)
                                           isOptionSelected = true
                                       }
                               )
                               Text(text = country)*/
                            Image(
                                painter = painterResource(id = flags[index]),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(6.dp)
                                    .clickable {
                                        guessedCountry = TextFieldValue(country)
                                        isOptionSelected = true
                                    }
                            )
                        }

                    }
                }
            }
            /*LaunchedEffect(Unit) {
                delay(1300)
//                gameStarted = false
//                changable = true
            }*/
        }


        /*Button(
            onClick = { checkAnswer() },
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text("Next Flag")
                Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
            }
        }*/

        Text("Score: $score", style = MaterialTheme.typography.bodySmall)

        if (isGameOver) {
            content(true, rightGameAnswersGuess, (rightGameAnswersGuess + wrongGameAnswersGuess))
         /*   Dialog(
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
            )*/
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

/*fun getCountryName(index: Int): String {
    return when (index) {
        0 -> "United States"
        1 -> "United Kingdom"
        2 -> "United Kingdom2"
        3 -> "France"
        // Add more country names as needed
        else -> ""
    }
}*/

fun getCountryName(index: Int): String {
    return when (index) {
        0 -> "Brazil"
        1 -> "Canada"
        2 -> "China"
        3 -> "Colombia"
        4 -> "Denmark"
        5 -> "Egypt"
        6 -> "Finland"
        7 -> "France"
        8 -> "Georgia"
        9 -> "Germany"
        10 -> "Greece"
        11 -> "Iceland"
        12 -> "India"
        13 -> "Indonesia"
        14 -> "Iran"
        15 -> "Iraq"
        16 -> "Ireland"
        17 -> "Japan"
        18 -> "Kuwait"
        19 -> "Malaysia"
        20 -> "Mexico"
        21 -> "Morocco"
        22 -> "Netherlands"
        23 -> "New Zealand"
        24 -> "Nigeria"
        25 -> "Norway"
        26 -> "Oman"
        27 -> "Pakistan"
        28 -> "Portugal"
        29 -> "Qatar"
        30 -> "Saudi Arabia"
        31 -> "Singapore"
        32 -> "South Africa"
        33 -> "Spain"
        34 -> "Sri Lanka"
        35 -> "Sweden"
        36 -> "Switzerland"
        37 -> "Syria"
        38 -> "Thailand"
        39 -> "Turkey"
        40 -> "United Arab Emirates"
        41 -> "United Kingdom"
        42 -> "United States"
        43 -> "Zimbabwe"
        44 -> "Argentina"
        45 -> "Australia"
        46 -> "Bangladesh"
        47 -> "Belgium"
        // Add more country names as needed
        else -> ""
    }
}

@Preview
@Composable
fun FlagGamePreview() {
    GuessTheFlagGame() { _, _, _ -> }
}


val flags = listOf(
    R.drawable.brazil_31,
    R.drawable.canada_08,
    R.drawable.china_17,
    R.drawable.colombia_39,
    R.drawable.denmark_30,
    R.drawable.egypt_48,
    R.drawable.finland_37,
    R.drawable.france_23,
    R.drawable.georgia_44,
    R.drawable.germany_16,
    R.drawable.greece_47,
    R.drawable.iceland_50,
    R.drawable.india_24,
    R.drawable.indonesia_33,
    R.drawable.iran_34,
    R.drawable.iraq_35,
    R.drawable.ireland_38,
    R.drawable.japan_25,
    R.drawable.kuwait_36,
    R.drawable.malaysia_41,
    R.drawable.mexico_42,
    R.drawable.morocco_22,
    R.drawable.netherland_20,
    R.drawable.new_zealand_11,
    R.drawable.nigeria_49,
    R.drawable.norway_10,
    R.drawable.oman_18,
    R.drawable.pakistan_02,
    R.drawable.portugal_46,
    R.drawable.qatar_09,
    R.drawable.saudi_arabia_28,
    R.drawable.singapore_27,
    R.drawable.south_africa_19,
    R.drawable.spain_21,
    R.drawable.srilanka_06,
    R.drawable.sweden_12,
    R.drawable.switzerland_13,
    R.drawable.syria_14,
    R.drawable.thailand_43,
    R.drawable.turkey_07,
    R.drawable.uae_04,
    R.drawable.uk_05,
    R.drawable.usa_03,
    R.drawable.zimbabwe_26,
    R.drawable.argentina_32,
    R.drawable.australia_29,
    R.drawable.bangladesh_15,
    R.drawable.belgium_45

)


