package com.teamx.equiz.games.games.learningy

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun OurView() {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            && configuration.screenWidthDp > 550
    val isSmallScreen = configuration.screenHeightDp < 550 && configuration.screenWidthDp <= 550

    Box(
        Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(16.dp), color = Color.White)
    ) {
        if (isLandscape) {
            OurViewContentLandscape(isCompact = isSmallScreen)
        } else {
            OurViewContentPortrait(isCompact = isSmallScreen)
        }
    }
}

@Composable
private fun OurViewContentPortrait(isCompact: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(color = Color.Red), contentAlignment = Alignment.Center
        ) { Text(text = "1") }
    }
}

@Composable
private fun OurViewContentLandscape(isCompact: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(color = Color.Red), contentAlignment = Alignment.Center
        ) { Text(text = "1") }
    }

}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}