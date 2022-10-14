package com.warmerhammer.acmemobilebrowser.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(

    h1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        color = TextColor,
        fontSize = 28.sp,
        letterSpacing = (1.15).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        color = TextColor,
        fontFamily = FontFamily.Default,
        fontSize = 15.sp,
        letterSpacing = (1.15).sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        color = TextColor,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        color = TextColor,
        fontSize = 13.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    caption = TextStyle(
        color = Color.White,
        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        letterSpacing = (1.15).sp
    ),


/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)