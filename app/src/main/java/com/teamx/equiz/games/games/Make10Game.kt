package com.teamx.equiz.games.games

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.delay
import java.util.LinkedList
import kotlin.random.Random


class Make10Game{}


//make 10

/*@Composable
fun Make10GameScreen() {
    val availableCards = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val selectedCards = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Make 10",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (card in availableCards) {
                Button(
                    onClick = {
                        if (selectedCards.sum() + card <= 10) {
                            selectedCards.add(card)
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCards.contains(card)) Color.Gray else Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(text = card.toString())
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
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Clear")
        }
    }
}

@Preview
@Composable
fun PreviewMake10GameScreen() {
    Make10GameScreen()
}*/

class SelectedCard(var index: Int, var cardValue: Int)

@Composable
fun Make10GameScreen(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit = { bool, rightAnswer, total -> }) {
    var availableCards = remember { mutableStateOf(generateArray()) }
    val selectedCards = remember { mutableStateListOf<Int>() }
    val selectedCardstr = remember { mutableStateListOf<String>() }
    val selectedCards2 = remember { mutableStateListOf<SelectedCard>() }
    var restart by remember { mutableStateOf(true) }
    var counter by remember { mutableStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }
    var rightGameAnswers = 0
    var wrongGameAnswers = 0

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
        content(true, rightGameAnswers10, (rightGameAnswers10 + totalGameAnswers10))
    }
    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(false, rightGameAnswers10, (rightGameAnswers10 + totalGameAnswers10))
            }
        }


    } else {
        if (restart) {
//        availableCards.value = availableCards.value.shuffled()
            counter += 1
            if (counter == 14) {
                counter = 0
            }

            selectedCards.clear()
            selectedCardstr.clear()
            selectedCards2.clear()
            restart = false
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.White),
        ) {
            Row(modifier = Modifier.background(color = Color(0xFF9F81CA))) {

                BackButton(onClick = { content(false,0,0) }
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

            making10Game()
            /*Column(
                modifier = Modifier
                    .fillMaxSize()
                     ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Make 10",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    availableCards.value.get(counter).forEachIndexed { index, card ->

                        Box(modifier = Modifier
                            .padding(8.dp)
                            .width(72.dp)
                            .height(92.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                var checker = false
                                //
                                if (selectedCards.contains(card)) {
                                    selectedCards2.forEach { selectedCard ->
                                        if (card == selectedCard.cardValue) {
                                            checker = selectedCard.index == index
                                        }
                                    }
                                    if (selectedCards.sum() + card <= 10 && !checker) {
                                        if (selectedCards.sum() + card == 10) {
                                            restart = true
                                            selectedCards.add(card)
                                            selectedCardstr.add("$card@$index")
                                            selectedCards2.add(SelectedCard(index, card))
                                            return@clickable
                                        }
                                        selectedCards.add(card)
                                        selectedCardstr.add("$card@$index")
                                        selectedCards2.add(SelectedCard(index, card))

                                    }
                                } else {
                                    if (selectedCards.sum() + card <= 10) {
                                        if (selectedCards.sum() + card == 10) {
                                            restart = true
                                            selectedCards.add(card)
                                            selectedCardstr.add("$card@$index")
                                            selectedCards2.add(SelectedCard(index, card))
                                            return@clickable
                                        }
                                        selectedCardstr.add("$card@$index")
                                        selectedCards.add(card)
                                        selectedCards2.add(SelectedCard(index, card))

                                    }
                                }

                            }
                            .background(
                                if (selectedCardstr.contains("$card@$index")) {
//                            if (selectedCards.contains(card)) {

//                                    if (selectedCards2[selectedCards.indexOf(card)].index == index) {
                                    BirdColor3*//*} else {
                                    BirdColor1
                                }*//*

                                } else {
                                    BirdColor1
                                }
                            )
//                        .border(BorderStroke(1.dp, Color.Black))
                            , contentAlignment = Alignment.Center
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (selectedCards.contains(card)) Color.Gray else Color.White
//                    ),
//                    border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Text(text = card.toString(), fontSize = 26.sp, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    color = Color.White,
                    text = "Selected Cards: ${selectedCards.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

               *//* Button(
                    onClick = { selectedCards.clear() }, modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Clear", color = Color.White)
                }*//*
            }*/
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

@Preview
@Composable
fun PreviewMake10GameScreen() {
    Make10GameScreen() { bool, rightAnswer, total -> }
}
//make 10


/**
fun generateArray(): Array<Array<Int>> {
val size = 25
val array = Array(size) { Array(3) { 0 } }

var count = 0

for (i in 1..10) {
for (j in 1..10) {
for (k in 1..10) {
if (i + j + k == 10 && count < size) {
array[count][0] = i
array[count][1] = j
array[count][2] = k
count++
}
}
}
}

return array
}

fun main() {
val array = generateArray()

println("Generated 2D Array:")
for (i in array) {
println(i.joinToString(", "))
}
}


//2items 10

import kotlin.random.Random
fun generateArray(): Array<Array<Int>> {
val size = 75
val array = Array(size) { Array(3) { 0 } }

var count = 0

for (i in 1..10) {
for (j in 1..10) {
for (k in 1..10) {
if (i + j == 10 && count < size) {
array[count][0] = i
array[count][1] = j
array[count][2] = Random.nextInt(1, 10) // Generate a random number between 1 and 10
count++
}
}
}
}

return array
}

fun main() {
val array = generateArray()

println("Generated 2D Array:")
for (i in array) {
println(i.joinToString(", "))
}
}


fun generateArray(): Array<Array<Int>> {
val size = 15
val array = Array(size) { Array(3) { 0 } }

var count = 0

for (i in 1..10) {
for (j in 1..10) {
for (k in 1..10) {
if (i + j + k == 10 && count < size) {
array[count][0] = i
array[count][1] = j
array[count][2] = k
count++
}
}
if (i + j == 10 && count < size) {
array[count][0] = i
array[count][1] = j
array[count][2] = Random.nextInt(1, 10)
count++
}
}
}

return array
}

fun main() {
val array = generateArray()

println("Generated 2D Array:")
for (i in array) {
println(i.joinToString(", "))
}
}

//




 * */

fun generateArray(): Array<Array<Int>> {
    val size = 25
    val array = Array(size) { Array(3) { 0 } }

    var count = 0

    for (i in 1..10) {
        for (j in 1..10) {
            for (k in 1..10) {
                if (i + j + k == 10 && count < size) {
                    array[count][0] = i
                    array[count][1] = j
                    array[count][2] = k
                    count++
                }
            }
            if (i + j == 10 && count < size) {
                array[count][0] = i
                array[count][1] = j
                array[count][2] = Random.nextInt(1, 10)
                count++
            }
        }
    }

    return array
}

fun main() {
    val array = generateArray()

    println("Generated 2D Array:")
    for (i in array) {
        println(i.joinToString(", "))
    }
}

var linkListAddictmake1067 = LinkedList<Int>()
var linkListAddictmake1067Checker = LinkedList<Int>()

@Preview
@Composable
fun making10Game() {
    val scrollState = LazyListState()
    var columnValue by remember {
        mutableIntStateOf(7)
    }
    var indexValueForArr by remember {
        mutableIntStateOf(0)
    }
    var randNumber by remember { mutableStateOf(10) }
    var availableCards by remember { mutableStateOf(generateArray()) }
    var addingValue by remember {
        mutableStateOf(false)
    }
    var changeable by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(changeable) {
        availableCards = generateArray()
        delay(100)
        indexValueForArr++
        addingValue = false
    }

    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            state = scrollState,
            reverseLayout = false,
            modifier = Modifier.wrapContentSize()
        ) {
            items(columnValue) { item ->
                Row(
                    modifier = Modifier.offset(y = (-(item) * 40).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { row ->
                        val index = item
                        val indexer = row * columnValue + item
                        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                            var addingValue2 by remember { mutableStateOf(false) }
                            Image(
                                modifier = Modifier

                                    .size((120).dp)
                                    .padding(horizontal = (40 - (index + 2) * 2).dp)
                                    .padding(top = ((40 - (index + 2) * 2)).dp)
                                    .clickable(item == (columnValue - 1)) {
                                        addingValue2 = !addingValue2

                                        if (linkListAddictmake1067Checker.contains(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            ) && !addingValue2
                                        ) {

                                            linkListAddictmake1067Checker.push(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            )
                                            val too =
                                                if (/*linkListAddictmake1067Checker.size <= linkListAddictmake1067.size &&*/ randNumber == linkListAddictmake1067Checker.sum()) {
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Right ${linkListAddictmake1067Checker.sum()}"
                                                    )
                                                    changeable = !changeable
                                                    linkListAddictmake1067Checker.clear()

                                                    rightGameAnswers10++
                                                    true
                                                } else {
                                                    totalGameAnswers10++
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Wrong ${linkListAddictmake1067Checker.sum()}"
                                                    )
                                                    false
                                                }



                                            Log.d(
                                                "123123",
                                                "AnimatedObject2:$availableCards.value.get(item).get(row) ::$availableCards.value.get(item).get(row) "
                                            )
                                            return@clickable
                                        } else if (!linkListAddictmake1067Checker.contains(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            )
                                        ) {

                                            linkListAddictmake1067Checker.push(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            )
                                            val too =
                                                if (/*linkListAddictmake1067Checker.size <= linkListAddictmake1067.size &&*/ randNumber == linkListAddictmake1067Checker.sum()) {
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Right ${linkListAddictmake1067Checker.sum()}"
                                                    )
                                                    changeable = !changeable
                                                    linkListAddictmake1067Checker.clear()

                                                    rightGameAnswers10++
                                                    true
                                                } else {
                                                    totalGameAnswers10++
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Wrong ${linkListAddictmake1067Checker.sum()}"
                                                    )
                                                    false
                                                }



                                            Log.d(
                                                "123123",
                                                "AnimatedObject2:$availableCards.value.get(item).get(row) ::$availableCards.value.get(item).get(row) "
                                            )
                                            return@clickable
                                        } else if (linkListAddictmake1067Checker.contains(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            ) && addingValue2
                                        ) {

                                            linkListAddictmake1067Checker.remove(
                                                availableCards
                                                    .get(indexValueForArr + item)
                                                    .get(row)
                                            )

                                            val too =
                                                if (/*linkListAddictmake1067Checker.size <= linkListAddictmake1067.size
                                                        && */randNumber == linkListAddictmake1067Checker.sum()
                                                ) {
                                                    linkListAddictmake1067Checker.clear()
                                                    changeable = !changeable
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Right ${linkListAddictmake1067Checker.sum()}"
                                                    )

                                                    rightGameAnswers10++
                                                    true
                                                } else {
                                                    totalGameAnswers10++
                                                    Log.d(
                                                        "123123",
                                                        "AddictGame:Wrong ${linkListAddictmake1067Checker.sum()}"
                                                    )
                                                    false
                                                }


                                        } else {

                                        }


                                    },
                                painter = painterResource(
                                    if (linkListAddictmake1067Checker.isNotEmpty() && addingValue2) {
                                        R.drawable.purple_card
                                    } else {
                                        addingValue2 = false
                                        R.drawable.white_card
                                    }
                                ),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier

                                    .wrapContentSize(),
                                text = "${
                                    availableCards.get(indexValueForArr + item).get(row)
                                }",
                                color = if (linkListAddictmake1067Checker.isNotEmpty() && addingValue2) {
                                    Color.White
                                } else {

                                    Color.Black
                                },
                                textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold,
                                fontSize = 36.sp
                            )
                        }
                    }
                }

            }
        }

    }

}

var rightGameAnswers10: Int = 0
var totalGameAnswers10: Int = 0