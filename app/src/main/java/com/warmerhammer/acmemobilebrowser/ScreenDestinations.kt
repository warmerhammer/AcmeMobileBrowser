package com.warmerhammer.acmemobilebrowser

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface ScreenDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object HomeScreen : ScreenDestination {
    override val icon = Icons.Filled.Home
    override val route = "homescreen"
}

object BookmarkScreen : ScreenDestination {
    override val icon = Icons.Outlined.Bookmarks
    override val route = "bookmarks"
}

object WebViewScreen: ScreenDestination {
    override val icon = Icons.Filled.WebAsset
    override val route = "webview"
    const val urlArg = "urlArg"
    val routeWithArgs = "$route/{$urlArg}"
    val arguments = listOf(
        navArgument(urlArg) { type = NavType.StringType }
    )
}


// Screens to be displayed in the top RallyTabRow
val screens = listOf(HomeScreen, WebViewScreen, BookmarkScreen)