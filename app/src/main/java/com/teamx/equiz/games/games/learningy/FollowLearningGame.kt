package com.teamx.equiz.games.games.learningy

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Preview
@Composable
fun DynamicVerticalGrid() {
    var gridData by remember { mutableStateOf(ArrayList<DataItem>()) }

    // Simulate fetching or updating data (replace with your actual data logic)

    LaunchedEffect(Unit) {

        repeat(31) {

            delay(200) // Delay to simulate data fetching
            val gridData2 = gridData

            gridData2.add(DataItem("Item 1", "Description z$it"))
            gridData = arrayListOf(gridData2.get(0))

        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(gridData.size) { item ->
           /* val itemVal = gridData.get(item)
            Row {
                // Display item data in grid cells
                Text(itemVal.title)
                Text(itemVal.description)
            }*/
        }
    }
}

data class DataItem(val title: String, val description: String)