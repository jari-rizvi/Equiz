package com.teamx.equiz.games.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.teamx.equiz.R

@Composable
fun SpinningBlockGame(content: @Composable () -> Unit) {
    Column {
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
    content()
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
    }
    }
}