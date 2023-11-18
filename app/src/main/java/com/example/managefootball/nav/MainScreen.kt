package com.example.managefootball.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen: MainScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object MatchScreen: MainScreen(
        route = "match",
        title = "Match",
        icon = Icons.Default.DateRange
    )

    object PlayerScreen: MainScreen(
        route = "player",
        title = "Players",
        icon = Icons.Default.Person
    )

    object SettingScreen: MainScreen(
        route = "setting",
        title = "Setting",
        icon = Icons.Default.Settings
    )
}