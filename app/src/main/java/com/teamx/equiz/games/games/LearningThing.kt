package com.teamx.equiz.games.games

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Main(content: @Composable () -> Unit) {

    /*  var visible by remember { mutableStateOf(true) }
  
      AnimatedVisibility(
          visible = visible,
          enter = fadeIn(),
          exit = fadeOut()
      ) {
          // Fade in/out the background and the foreground.
          Box(Modifier.fillMaxSize().background(Color.DarkGray)) {
              Box(
                  Modifier
                      .align(Alignment.Center)
                      .animateEnterExit(
                          // Slide in/out the inner box.
                          enter = slideInVertically(),
                          exit = slideOutVertically()
                      )
                      .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                      .background(Color.Blue)
              ) {
                  // Content of the notification…
              }
          }
      }*/
    val visible by remember { mutableStateOf(true) }
    AnimatedVisibility(visible = true,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            -fullWidth / 3
        } + fadeIn(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
            2000
        } + fadeOut()) {
        // Content that needs to appear/disappear goes here:
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight(200.dp)
//                .requiredWidth(200.dp)
                .background(Color.Red)
        ) {
            Text(text = "Hello")
        }
    }


}


/*@Preview
@Composable
fun PreviewMethod() {
    MaterialTheme {
        Main()
    }
}*/

