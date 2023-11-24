package com.teamx.equiz.games.games


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.teamx.equiz.games.ui.theme.BirdColor4
import java.util.LinkedList
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    GridList()
                }
            }
        }
    }
}

@Composable
fun GridList() {
    val items = listOf(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10",
        "Item 11",
        "Item 12",
        "Item 13",
        "Item 14",
        "Item 15",
        "Item 16",
        "Item 17",
        "Item 18",
        "Item 19",
        "Item 20",
        "Item 21",
        "Item 22",
        "Item 23",
        "Item 24",
        "Item 25",
        "Item 26",
        "Item 27",
        "Item 28",
        "Item 29",
        "Item 30"
    )
    var restart by remember { mutableStateOf(true) }
    val birdLinkListAdded by remember { mutableStateOf(LinkedList<BirdListItem>()) }
    for (i in 0..29) {
        val randomNum = Random.nextInt(0, 4)
        birdLinkListAdded.push(
            BirdListItem(
                i,
                valueColor = randomNum,
                12.dp,
                checkNumberReturnColor2(BirdEnum.values()[randomNum]),
            )
        )
    }

    var gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        gridState.scrollToItem(birdLinkListAdded.size/* - 1*/)
        gridState.scroll(MutatePriority.PreventUserInput, {})
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .rotate(180.0f),
        /*
                contentAlignment = Alignment.TopCenter*/

    ) {

//        LazyVerticalGrid(
//            state = gridState,
//            columns = GridCells.Fixed(3),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(420.dp)
//                .clickable(enabled = false, null, null, {}),
//        ) {
//            items(items.size) { index ->
//                GridItem(item = items[index])
//            }
//        }


        for (i in birdLinkListAdded) {


            Bird2AnimatedObject(i) { it ->

                val clickedCount = birdLinkListAdded.count { a -> a.valueColor == it.valueColor }
                var maxCount2 = birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                    .filter { it.value >= 0 }.values.max()
                if (clickedCount == maxCount2) {
                    restart = false
                    restart = true
                    iteration++


                }
//                Log.d("123123", "AscendingObjects:items $it")
                Log.d("123123",
                    "@@@@@$iteration@@@@" + birdLinkListAdded.groupingBy { it.valueColor }
                        .eachCount().filter { it.value >= 0 }.values.max()
                )

                val aia = birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                    .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                        .eachCount().filter { it.value >= 1 }.values.max())
                Log.d("123123",
                    "@@@@@" + BirdEnum.values()[birdLinkListAdded.groupingBy { it.valueColor }
                        .eachCount()
                        .filter { it.value >= 0 }.values.indexOf(birdLinkListAdded.groupingBy { it.valueColor }
                            .eachCount().filter { it.value >= 1 }.values.max())]
                ).toString()

                Log.d(
                    "123123",
                    "@@@@@" + birdLinkListAdded.groupingBy { it.valueColor }.eachCount()
                        .filter { it.value >= 0 }.values
                )
                Log.d(
                    "123123", "@@@@@" + BirdEnum.values()[it.valueColor].toString()
                )
                Log.d("123123",
                    "@@@@@" + birdLinkListAdded.count { a -> a.valueColor == it.valueColor })
            }
        }


    }

    // To stop the scroll
//    LaunchedEffect(gridState) {
//        gridState.scrollToItem(0) // Scroll to the top
//    }

}

@Composable
fun Bird2AnimatedObject(
    itemCompared: BirdListItem, onClick: (Item: BirdListItem) -> Unit
) {


    Surface(
//        color = itemCompared.color,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .width((70 - ((itemCompared.name / 3) * 4) / 2).dp)
            .height((80 - ((itemCompared.name / 3) * 4) / 2).dp)
            .offset(
                y = (((itemCompared.name / 3) + 0) * 60 - ((itemCompared.name / 3) * 4)  /*-((itemCompared.name % 3)*(itemCompared.name / 3))*8*/).dp/* if (itemCompared.name % 3 == 2) {
                    (((itemCompared.name / 3) + 2) * 90).dp
                } else if (itemCompared.name % 3 == 1) {
                    (((itemCompared.name / 3) + 2) * 90).dp
                } else {
                    (((itemCompared.name / 3) + 2) * 90).dp
                }*/


                /*if (itemCompared.name in 3..5) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else if (itemCompared.name in 6..8) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else if (itemCompared.name in 9..11) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else if (itemCompared.name in 12..14) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else {
                    ((itemCompared.name + 2) * 90).dp
                }*/,
                x = (((itemCompared.name % 3) + 0.78) * 90 + ((itemCompared.name / 3) * 4) / 2).dp/*if (itemCompared.name % 3 == 2) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else if (itemCompared.name % 3 == 1) {
                    (((itemCompared.name % 3) + 2) * 90).dp
                } else {
                    (((itemCompared.name % 3) + 2) * 90).dp
                }*//* if (itemCompared.name in 3..5) {
                    (2 * 70).dp
                } else if (itemCompared.name > 5) {
                    (3 * 80).dp
                }
                else {
                    (1 * 40).dp
                }*/
            )
//            .shadow(20.dp)

            .border(1.dp, Color.Gray.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
            .zIndex(2f)
            .clickable(
            ) {
                onClick(itemCompared)
            },
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .width((40 - ((itemCompared.name / 3) * 4)).dp)
                .height((40 - ((itemCompared.name / 3) * 4)).dp)
                .background(itemCompared.color)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .shadow(elevation = (100 - (itemCompared.name / 3) * 3).dp)
                    .rotate(180.0f),
                text = itemCompared.name.toString(),
                style = typography.bodySmall,
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default
            )
        }
    }


}

@Composable
fun GridItem(item: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .height(53.dp)
            .width(53.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BirdColor4), contentAlignment = Alignment.Center
    ) {
        Text(text = item, style = typography.bodySmall)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GridListPreview() {
    MaterialTheme {
        GridList()
    }
}


@Preview
@Composable
fun checkw() {
    MaterialTheme {

        letTry()
    }
}

var counter2 = 0

@Composable
fun letTry() {
    val firstArrCount = 3
    val secondArrCount = 4

    val mArr = arrayListOf<ArrayList<Int>>()
    val mArr2 = ArrayList<Int>()


    for (i in 1..firstArrCount) {
        mArr2.clear()
        for (j in 1..secondArrCount) {
            counter2++
            mArr2.add(Random.nextInt(1, 30))
            if (j == secondArrCount) {

                mArr.add(mArr2)
            }
        }
    }

    Log.d("TAG", "letTry:${mArr.size}  /*${mArr[0].size}*/ ")

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn() {
            items(mArr) { item2 ->
                var counte = 0
                LazyRow() {
                    items(mArr.get(counte++)) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .width(60.dp)
                                .height(80.dp)
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = it.toString())
                        }
                    }
                }
            }
        }
    }

}



