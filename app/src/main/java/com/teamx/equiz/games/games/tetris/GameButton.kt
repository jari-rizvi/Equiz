package com.teamx.equiz.games.games.tetris

import android.view.MotionEvent.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamx.equiz.R
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    size: Dp,
    onClick: () -> Unit = {},
    autoInvokeWhenPressed: Boolean = false,
    painter: Painter= painterResource(id = R.drawable.ic_launcher_background)
//    content: @Composable (Modifier) -> Unit = {}
) {
    val backgroundShape = RoundedCornerShape(size / 2)
    var ticker: ReceiveChannel<Unit>? = null

    val coroutineScope = rememberCoroutineScope()
    val pressedInteraction = remember { mutableStateOf<PressInteraction.Press?>(null) }
    val interactionSource = MutableInteractionSource()

    Box(
        modifier = modifier
            .shadow(5.dp, shape = backgroundShape)
            .size(size = size)
            .clip(backgroundShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White, Color.White
                    ),
                    startY = 0f,
                    endY = 80f
                )
            ).indication(interactionSource = interactionSource, indication = rememberRipple())
            .run {
                if (autoInvokeWhenPressed) {
                    pointerInteropFilter {
                        when (it.action) {
                            ACTION_DOWN -> {
                                coroutineScope.launch {
                                    // Remove any old interactions if we didn't fire stop / cancel properly
                                    pressedInteraction.value?.let { oldValue ->
                                        val interaction = PressInteraction.Cancel(oldValue)
                                        interactionSource.emit(interaction)
                                        pressedInteraction.value = null
                                    }
                                    val interaction = PressInteraction.Press(Offset(50f, 50f))
                                    interactionSource.emit(interaction)
                                    pressedInteraction.value = interaction
                                }


                                ticker = ticker(initialDelayMillis = 300, delayMillis = 60)
                                coroutineScope.launch {
                                    ticker!!
                                        .receiveAsFlow()
                                        .collect { onClick() }
                                }
                            }
                            ACTION_CANCEL, ACTION_UP -> {
                                coroutineScope.launch {
                                    pressedInteraction.value?.let {
                                        val interaction = PressInteraction.Cancel(it)
                                        interactionSource.emit(interaction)
                                        pressedInteraction.value = null
                                    }
                                }
                                ticker?.cancel()
                                if (it.action == ACTION_UP) {
                                    onClick()
                                }
                            }
                            else -> {
                                if (it.action != ACTION_MOVE) {
                                    ticker?.cancel()
                                }
                                return@pointerInteropFilter false
                            }
                        }
                        true
                    }
                } else {
                    clickable { onClick() }
                }
            }

    ) {
        IconButton( onClick={ onClick()},modifier = Modifier.align(Alignment.Center)){
            Icon(painter = painter , contentDescription ="" )
        }
    }
}
