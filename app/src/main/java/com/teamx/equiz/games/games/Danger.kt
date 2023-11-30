package com.teamx.equiz.games.games

import android.graphics.Color.argb
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun PoliceSirenIndicator(

) {
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(true) {
        animationProgress.animateTo(
            targetValue = 1f,

            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
            )
        )
    }

    val alpha = animationProgress.value * 255
    val color = argb(
        alpha.toInt(), (Color.White.red.toInt() + alpha).toInt(), 0, 0
    )
    val transparency = 0.5f
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
//
            .border(
                shape = RoundedCornerShape(15.dp), border = BorderStroke(
                    21.dp, color = Color(color)/*brush = Brush.radialGradient(
                        colors = listOf(
                           Red.copy(alpha = transparency),
                            Red.copy(alpha = 0f)
                        ),

                        radius = 1f
                    )*/

                )
            )

//            .alpha(alpha)

    ) {


    }
}


@Preview
@Composable
fun PreviewPoliceSiren() {
    MaterialTheme {
        PoliceSirenIndicator()
    }
}


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavDemoWithDropEffect() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = 4.dp,
                contentColor = Color.Black
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                            .dropShadow(
                                color = if (it.selected) Color.Black.copy(alpha = 0.5f) else Color.Transparent,
                                alpha = 0.7f,
                                radius = 10.dp,
                                offset = Offset(0.dp, 4.dp)
                            )
                    },
                    label = { Text("Home") },
                    onClick = { navController.navigate(NavDirections.homeScreen) },
                    selected = navController.currentDestination?.route == NavDirections.homeScreen
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                            .dropShadow(
                                color = if (it.selected) Color.Black.copy(alpha = 0.5f) else Color.Transparent,
                                alpha = 0.7f,
                                radius = 10.dp,
                                offset = Offset(0.dp, 4.dp)
                            )
                    },
                    label = { Text("Favorites") },
                    onClick = { navController.navigate(NavDirections.favoritesScreen) },
                    selected = navController.currentDestination?.route == NavDirections.favoritesScreen
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                            .dropShadow(
                                color = if (it.selected) Color.Black.copy(alpha = 0.5f) else Color.Transparent,
                                alpha = 0.7f,
                                radius = 10.dp,
                                offset = Offset(0.dp, 4.dp)
                            )
                    },
                    label = { Text("Settings") },
                    onClick = { navController.navigate(NavDirections.settingsScreen) },
                    selected = navController.currentDestination?.route == NavDirections.settingsScreen
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = NavDirections.homeScreen) {
            composable(NavDirections.homeScreen) {
                HomeScreen()
            }

            composable(NavDirections.favoritesScreen) {
                FavoritesScreen()
            }

            composable(NavDirections.settingsScreen) {
                SettingsScreen()
            }
        }
    }

    private sealed class NavDirections {
        companion object {
            val homeScreen = "home_screen"
            val favoritesScreen = "favorites_screen"
            val settingsScreen = "settings_screen"
        }
    }

    @Composable
    fun HomeScreen() {
        Text("Home Screen")
    }

    @Composable
    fun FavoritesScreen() {
        Text("Favorites Screen")
    }

    @Composable
    fun SettingsScreen() {
        Text("Settings Screen")
    }
}*//*
@Composable
fun CustomBottomNav() {
    val navController = rememberNavController()
    val selectedItemColor = Color.Red
    val unselectedItemColor = Color.Gray

    val isHomeSelected = navController.currentDestination?.route == NavDirections.homeScreen
    val isFavoritesSelected = navController.currentDestination?.route == NavDirections.favoritesScreen
    val isSettingsSelected = navController.currentDestination?.route == NavDirections.settingsScreen

    val selectedColor = if (isHomeSelected) selectedItemColor else if (isFavoritesSelected) selectedItemColor else if (isSettingsSelected) selectedItemColor else unselectedItemColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .background(shape = RoundedCornerShape(15.dp), color = selectedColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(Icons.Default.Home, contentDescription = "Home").background(shape = CircleShape(40.dp), color = if (isHomeSelected) selectedColor else unselectedItemColor)
                },
                label = { Text("Home") },
                onClick = { navController.navigate(NavDirections.homeScreen) },
                selected = isHomeSelected
            )

            BottomNavigationItem(
                icon = {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorites").background(shape = CircleShape(40.dp), color = if (isFavoritesSelected) selectedColor else unselectedItemColor)
                },
                label = { Text("Favorites") },
                onClick = { navController.navigate(NavDirections.favoritesScreen) },
                selected = isFavoritesSelected
            )

            BottomNavigationItem(
                icon = {
                    Icon(Icons.Default.Settings, contentDescription = "Settings").background(shape = CircleShape(40.dp), color = if (isSettingsSelected) selectedColor else unselectedItemColor)
                },
                label = { Text("Settings") },
                onClick = { navController.navigate(NavDirections.settingsScreen) },
                selected = isSettingsSelected
            )
        }
    }
}*/



@Composable
fun CustomBottomNavigation3() {
    val navController = rememberNavController()
    val selectedItemColor = MaterialTheme.colorScheme.primary
    val unselectedItemColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    val isHomeSelected = true/*navController.currentDestination?.route == NavDirections.homeScreen*/
    val isFavoritesSelected = false
//        navController.currentDestination?.route == NavDirections.favoritesScreen
    val isSettingsSelected =
        false/*navController.currentDestination?.route == NavDirections.settingsScreen*/

    val selectedColor =
        if (isHomeSelected) selectedItemColor else if (isFavoritesSelected) selectedItemColor else if (isSettingsSelected) selectedItemColor else unselectedItemColor

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background), Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            Arrangement.SpaceEvenly
        ) {
            CustomBottomNavigationItem(icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (isHomeSelected) selectedColor else unselectedItemColor
                )
            },
                label = { Text("Home", style = MaterialTheme.typography.bodySmall) },
                selected = isHomeSelected,
                onClick = {

                })

            CustomBottomNavigationItem(icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites",
                    tint = if (isFavoritesSelected) selectedColor else unselectedItemColor
                )
            },
                label = { Text("Favorites", style = MaterialTheme.typography.bodySmall) },
                selected = isFavoritesSelected,
                onClick = {

                })

            CustomBottomNavigationItem(icon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = if (isSettingsSelected) selectedColor else unselectedItemColor
                )
            },
                label = { Text("Settings", style = MaterialTheme.typography.bodySmall) },
                selected = isSettingsSelected,
                onClick = {

                })
        }
    }
}

private sealed class NavDirections {
    companion object {
        val homeScreen = "home_screen"
        val favoritesScreen = "favorites_screen"
        val settingsScreen = "settings_screen"
    }
}

@Composable
fun CustomBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (modifier: Modifier) -> Unit,
    label: @Composable (modifier: Modifier) -> Unit,
//    label: String="",
    dropEffectOffset: Dp = 4.dp
) {
    val density = LocalDensity.current.density

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentWidth()
            .clickable { onClick() }
            .fillMaxHeight()
            .background(Color.Yellow)


    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Red, shape = if (selected) CircleShape else RectangleShape)
                .graphicsLayer(
                    translationY = if (selected) dropEffectOffset.value * density else 0f
                )
        ) {
            icon(Modifier.size(24.dp))
        }

        if (selected) {
//            label
            Text(
                text = "label",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreVBar() {
    MaterialTheme {
        CustomBottomNavigation3()
    }
}


@Composable
fun ShapeChangingComponent2() {
    var isCircle by remember { mutableStateOf(true) }
    val transitionProgress by animateFloatAsState(
        targetValue = if (isCircle) 1f else 0f, animationSpec = tween(durationMillis = 10000)
    )
    Box(modifier = Modifier
        .size(100.dp)
        .background(color = Color.Red, shape = if (isCircle) CircleShape else RectangleShape)
        .clickable {
            isCircle = !isCircle
            Log.d("123123", "ShapeChangingComponent: 1")
        }
        .graphicsLayer(
            shape = if (isCircle) CircleShape else RectangleShape,
            translationY = 100f * transitionProgress
        ))
}

@Composable
fun ShapeChangingComponent() {
    var isCircle by remember { mutableStateOf(true) }
    val transitionProgress by animateFloatAsState(
        targetValue = if (isCircle) 1f else 0f,
        animationSpec = tween(durationMillis = 100000)
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.Red, shape = if (isCircle) CircleShape else RectangleShape)
            .clickable {
                isCircle = !isCircle
                Log.d("123123", "ShapeChangingComponent: 1")
            }
            .graphicsLayer(
                shape = if (isCircle) CircleShape else RectangleShape,
                translationY = 100f * transitionProgress
            )
    )
}

@Preview
@Composable
fun ShapeChangingComponentPreview() {
    ShapeChangingComponent()
}

@Preview
@Composable
fun ShapeChangingApp() {
    MaterialTheme {
        Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {

            ShapeChangingComponent()
        }
    }
}
