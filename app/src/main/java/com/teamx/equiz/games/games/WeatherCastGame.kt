package com.teamx.equiz.games.games

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R
import com.teamx.equiz.games.GamesUID
import com.teamx.equiz.games.games.ui_components.GameAlertingTime
import com.teamx.equiz.games.games.ui_components.TimeUpDialogCompose
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

var rightGameAnswersWeather = 0
var wrongGameAnswersWeather = 0

@Composable
fun WeatherCastGame(content: (bool: Boolean, rightAnswer: Int, totalAnswer: Int) -> Unit) {


    var isGameOver by remember { mutableStateOf(false) }
    var isAlert by remember { mutableStateOf(false) }

    var isTimeUp by remember { mutableStateOf(false) }

    var timeLeft by remember { mutableStateOf(20L) }
    val context = LocalContext.current

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
        content(true, rightGameAnswersWeather, wrongGameAnswersWeather)
          rightGameAnswersWeather = 0
          wrongGameAnswersWeather = 1
    }

    if (isTimeUp) {

        TimeUpDialogCompose() { i ->
            if (i) {
                isGameOver = true

            } else {
                content(
                    false,
                    rightGameAnswersWeather,
                    wrongGameAnswersWeather
                )
                  rightGameAnswersWeather = 0
                  wrongGameAnswersWeather = 1
            }
        }


    }else{
        MaterialTheme {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color(0xFFE1E1E1)),
            ) {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                ) {
                    Box(modifier = Modifier.height(48.dp).background(color = Color(0xFF9F81CA)),contentAlignment =Alignment.CenterStart)  {

                        BackButton(onClick = {content(false,0,0) }/*onContinueClicked*/)
                        Text(
                            text = "Weather Cast",
                            modifier = Modifier
                                .fillMaxWidth()

                                ,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 17.sp
                        )

                    }

                    weatherCastGamePlot()
                }


            }
        }
    }





}
lateinit var job: Job


@RequiresApi(Build.VERSION_CODES.M)
private fun timerStart() {
    val durationSeconds = 30
    var remainingSeconds = durationSeconds
    var progressTime = 100
//    mViewDataBinding.progressbar.progress = progressTime

    job = GlobalScope.launch {
        while (remainingSeconds > 0) {
            delay(1000) // Delay for 1 second
            remainingSeconds--
            progressTime - 3
//            mViewDataBinding.textView46545454.text = "$remainingSeconds"
//            mViewDataBinding.progressbar.progress = progressTime
        }

//        changeObserver()
    }
}

@Composable
fun weatherCastGamePlot() {
    val leftItems = (0..(2)).map {
        WeatherListItem(
            height = 70.dp, id = it, gamesUID = GamesUID.values()[it], color = if (it % 5 == 0) {
                Color(0xFFF44336).copy(alpha = 1f)
            } else if (it % 2 == 0) {
                Color(0xFF4CAF50).copy(alpha = 1f)
            } else {
                Color(0xFF00BCD4).copy(alpha = 1f)
            }, gameObject = EnumWeather.values()[it]/*if (it % 5 == 0) {
                EnumWeather.SUN
            } else if (it % 2 == 0) {
                EnumWeather.CLOUD
            } else {
                EnumWeather.DROP
            }*/
        )
    }
    val leftBoxes by remember { mutableStateOf(leftItems) }
    var imageCheckObj by remember { mutableStateOf<EnumWeather>(EnumWeather.CLOUD) }
    var imageRandom by remember { mutableStateOf<ImageVector?>(null) }
    var gameRand by remember { mutableStateOf<Int>(0) }
    var counter by remember { mutableStateOf<Int>(0) }

    when (imageCheckObj) {
        EnumWeather.SUN -> {
            imageRandom = ImageVector.vectorResource(id = androidx.core.R.drawable.ic_call_answer)

        }

        EnumWeather.CLOUD -> {

            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_background)
        }

        EnumWeather.DROP -> {
            imageRandom =
                ImageVector.vectorResource(id = androidx.core.R.drawable.ic_call_answer_video)
        }

        EnumWeather.CLOUD_SUN -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }

        EnumWeather.CLOUD_DROP -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }

        EnumWeather.DROP_SUN -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }

        EnumWeather.DROP_CLOUD -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }

        EnumWeather.SUN_DROP -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }

        EnumWeather.SUN_CLOUD -> {
            imageRandom = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*Image(
            modifier = Modifier.size(10.dp),
            painterResource(id = checkStringReturnDrawable(imageCheckObj.name.toString())),
            contentDescription = "image"
        )*/
        Image(
            modifier = Modifier.size(130.dp),
            painter = painterResource(id = checkStringReturnDrawable(imageCheckObj.name.toString())),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.size(1.dp),
            text = imageCheckObj.name,
            textAlign = TextAlign.Center
        )
        Row() {
            leftBoxes.forEach {
                weatherDrop(item = it) {
                    if (imageCheckObj.name == it.gameObject.name) {
//                        gameRand = Random.nextInt(0, 9)
//                        imageCheckObj = EnumWeather.values()[gameRand]
                        counter++
                        rightGameAnswersWeather++
                    } else if (!imageCheckObj.name.contains(it.gameObject.name) && imageCheckObj.name.contains("_")) {
//                        gameRand = Random.nextInt(0, 9)
//                        imageCheckObj = EnumWeather.values()[gameRand]
                        counter++
                        rightGameAnswersWeather++
                    }else{
                    }
                    gameRand = Random.nextInt(0, 9)
                    imageCheckObj = EnumWeather.values()[gameRand]
                        wrongGameAnswersWeather++

                /*else if (imageCheckObj.name == it.gameObject.name) {
//                        imageCheckObj = it.gameObject
                        gameRand = Random.nextInt(0, 9)
                        imageCheckObj = EnumWeather.values()[gameRand]
                        counter++
                    }*/
                }

            }
        }

    }

}



@Preview
@Composable
fun previewWeatherCastGame() {
    MaterialTheme {
        WeatherCastGame() {bool,rightAnswer,total ->}
    }
}

@Composable
fun weatherDrop(item: WeatherListItem, onClick: () -> Unit) {
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .padding(vertical = 120.dp, horizontal = 9.dp)
            .width(90.dp)
            .height(item.height)
            .clip(RoundedCornerShape(10.dp))

            .background(Color.White)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center


    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .background(Color.White)


        ) {
            Image(
                painter = painterResource(id = checkStringReturnDrawable(item.gameObject.name.toString())),
                contentDescription = ""
            )
            Text(
                text = item.gameObject.name.toString(), style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun checkStringReturnDrawable(str: String): Int {
    return when (str) {
        "SUN" -> {

            R.drawable.sun_red
        }

        "SUN_CLOUD" -> {

            R.drawable.sun_bule
        }

        "SUN_DROP" -> {

            R.drawable.sun_gray
        }

        "CLOUD" -> {

            R.drawable.cloud_blue
        }

        "CLOUD_SUN" -> {

            R.drawable.cloud_red
        }

        "CLOUD_DROP" -> {

            R.drawable.cloud_gray
        }

        "DROP" -> {
            R.drawable.drop_gray
        }

        "DROP_CLOUD" -> {
            R.drawable.drop_blue
        }

        "DROP_SUN" -> {
            R.drawable.drop_red
        }

        else -> {
            R.drawable.drop_red
        }
    }
}

@Keep
data class WeatherListItem(
    var id: Int,
    var height: Dp,
    var gameObject: EnumWeather,
    var color: Color,
    var gamesUID: GamesUID,
)

enum class EnumWeather {
    DROP, CLOUD, SUN, DROP_CLOUD, DROP_SUN, SUN_CLOUD, SUN_DROP, CLOUD_DROP, CLOUD_SUN
}


