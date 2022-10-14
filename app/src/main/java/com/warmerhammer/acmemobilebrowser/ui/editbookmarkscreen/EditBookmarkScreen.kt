package com.warmerhammer.acmemobilebrowser.ui.editbookmarkscreen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.warmerhammer.acmemobilebrowser.MainActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.utility.validateSearch

@Composable
fun EditBookmarkScreen(
    viewModel: MainActivityViewModel = viewModel(),
    onDelete: (bookmarks: List<AcmeUrl>) -> Unit,
    onClose: () -> Unit,
) {

    val bookmarks by viewModel.bookmarks.collectAsState()
    var checked by rememberSaveable { mutableStateOf(listOf<AcmeUrl>()) }

    Scaffold(

        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    horizontal = 28.dp,
                    vertical = 10.dp
                )
            ) {
                var url by rememberSaveable { mutableStateOf("https://") }

                TextField(
                    value = url,
                    onValueChange = { url = it },
                    placeholder = {
                        Text(
                            "Add bookmark ..."
                        )
                    },
                    modifier = Modifier.weight(.80f),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.toggleBookmark(AcmeUrl(url))
                            url = "https://"
                        }
                    )
                )

                Button(
                    onClick = {
                        viewModel.toggleBookmark(AcmeUrl(url))
                        url = "https://"
                    },
                    modifier = Modifier.weight(.20f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    enabled = validateSearch(url)

                ) {
                    Text("Add", color = Color.White)
                }
            }
        },

        bottomBar = {
            Surface(
                elevation = 2.dp,
                color = MaterialTheme.colors.primary,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {

                    Spacer(modifier = Modifier.weight(.25f))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(.50f)
                            .padding(
                            )
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                            onClick = {
                                onDelete(checked)
                                checked = listOf<AcmeUrl>()
                            },
                            enabled = checked.isNotEmpty()
                        ) {
                            Text(
                                "Delete",
                                color = Color.White
                            )
                        }
                    }


                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.weight(.25f)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }


                }
            }
        }

    ) { padding ->
        val density = LocalDensity.current
        var visible by remember { mutableStateOf(true) }

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                with(density) { 20.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Bottom
            ) + fadeIn(
                initialAlpha = .1f
            )

        ) {
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(vertical = 15.dp)
            ) {

                BookmarkSection(
                    title = "Edit Bookmarks",
                    bookmarkList = bookmarks,
                    onItemClicked = { acmeUrl ->
                        // toggle items in and out of list
                        // dependent on checked state
                        checked = if (checked.contains(acmeUrl)) {
                            checked.toMutableList().also { it.remove(acmeUrl) }
                        } else {
                            checked + listOf(acmeUrl)
                        }
                    },
                    onDeleteClick = {},
                    onEditClick = {},
                    onExpand = {},
                    backgroundColor = Color.White
                )
            }
        }

    }
}