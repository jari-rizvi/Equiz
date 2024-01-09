package com.teamx.equiz.games.games.learningy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EquationText() {
    val equation = "2x + 3 - 4 / 2"

    val annotatedString = buildAnnotatedString {
        equation.forEachIndexed { index, char ->
            val drawableId = when (char) {
                'x' -> Icons.Default.Star
                '+' -> Icons.Default.Add
                '-' -> Icons.Default.Remove
                '/' -> Icons.Default.Remove // replace with your custom drawable resource
                else -> null
            }

            if (drawableId != null) {
                withStyle(style = MaterialTheme.typography.headlineLarge.toSpanStyle()) {
//                    appendInlineContent(drawableId, index)
                }
            } else {
                append(char)
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .padding(bottom = 26.dp)
            .clickable {

            },
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black,
        fontSize = 36.sp,
    )
}

@Composable
fun EquationText3() {
    val equation = "2x + 3 - 4 / 2"

    val annotatedString = buildAnnotatedString {
        equation.forEachIndexed { index, char ->
            val inlineContent = when (char) {
                'x' -> Icons.Default.Star
                '+' -> Icons.Default.Add
                '-' -> Icons.Default.Remove
                '/' -> "divide" // Custom identifier for division operator
                else -> null
            }

            if (inlineContent != null) {
                withStyle(style = MaterialTheme.typography.headlineLarge.toSpanStyle()) {
                    appendInlineContent(inlineContent.toString(), inlineContent.toString())
                }
            } else {
                append(char)
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = Modifier.padding(bottom = 26.dp),

        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Black,
        fontSize = 36.sp,
    )
}

@Preview(showBackground = true)
@Composable
fun EquationTextPreview() {
    EquationText3()
}