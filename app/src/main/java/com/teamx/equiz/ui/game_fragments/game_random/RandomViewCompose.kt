package com.teamx.equiz.ui.game_fragments.game_random

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.teamx.equiz.games.ui.theme.BirdColor4
import com.teamx.equiz.ui.fragments.dashboard.DashboardFragment.Companion.returnGameName
import com.teamx.equiz.ui.fragments.dashboard.DashboardFragment.Companion.returnImg
import com.teamx.equiz.ui.fragments.dashboard.GamesUID2
import kotlinx.coroutines.delay
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random


@Preview
@Composable
fun RandomViewCompose(roundName: String="", onClick: (position: String) -> Unit = {}) {


    var getListOfGames by remember { mutableStateOf(generateGameList()) }
    var randNum by remember { mutableIntStateOf(-1) }

    var stateChangeClicked by remember { mutableStateOf(false) }
    Log.d("123123", "RandomViewComposeHome: ")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffEFF4F9)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Round ${roundName.toInt()+1}",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(22.dp))
            TimerScreen {
                randNum = -1
                stateChangeClicked = !stateChangeClicked
                Log.d("123123", "RandomViewCompose: ")
            }
            Spacer(modifier = Modifier.size(62.dp))


            Text(
                text = "Game Select",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(12.dp))

            LazyRow() {
                itemsIndexed(getListOfGames) { it, I ->
                    var colorState by remember {
                        mutableStateOf(
                            Color.White
                        )
                    }
                    LaunchedEffect(stateChangeClicked) {
                        if (stateChangeClicked) {
                            if (randNum == -1) {
                                if (it == 0) {
                                    colorState = Color.Blue
                                    onClick(I.gameName)
                                }
                            } else {
                                if (randNum == it) {
                                    onClick(I.gameName)
                                }
                            }

                        }
                        delay(1000)
                        stateChangeClicked = false
//                        getListOfGames = generateGameList()
                        colorState = Color.White

                    }
                    /*LaunchedEffect(getListOfGames) {
                        colorState = Color.White
                    }*/

                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(
                                BorderStroke(
                                    2.dp, if (I.gameUsed) {
                                        Color.Blue
                                    } else {
                                        colorState
                                    }
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .size(110.dp)
                            .clickable {

                                randNum = it
                                colorState = Color.Blue
                                stateChangeClicked = !stateChangeClicked
//                            getListOfGames = generateGameList()
                            }
                            .background(Color.White, shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        val gameOb = getListOfGames.get(it)
                        val gameName = returnGameName(gameOb.gamesUID2.toString())
                        val Img = returnImg(gameOb.gamesUID2.toString())
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Image(
                                painter = painterResource(id = Img),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(text = "${gameName}", textAlign = TextAlign.Center)
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
}

fun generateGameList(): ArrayList<RandomGameModel> {
    val arr = arrayListOf<RandomGameModel>()
    val random = Random.nextInt(0, GamesUID2.values().size - 4)
    for (i in random..(random + 2)) {

        arr.add(
            RandomGameModel(
                GamesUID2.values().get(i).toString(),
                GamesUID2.values().get(i),
                false
            )
        )
    }
    return arr
}

@Preview
@Composable
fun TimerScreen(onClick: () -> Unit = {}) {
    var remainingTime by remember { mutableIntStateOf(10) }

    // Start the timer when the composable is first drawn
    DisposableEffect(Unit) {
        val timer = fixedRateTimer(period = 1000) {
            if (remainingTime == 0) {
                onClick()
                this.cancel()
            }
            if (remainingTime > 0) {
                remainingTime--
            }

        }

        onDispose {

            timer.cancel()
        }
    }

    Box(
        modifier = Modifier
            .size(185.dp)
            .background(color = Color.White, shape = RoundedCornerShape(112.dp))
            .padding(1.dp),
        contentAlignment = Alignment.Center,

        ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = (remainingTime.toFloat() / 10).toFloat(),
            color = BirdColor4,
            strokeWidth = 8.dp,

            )



        Text(
            modifier = Modifier.wrapContentSize(),
            text = " $remainingTime ", fontWeight = FontWeight.ExtraBold,
            fontSize = 29.sp,
            style = MaterialTheme.typography.bodySmall
        )
    }
}