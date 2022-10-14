package com.warmerhammer.acmemobilebrowser

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector


interface NavBarItem {
    val icon: ImageVector
    val name: String
}

object Back: NavBarItem {
    override val icon = Icons.Default.ArrowBack
    override val name = "back"
}

object Forward: NavBarItem {
    override val icon = Icons.Default.ArrowForward
    override val name = "forward"
}

object Reload: NavBarItem {
    override val icon = Icons.Outlined.Refresh
    override val name = "reload"
}

object Home: NavBarItem {
    override val icon = Icons.Outlined.Home
    override val name = "home"
}

object Bookmark : NavBarItem {
    override val icon = Icons.Outlined.Grade
    override val name = "bookmark"
}

val navBarItems = listOf(
    Back,
    Home,
    Bookmark,
    Reload,
    Forward
)