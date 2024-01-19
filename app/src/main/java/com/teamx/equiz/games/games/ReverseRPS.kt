package com.teamx.equiz.games.games

import android.os.CountDownTimer
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.GamesUID
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlin.random.Random

class ReverseRPS {
}
//Rock paper scissor

/*enum class Symbol(val text: String, val dominantSymbols: List<Symbol>, val deficientSymbols: List<Symbol>) {
    ROCK("Rock", listOf(Symbol.SCISSORS), listOf(Symbol.PAPER)),
    PAPER("Paper", listOf(ROCK), listOf(SCISSORS)),
    SCISSORS("Scissors", listOf(PAPER), listOf(ROCK))
}

@Composable
fun ReverseRockPaperScissorsGameScreen() {
    var score by remember { mutableStateOf(0) }
    var currentSymbol by remember { mutableStateOf(generateSymbol()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reverse Rock Paper Scissors",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color = currentSymbol.color)
                .clickable {
                    val nextSymbol = generateSymbol()
                    val isCorrect = if (currentSymbol.color == Color.Green) {
                        nextSymbol in currentSymbol.dominantSymbols
                    } else {
                        nextSymbol in currentSymbol.deficientSymbols
                    }

                    if (isCorrect) {
                        score++
                    } else {
                        score = 0
                    }

                    currentSymbol = nextSymbol
                }
        ) {
            Text(
                text = currentSymbol.text,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

private fun generateSymbol(): Symbol {
    val symbols = Symbol.values()
    return symbols.random()
}

@Preview
@Composable
fun PreviewReverseRockPaperScissorsGameScreen() {
    ReverseRockPaperScissorsGameScreen()
}*//*
enum class Symbols {


    ROCK,
    PAPER,
    SCISSORS, ;
}

enum class Symbol(
    val text: String,
    val dominantSymbols: List<Symbols>,
    val deficientSymbols: List<Symbols>
) {
    ROCK("Rock", listOf(Symbols.PAPER), listOf(Symbols.SCISSORS)),
    PAPER("Paper", listOf(Symbols.SCISSORS), listOf(Symbols.ROCK)),
    SCISSORS("Scissors", listOf(Symbols.ROCK), listOf(Symbols.PAPER));

    val color: Color
        get() = if (dominantSymbols.isNotEmpty()) Color.Green else Color.Red
}


@Composable
fun ReverseRockPaperScissorsGameScreen(content: @Composable () -> Unit) {
    var score2 by remember { mutableStateOf(0) }
    var currentSymbol by remember { mutableStateOf(generateSymbol()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reverse Rock Paper Scissors",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = currentSymbol.text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            currentSymbol.dominantSymbols.forEach { symbol ->
                Button(
                    onClick = {
                        checkAnswer(symbol, { score2++ }, { score2-- }, { symbol ->
                            currentSymbol = symbol
                        })
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(125.dp), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = symbol.name)
                }
            }

            Button(
                onClick = {
                    checkAnswer(currentSymbol.text, { score2++ }, { score2-- }, { symbol ->
                        currentSymbol = symbol
                    })
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(125.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = currentSymbol.name)
            }


            currentSymbol.deficientSymbols.forEach { symbol ->
                Button(
                    onClick = {
                        checkAnswer(symbol, { score2++ }, { score2-- }, { symbol ->
                            currentSymbol = symbol
                        })
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(125.dp), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = symbol.name)
                }
            }
        }

        Text(
            text = "Score: $score2",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

private fun generateSymbol(): Symbol {
    val symbols = Symbol.values()
    return symbols.random()
}

private fun checkAnswer(
    chosenSymbol: Symbols,
    correctAction: () -> Unit,
    wrongAction: () -> Unit,
    changeAction: (symbol: Symbol) -> Unit
) {
    val nextSymbol = generateSymbol()
    val isCorrect = if (nextSymbol.color == Color.Green) {
        chosenSymbol in nextSymbol.dominantSymbols
    } else {
        chosenSymbol in nextSymbol.deficientSymbols
    }

    // Update score based on correctness
    if (isCorrect) {
//        score3++
        correctAction()
    } else {
        wrongAction()
//        score3--
//        score3 = 0
    }
    changeAction(nextSymbol)

//    currentSymbol = nextSymbol

}

private fun checkAnswer(
    chosenSymbol: String,
    correctAction: () -> Unit,
    wrongAction: () -> Unit,
    changeAction: (symbol: Symbol) -> Unit
) {
    val nextSymbol = generateSymbol()
    val isCorrect = nextSymbol.text == chosenSymbol

    // Update score based on correctness
    if (isCorrect) {
//        score3++
        correctAction()
    } else {
        wrongAction()
//        score3--
//        score3 = 0
    }
    changeAction(nextSymbol)

//    currentSymbol = nextSymbol

}
*/

var currentSymbol: Any? = null

/*
@Preview
@Composable
fun PreviewReverseRockPaperScissorsGameScreen() {
    ReverseRockPaperScissorsGameScreen()
}
*/

var rightGameAnswersRps = 0
var wrongGameAnswersRps = 0


@Composable
fun rpsCastGamePlot(content: (bool:Boolean, rightAnswer:Int, totalAnswer:Int) -> Unit={bool,rightAnswer,total ->}) {
    val leftItems = (0..(2)).map {
        rpsListItem(
            height = 70.dp, id = it, gamesUID = GamesUID.values()[it], color = if (it % 5 == 0) {
                Color(0xFFF44336).copy(alpha = 1f)
            } else if (it % 2 == 0) {
                Color(0xFF4CAF50).copy(alpha = 1f)
            } else {
                Color(0xFF00BCD4).copy(alpha = 1f)
            }, gameObject = EnumRPS.values().get(it)
        )
    }
    val leftBoxes by remember { mutableStateOf(leftItems) }
    var imageCheckObj by remember { mutableStateOf<EnumRPS>(EnumRPS.PAPER) }
    var imageRandom by remember { mutableStateOf<ImageVector?>(null) }
    var gameRand by remember { mutableStateOf<Int>(0) }
    var counter by remember { mutableStateOf<Int>(0) }



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


        content(true, rightGameAnswersRps, wrongGameAnswersRps)
        rightGameAnswersRps=0
        wrongGameAnswersRps=1

    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswersRps, wrongGameAnswersRps)
                rightGameAnswersRps=0
                wrongGameAnswersRps=1
            }
        }


    }else{
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
                    text = "Reverse RPS",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 17.sp
                )

            }
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(180.dp),
                    painter = painterResource(id = rpsCheckStringReturnDrawable(imageCheckObj)),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.size(1.dp),
                    text = imageCheckObj.name,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier
                    .wrapContentWidth()
                    .height(63.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    leftBoxes.forEach {
                        rpsDrop(item = it) {

                            wrongGameAnswersRps++
                            if (imageCheckObj.name.toString().contains("INV")) {
                                if (imageCheckObj == EnumRPS.INVPAPER && it.gameObject == EnumRPS.ROCK) {
                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                } else if (imageCheckObj == EnumRPS.INVSCISSOR && it.gameObject == EnumRPS.PAPER) {
                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                } else if (imageCheckObj == EnumRPS.INVROCK && it.gameObject == EnumRPS.SCISSOR) {

                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                }
                            } else {
                                if (imageCheckObj.equals(it.gameObject)) {

                                } else if (imageCheckObj == EnumRPS.PAPER && it.gameObject == EnumRPS.SCISSOR) {
                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                } else if (imageCheckObj == EnumRPS.SCISSOR && it.gameObject == EnumRPS.ROCK) {
                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                } else if (imageCheckObj == EnumRPS.ROCK && it.gameObject == EnumRPS.PAPER) {

                                    gameRand = Random.nextInt(0, 6)
                                    imageCheckObj = EnumRPS.values()[gameRand]
                                    counter++
                                    rightGameAnswersRps++
                                }
                            }


                            /*  if (imageCheckObj.name == it.gameObject.name) {
                                  gameRand = Random.nextInt(0, 6)
                                  imageCheckObj = EnumRPS.values()[gameRand]
                                  counter++
                              } else if (!imageCheckObj.name.contains(it.gameObject.name) && imageCheckObj.name.contains(
                                      "INV"
                                  )
                              ) {
                                  gameRand = Random.nextInt(0, 6)
                                  imageCheckObj = EnumRPS.values()[gameRand]
                                  counter++
                              } else if (imageCheckObj.name == it.gameObject.name) {
          //
                                  gameRand = Random.nextInt(0, 6)
                                  imageCheckObj = EnumRPS.values()[gameRand]
                                  counter++
                              }*/
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


@Composable
fun rpsDrop(item: rpsListItem, onClick: () -> Unit) {
    val context = LocalContext.current


    Box(
        modifier = Modifier

            .size(item.height)
            .clip(RoundedCornerShape(10.dp))
//            .background(item.color)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center


    ) {
        Column(

        ) {
            Image(
                painter = painterResource(id = rpsCheckStringReturnDrawable(item.gameObject)),
                contentDescription = ""
            )
            Text(
                text = item.gameObject.name.toString(), style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun rpsCheckStringReturnDrawable(str: EnumRPS): Int {
    return when (str) {
        EnumRPS.SCISSOR -> {

            R.drawable.scissorsbluerps
        }

        EnumRPS.PAPER -> {

            R.drawable.paperbluerps
        }

        EnumRPS.ROCK -> {

            R.drawable.rockbluerps
        }

        EnumRPS.INVSCISSOR -> {

            R.drawable.scissorsredrps
        }

        EnumRPS.INVROCK -> {

            R.drawable.rockredrps
        }

        EnumRPS.INVPAPER -> {

            R.drawable.paperredrps
        }


        else -> {
            R.drawable.paperredrps
        }
    }
}

@Keep
data class rpsListItem(
    var id: Int,
    var height: Dp,
    var gameObject: EnumRPS,
    var color: Color,
    var gamesUID: GamesUID,
)

enum class EnumRPS {
    SCISSOR, PAPER, ROCK, INVSCISSOR, INVROCK, INVPAPER
}

@Preview
@Composable
fun previewRPSCastGame() {
    MaterialTheme {
        rpsCastGamePlot(){bool,rightAnswer,total ->}
    }
}

@Composable
fun ReverseRockPaperScissorsGameScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .background(color = Color(0xFF9F81CA)), contentAlignment = Alignment.CenterStart
    ) {

        BackButton(onClick = {}/*onContinueClicked*/)
        Text(
            text = "Training",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 17.sp
        )

    }
    rpsCastGamePlot(){bool,rightAnswer,total ->}
}

//Rock paper scissor
