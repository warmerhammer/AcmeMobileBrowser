package com.warmerhammer.acmemobilebrowser.ui.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.warmerhammer.acmemobilebrowser.MainActivityViewModel
import com.warmerhammer.acmemobilebrowser.NavBarItem
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NavBarRow(
    bookmarked: Boolean,
    navItems: List<NavBarItem>,
    onClick: (String) -> Unit
) {

    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.selectableGroup()
        ) {
            Row (
                Modifier
                    .weight(.9f)
                    .padding(PaddingValues(start = 20.dp))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                navItems.forEach { item ->
                    if (item.name == "bookmark" && bookmarked) {

                        NavTab(
                            text = item.name,
                            icon = Icons.Filled.Star,
                            onSelected = { onClick(item.name) },
                        )
                    } else {
                        NavTab(
                            text = item.name,
                            icon = item.icon,
                            onSelected = { onClick(item.name) }
                        )
                    }
                }
            }
            
            Row (Modifier.weight(.2f).testTag("Go Button")) {
                Button(
                    enabled = true,
                    onClick = { onClick("go") }
                ) {
                    Text("GO")
                }
            }

            
        }
    }
}

@Composable
private fun NavTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit
) {

    IconButton(
        content = {
            Icon(icon, contentDescription = text)
        },
        onClick = onSelected,
        enabled = true,
    )
}

private val TabHeight = 56.dp