package com.teamx.equiz.games.games.learningy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class LSSL {
}
val items = listOf( // Replace with your actual data
    "Item 1",
    "Item 2",
    "Item 3",
    "Item 4",
    "Item 5",
    "Item 6",
    "Item 7",
    "Item 8",
    "Item 9"
)
@Preview
@Composable
fun StaggeredGridItemGrid() {
    val animationState = remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { item2 ->
            val item = items.get(item2)
            val itemDelay = animationState.value * 1000L // Convert index to delay in ms
            if (animationState.value < items.count()) {
                LaunchedEffect(key1 = item, key2 = animationState.value) {
                    delay(itemDelay) // Stagger item rendering
                    animationState.value++
                }
            }

            if (animationState.value >= items.indexOf(item)) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.33f), // Occupy 1/3rd width for 3 columns
                ) {
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}