package com.teamx.equiz.games.games.learningy.musiclearning

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.teamx.equiz.R



fun correctSound(context: Context) {

    val mediaPlayer = MediaPlayer.create(context,R.raw.correct_move)
    if (mediaPlayer.isPlaying) {

        mediaPlayer.stop()
        mediaPlayer.prepare()
    }
    mediaPlayer.start()

}
fun incorrectSound(context: Context) {

    val mediaPlayer = MediaPlayer.create(context,R.raw.incorrect_move)
    if (mediaPlayer.isPlaying) {

        mediaPlayer.stop()
        mediaPlayer.prepare()
    }
    mediaPlayer.start()

}

@Composable
fun InCorrectSound() {
    var soundEffectResource by remember { mutableIntStateOf(R.raw.incorrect_move) }
    val mediaPlayer by rememberUpdatedState(mediaPlayerFor(soundEffectResource))
    if (mediaPlayer.isPlaying) {

        mediaPlayer.stop()
        mediaPlayer.prepare()
    }
    mediaPlayer.start()

}

@Preview
@Composable
fun ClickableSoundEffectExample() {
    var soundEffectResource by remember { mutableIntStateOf(R.raw.correct_move) }
    var soundEffectResource2 by remember { mutableIntStateOf(R.raw.incorrect_move) }
    var counter by remember { mutableIntStateOf(0) }
    val mediaPlayer by rememberUpdatedState(mediaPlayerFor(soundEffectResource))

    val mediaPlayer2 by rememberUpdatedState(mediaPlayerFor(soundEffectResource2))

    val context = LocalContext.current
    val add = SwipeRefreshLayout(context).also {
        it.addView(ComposeView(context).also {
            it.setContent {
                Box() {

                    Text("Hello$counter")

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                if (mediaPlayer.isPlaying) {
                                    mediaPlayer.stop()
                                    mediaPlayer.prepare()
//                    mediaPlayer.release()
                                }
                                mediaPlayer.start()


                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Click me to play sound effect")
                        }
                        Button(
                            onClick = {
                                if (mediaPlayer2.isPlaying) {
                                    mediaPlayer2.stop()
                                    mediaPlayer2.prepare()
//                    mediaPlayer2.release()
                                }
                                mediaPlayer2.start()
                                // Start playing the sound on button click


                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Click me to play sound effect")
                        }
                    }
                }
            }
        })
    }
    add.setOnRefreshListener {
        counter++
        add.isRefreshing = false
    }

    Box() {
        AndroidView(factory = { add })

    }


}

@Composable
fun mediaPlayerFor(soundResource: Int): MediaPlayer {
    val context = LocalContext.current
    return MediaPlayer.create(context, soundResource)
}

@Composable
fun DisposableMediaPlayer(mediaPlayer: MediaPlayer) {
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}