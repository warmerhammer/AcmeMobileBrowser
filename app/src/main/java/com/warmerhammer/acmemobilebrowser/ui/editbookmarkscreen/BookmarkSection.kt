package com.warmerhammer.acmemobilebrowser.ui.editbookmarkscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.ui.theme.BottomSheetShape
import com.warmerhammer.acmemobilebrowser.ui.theme.acme_divider_color

@Composable
fun BookmarkSection(
    title: String,
    bookmarkList: List<AcmeUrl>,
    modifier: Modifier = Modifier,
    onItemClicked: (AcmeUrl) -> Unit,
    onDeleteClick: (AcmeUrl) -> Unit,
    onEditClick: () -> Unit,
    onExpand: () -> Unit,
    backgroundColor : Color = Color.White
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .testTag("bookmarkList")
        ,
        color = backgroundColor,
        shape = BottomSheetShape,
    ) {

        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {

            if (title == "Explore Bookmarks") {
                IconButton(
                    onClick = {
                        onExpand()
                    },
                    content = {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .weight(.40f)
                        .padding(PaddingValues(start = 8.dp)),
                )

                if (title == "Explore Bookmarks") {
                    Spacer(Modifier.weight(.50f))

                    IconButton(
                        content = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        onClick = onEditClick,
                        modifier = Modifier.weight(.10f),
                    )
                }

            }

            val listState = rememberLazyListState()
            BookmarkList(
                bookmarkList,
                title,
                onItemClicked = onItemClicked,
                onDeleteClick = onDeleteClick,
                listState = listState
            )
        }
    }
}

@Composable
private fun BookmarkList(
    bookmarkList: List<AcmeUrl>,
    title: String,
    onItemClicked: (AcmeUrl) -> Unit,
    onDeleteClick: (AcmeUrl) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(modifier = modifier, state = listState) {
        val isEditScreen = title == "Edit Bookmarks"

        items(bookmarkList) { bookmarkItem ->
            Column(Modifier.fillParentMaxWidth()) {
                BookmarkItem(
                    item = bookmarkItem,
                    isEditScreen = isEditScreen,
                    modifier = Modifier.fillParentMaxWidth(),
                    onItemClicked = onItemClicked,
                    onDeleteClick = onDeleteClick
                )
                Divider(color = acme_divider_color)
            }
        }
        item {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}

@Composable
private fun BookmarkItem(
    modifier: Modifier = Modifier,
    isEditScreen: Boolean,
    item: AcmeUrl,
    onItemClicked: (AcmeUrl) -> Unit,
    onDeleteClick: (AcmeUrl) -> Unit,
) {
    val checkedState = rememberSaveable { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.testTag("Bookmark Item")
            .clickable {
                onItemClicked(item)
                checkedState.value = !checkedState.value
            }
            .padding(top = 12.dp, bottom = 12.dp)
    ) {

        if (isEditScreen) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    onItemClicked(item)
                    checkedState.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.secondary,
                    uncheckedColor = MaterialTheme.colors.primary,
                )
            )
        } else {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Bookmark Trashcan",
                        tint = Color.White
                    )
                },
                onClick = { onDeleteClick(item) },
                modifier = Modifier
                    .width(17.dp)
                    .height(17.dp)
            )
        }


        Spacer(Modifier.width(24.dp))
        Text(
            text = item.url,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isEditScreen) Color.Black else Color.White,
            modifier = Modifier.testTag("Bookmark Item Text"),
        )
    }
}