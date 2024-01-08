package com.teamx.equiz.games.games

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose

@Composable
fun TouchTheNumPlusGame(content:   (bool:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit) {

    var isGameOver by remember { mutableStateOf(false) }
        var isAlert by remember { mutableStateOf(false) }
 rightGameAnswers = 1
 wrongGameAnswers = 1
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


        content(true, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))

    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswers, (rightGameAnswers + wrongGameAnswers))
            }
        }


    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFFE1E1E1)),
        ) {
            Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

            BackButton(onClick = {}/*onContinueClicked*/)
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