package com.teamx.equiz.games

import LoadingAnimation
import android.os.Build
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teamx.equiz.games.games.*
import com.teamx.equiz.games.games.tetris.logic.SoundUtil
import com.teamx.equiz.games.games.tetris.logic.StatusBarUtil
import com.teamx.equiz.games.ui.theme.GameEquizApplicationTheme
import com.teamx.equiz.games.utils.grids
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.transparentStatusBar(this)
        SoundUtil.init(this)
        setContent {
            GameEquizApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

//                    HexaChainGameScreen() {}
//                    Navigation()
                    startUp(Modifier)

                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun startUp(modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(

        ) {
//            androidx.navigation.Navigation()/* Text(text = "Loading...", modifier = Modifier.padding(vertical = 48.dp))
//             LoadingAnimation()
//             grids()*/
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameEquizApplicationTheme {
        startUp(Modifier)

    }
}




//lazycolum//
@Composable
fun LazyListExample() {
    val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    LazyColumn {
        items(itemsList) { item ->
            ListItem(item = item)
        }
    }
}

@Composable
fun ListItem(item: String) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = androidx.compose.ui.Modifier.padding(8.dp)
    ) {
        Text(
            text = item, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold, color = Color.Black
            ), modifier = androidx.compose.ui.Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLazyListExample() {
    LazyListExample()
}

/*@ExperimentalFoundationApi
@Composable
fun LazyGridExample() {
    val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9")

    LazyVerticalGrid(
        cells = GridCells.Fixed(3)
    ) {
        items(itemsList) { item ->
            GridItem(item = item)
        }
    }
}

@Composable
fun GridItem(item: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        backgroundColor = Color.White,
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color.White
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewLazyGridExample() {
    LazyGridExample()
}*/
//

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "splashScreen") {
//
//        composable("splashScreen") {
//            SplashScreen(navController = navController)
//        }
//
//        composable("mainScreen") {
//            MainScreen(navController = navController)
//        }
//      /*  composable(GamesUID.DashboardScreen.name) {
////            MainScreen()
//        }*/
//
//        composable(GamesUID.AdditionScreen.name) {
//            AdditionAddictionGameMethod(){} /*{
//              *//*  ToolbarPreview(
//                    navController = navController, GamesUID.AdditionScreen.name
//                )*//*
//            }*/
//        }
//        composable(GamesUID.BirdWatchingScreen.name) {
//            BirdWatchingGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.BirdWatchingScreen.name
//                )
//            }
//        }
//        composable(GamesUID.BreakTheBlockScreen.name) {
//            BreakTheBlockGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.BreakTheBlockScreen.name
//                )
//            }
//        }
//        composable(GamesUID.ColorDeceptionScreen.name) {
//            TouchTheColorGameScreen() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.ColorDeceptionScreen.name
//                )*/
//            }
//        }
//      /*  composable(GamesUID.ColorSwitchScreen.name) {
//            ColorSwitchGameScreen() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.ColorSwitchScreen.name
//                )
//            }
//        }*/
//        composable(GamesUID.ConcentrationScreen.name) {
//            ConcentrationGame() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.ConcentrationScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.CardCalculationGameScreen.name) {
//            CardCalculationGameScreen() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.CardCalculationGameScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.FlickScreen.name) {
//            FlickGameScreen() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.FlickScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.FollowTheLeaderScreen.name) {
//            FollowTheLeaderGame() {
//               /* ToolbarPreview(
//                    navController = navController, GamesUID.FollowTheLeaderScreen.name
//                )*/
//            }
//        }
//      /*  composable(GamesUID.HexaChainScreen.name) {
//            HexaChainGameScreen() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.HexaChainScreen.name
//                )
//            }
//        }*/
//        composable(GamesUID.HighLowScreen.name) {
//            HighOrLowGameScreen(content = {
//                ToolbarPreview(
//                    navController = navController, GamesUID.HighLowScreen.name
//                )
//            }) {}
//        }
//        /*composable(GamesUID.LearningThingScreen.name) {
//            Main() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.LearningThingScreen.name
//                )
//            }
//        }*/
//        composable(GamesUID.MakeTenScreen.name) {
//            Make10GameScreen() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.MakeTenScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.MatchingScreen.name) {
//            MatchingStepGame(Modifier.padding(2.dp)) {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.MatchingScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.MissingPieceScreen.name) {
//            MissingPieceGameScreen() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.MissingPieceScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.OperationsScreen.name) {
////            GameScreen2() {
////                ToolbarPreview(
////                    navController = navController, GamesUID.OperationsScreen.name
////                )
////            }
//        }
//
//        composable(GamesUID.QuickEyeScreen.name) {
//            QuickEyeGame() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.QuickEyeScreen.name
//                )*/
//            }
//        }
//        composable(GamesUID.RainFallScreen.name) {
//            RainFallGame() {
//                /*ToolbarPreview(
//                    navController = navController, GamesUID.RainFallScreen.name
//                )*/
//            }
//        }
//       /* composable(GamesUID.ResultScreen.name) {
////            MainScreen()
//        }*/
//        composable(GamesUID.ReverseRpsScreen.name) {
//            ReverseRockPaperScissorsGameScreen() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.ReverseRpsScreen.name
//                )
//            }
//        }
//        composable(GamesUID.RapidSorting.name) {
//            RapidSortingGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.RapidSorting.name
//                )
//            }
//        }
//        composable(GamesUID.SimplicityScreen.name) {
//            ImplicityGameScreen() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.SimplicityScreen.name
//                )
//            }
//        }
//        composable(GamesUID.SpinningBlockScreen.name) {
//            SpinningBlockGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.SpinningBlockScreen.name
//                )
//            }
//        }
//        composable(GamesUID.TapTheColorScreen.name) {
//            TapTheColorGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.TapTheColorScreen.name
//                )
//            }
//        }
///*        composable(GamesUID.TenSecondScreen.name) {
////            MainScreen()
//        }*/
//        composable(GamesUID.TouchTheNumScreen.name) {
//            TouchTheNumGamePlus() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.TouchTheNumScreen.name
//                )
//            }
//        }
//        /*composable(GamesUID.TouchTheNumPlusScreen.name) {
//            TouchTheNumPlusGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.TouchTheNumPlusScreen.name
//                )
//            }
//        }*/
//        composable(GamesUID.UnfollowTheLeaderScreen.name) {
//            UnfollowTheLeaderGame() {
//                ToolbarPreview(
//                    navController = navController, GamesUID.UnfollowTheLeaderScreen.name
//                )
//            }
//        }
//        composable(GamesUID.WeatherCast.name) {
//            WeatherCastGame() {
//               /* ToolbarPreview(
//                    navController = navController, GamesUID.WeatherCast.name
//                )*/
//            }
//        }
//
//    }
//
//
//}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 500, /*delayMillis = 50, */easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )

        delay(3000L)
        navController.navigate("mainScreen")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .clip(RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center
    ) {


        Icon(
            modifier = Modifier
                .size(145.dp)
                .scale(scale.value),
            imageVector = Icons.Default.Spa,
            tint = Color.White,
            contentDescription = "SplashScreen"
        )

    }


}

@Composable
fun MainScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center,

        ) {

        Text(
            style = MaterialTheme.typography.bodySmall, text = ""
        )
        grids(navController)
    }

}

@Composable
fun GeneralScreen(text: String) {

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .clip(RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center,

        ) {

        Text(
            style = MaterialTheme.typography.bodySmall, text = text
        )

    }

}


enum class GamesUID {
    AdditionScreen, BirdWatchingScreen, BreakTheBlockScreen, ColorDeceptionScreen,  ConcentrationScreen, CardCalculationGameScreen, FlickScreen, FollowTheLeaderScreen,  HighLowScreen,  MakeTenScreen, MatchingScreen, MissingPieceScreen, OperationsScreen, QuickEyeScreen, RainFallScreen,  RapidSorting, ReverseRpsScreen, SimplicityScreen, SpinningBlockScreen, TapTheColorScreen,  TouchTheNumScreen,  UnfollowTheLeaderScreen, WeatherCast
}

