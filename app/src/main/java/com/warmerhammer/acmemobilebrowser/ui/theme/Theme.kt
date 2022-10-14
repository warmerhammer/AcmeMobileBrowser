package com.warmerhammer.acmemobilebrowser.ui.theme

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val acme_divider_color = primary

val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

val TabShape = RoundedCornerShape(
    topStart = 5.dp,
    topEnd = 5.dp,
    bottomStart = 5.dp,
    bottomEnd = 5.dp
)

val TabBarShape = RoundedCornerShape(
    topStart = 15.dp,
    topEnd = 15.dp,
    bottomStart = 15.dp,
    bottomEnd = 15.dp
)

val SearchBarShape = RoundedCornerShape(
    topStart = 5.dp,
    topEnd = 5.dp,
    bottomStart = 5.dp,
    bottomEnd = 5.dp
)

@Composable
fun AcmeMobileBrowserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = primary,
    secondary = secondary,
    surface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)