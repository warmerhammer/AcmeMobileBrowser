package com.warmerhammer.acmemobilebrowser.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import java.sql.Timestamp

data class AcmeUrl(
    val url : String,
    val timestamp: Long = System.currentTimeMillis(),
)



