package com.teamx.equiz.games.games.tetris

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamx.equiz.R
import com.teamx.equiz.games.games.tetris.logic.Direction

var runGame = true
@Composable
fun GameBody(
    clickable: Clickable = combinedClickable(),
    screen: @Composable () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFE4E5E7)
            )
    ) {
        Column(
            Modifier
                .wrapContentSize()
//            .background(Color.Black)
                .background(Color(0xFFE4E5E7)/*Color.White*/, RoundedCornerShape(10.dp))
                .padding(5.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {

            //Screen
            Box(
                Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
            ) {


                /*  Box(
                      modifier = Modifier
                          .align(Alignment.Center)
                          .size(330.dp, 400.dp)
                          .padding(top = 20.dp)
                          .background(Color.Black.copy(alpha = 0.8f))
                          .padding(5.dp)
                          .background(BodyColor)
                  )*/

                /*Box(
                    Modifier
                        .width(120.dp)
                        .height(45.dp)
                        .align(Alignment.TopCenter)
                        .background(BodyColor)
                ) {
                    Text(
                        stringResource(id = R.string.body_label),
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                }*/

                Box(
                    Modifier
                        .align(Alignment.Center)
                        .size(600.dp, 680.dp)
                        .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
                ) {
                    /*  Canvas(modifier = Modifier.fillMaxSize()) {
                          drawScreenBorder(
                              Offset(0f, 0f),
                              Offset(size.width, 0f),
                              Offset(0f, size.height),
                              Offset(size.width, size.height)
                          )
                      }*/

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .background(Color.White)
                    ) {
                        screen()
                    }
                }
            }


            /* Spacer(modifier = Modifier.height(20.dp))*/

            /* val SettingText = @Composable { text: String, modifier: Modifier ->
                 Text(
                     text, modifier = modifier,
                     color = Color.Black.copy(0.9f),
                     fontSize = 12.sp,
                     textAlign = TextAlign.Center
                 )
             }*/


            //Setting Button
            /*  Column(
                  modifier = Modifier
                      .padding(start = 40.dp, end = 40.dp)
              ) {
                  Row {
                      SettingText(stringResource(id = R.string.button_sounds), Modifier.weight(1f))
                      SettingText(stringResource(id = R.string.button_pause), Modifier.weight(1f))
                      SettingText(stringResource(id = R.string.button_reset), Modifier.weight(1f))
                  }
                  Spacer(modifier = Modifier.height(5.dp))
                  Row {

                      //SOUNDS
                      GameButton(
                          modifier = Modifier
                              .weight(1f)
                              .padding(start = 20.dp, end = 20.dp),
                          onClick = { clickable.onMute() },
                          size = SettingButtonSize
                      ) {}

                      //PAUSE
                      GameButton(
                          modifier = Modifier
                              .weight(1f)
                              .padding(start = 20.dp, end = 20.dp),
                          onClick = { clickable.onPause() },
                          size = SettingButtonSize
                      ) {}

                      //RESET
                      GameButton(
                          modifier = Modifier
                              .weight(1f)
                              .padding(start = 20.dp, end = 20.dp),
                          onClick = { clickable.onRestart() },
                          size = SettingButtonSize
                      ) {}

                  }
              }*/


            /* Spacer(modifier = Modifier.height(30.dp))*/


            //Game Button
//        val ButtonText = @Composable { modifier: Modifier,painter: Painter ->
//            Icon(
//                painter = painterResource(id = R.drawable.ic_launcher_background), modifier = modifier,
//
//            )
//        }

            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            ) {
                //DIRECTION BTN
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GameButton(
//                    Modifier.align(Alignment.TopCenter),
                        onClick = {
                            clickable.onRestart()
                        },
                        autoInvokeWhenPressed = false,
                        size = DirectionButtonSize,
                        painter = painterResource(R.drawable.tetris_icon)
                    )
                    GameButton(
//                    Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            //starting the game

                            clickable.onRotate()
                        },
                        autoInvokeWhenPressed = false,
                        size = DirectionButtonSize,
                        painter = painterResource(R.drawable.rotatepink_tetris)
                    )/* {
     //                    ButtonText(it, stringResource(id = R.string.button_rotate))

                     }*/
                    GameButton(
                        Modifier/*align(Alignment.CenterStart)*/,
                        onClick = { clickable.onMove(Direction.Left) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize,
                        painter = painterResource(R.drawable.leftpink_tetris)
                    )/*  {
      //                    ButtonText(it, stringResource(id = R.string.button_left))

                      }*/
                    GameButton(
                        Modifier/*align(Alignment.CenterEnd)*/,
                        onClick = { clickable.onMove(Direction.Right) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize,
                        painter = painterResource(R.drawable.rightpink_tetris)
                    )/* {
     //                    ButtonText(it, stringResource(id = R.string.button_right))

                     }*/
                    GameButton(
                        Modifier/*align(Alignment.BottomCenter)*/,
                        onClick = { clickable.onMove(Direction.Down) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize,
                        painter = painterResource(R.drawable.downpink_tetris)
                    )/*  {
      //                    ButtonText(it, stringResource(id = R.string.button_down))

                      }*/


                }


                //ROTATE BTN
                /* Box(
                     modifier = Modifier
                         .weight(1f)
                         .fillMaxHeight()
                 ) {
                     GameButton(
                         Modifier.align(Alignment.CenterEnd),
                         onClick = { clickable.onRotate() },
                         autoInvokeWhenPressed = false,
                         size = RotateButtonSize
                     ) {
                         ButtonText(it, stringResource(id = R.string.button_rotate))
                     }
                 }*/
            }

        }
}
}


fun DrawScope.drawScreenBorder(
    topLef: Offset,
    topRight: Offset,
    bottomLeft: Offset,
    bottomRight: Offset
) {
    var path = Path().apply {
        moveTo(topLef.x, topLef.y)
        lineTo(topRight.x, topRight.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(bottomLeft.x, bottomLeft.y)
        close()
    }
    drawPath(path, Color.Black.copy(0.5f))

    path = Path().apply {
        moveTo(bottomRight.x, bottomRight.y)
        lineTo(bottomLeft.x, bottomLeft.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(topRight.x, topRight.y)
        close()
    }

    drawPath(path, Color.White.copy(0.5f))

}


data class Clickable constructor(
    val onMove: (Direction) -> Unit,
    val onRotate: () -> Unit,
    val onRestart: () -> Unit,
    val onPause: () -> Unit,
    val onMute: () -> Unit
)

fun combinedClickable(
    onMove: (Direction) -> Unit = {},
    onRotate: () -> Unit = {},
    onRestart: () -> Unit = {},
    onPause: () -> Unit = {},
    onMute: () -> Unit = {}
) = Clickable(onMove, onRotate, onRestart, onPause, onMute)


@Preview(/*widthDp = 400, heightDp = 700*/)
@Composable
fun PreviewGameBody() {
    GameBody {}
}


val DirectionButtonSize = 60.dp
val RotateButtonSize = 90.dp
val SettingButtonSize = 15.dp