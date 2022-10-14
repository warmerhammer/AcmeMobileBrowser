package com.warmerhammer.acmemobilebrowser.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.ui.theme.TabBarShape
import com.warmerhammer.acmemobilebrowser.ui.theme.TabShape
import java.util.*

@Composable
fun TabBarRow(
    tabs: List<AcmeUrl>,
    onTabSelected: (tab: AcmeUrl) -> Unit,
    addTab: () -> Unit,
) {

    Surface(
        elevation = 5.dp,
        shape = TabBarShape,
        modifier = Modifier
            .padding(PaddingValues(bottom = 7.dp, end = 2.dp, start = 2.dp))
    ) {

        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(vertical = 0.dp)
                .background(color = MaterialTheme.colors.primary)
        ) {


            LazyRow(
                contentPadding = PaddingValues(horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .selectableGroup()
                    .weight(.85f)
            ) {


                items(tabs) { tab ->
                    var text: String = tab.url
                    if (text.length > 8) {
                        text = if (text[7] == '/') {
                            text.substring(8, text.length)
                        } else {
                            text.substring(7, text.length)
                        }
                    }

                    TabBarItem(
                        text = text,
                        onSelected = { onTabSelected(tab) },
                        selected = tabs.first().timestamp == tab.timestamp,
                    )
                }
            }

            IconButton(
                content = {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.surface
                    )
                },
                onClick = { addTab() },
                modifier = Modifier.weight(.15f),
            )
        }

    }

}

@Composable
private fun TabBarItem(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    val color = MaterialTheme.colors.secondary
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .animateContentSize()
            .fillMaxHeight()
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {

        if (selected) {
            Surface(
                elevation = 5.dp,
                modifier = Modifier
                    .padding(horizontal = 7.dp)
                    .height(40.dp),
                shape = TabShape,
                color = tabTintColor
            ) {
                Text(
                    text.uppercase(Locale.getDefault()),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 7.dp, vertical = 5.dp)
                        .widthIn(max = 200.dp),
                )
            }

        } else {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .height(35.dp),
                shape = TabShape,
                color = tabTintColor
            ) {
                Text(
                    text.lowercase(Locale.getDefault()),
                    color = Color.White,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 6.dp)
                        .widthIn(max = 200.dp)
                )
            }

        }
    }
}

private val TabHeight = 35.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100