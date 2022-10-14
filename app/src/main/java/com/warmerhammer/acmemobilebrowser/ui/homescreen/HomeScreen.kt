package com.warmerhammer.acmemobilebrowser.ui.homescreen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.warmerhammer.acmemobilebrowser.MainActivityViewModel
import com.warmerhammer.acmemobilebrowser.R
import com.warmerhammer.acmemobilebrowser.ui.components.SearchBar
import com.warmerhammer.acmemobilebrowser.ui.theme.AcmeMobileBrowserTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.warmerhammer.acmemobilebrowser.ui.editbookmarkscreen.BookmarkSection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel = viewModel(),
    onSearch: (
        url: String
    ) -> Unit,
    onEdit: () -> Unit,
) {
    val history by viewModel.history.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val editBookmarks by viewModel.editBookmarks.collectAsState()

    AcmeMobileBrowserTheme {
        val density = LocalDensity.current

        BackdropScaffold(
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            frontLayerScrimColor = Color.Unspecified,
            frontLayerBackgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(PaddingValues(top = 20.dp, start = 3.dp, end = 3.dp)
            ),
            appBar = {
                AnimatedVisibility(
                    visible = !editBookmarks,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = .3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {

                    SearchBar(
                        "https://",
                        Modifier.padding(horizontal = 16.dp),
                        onSearch = { searchUrl -> onSearch(searchUrl) },
                        searchString = {}
                    )
                }
            },

            backLayerContent = {
                AnimatedVisibility(
                    visible = !editBookmarks,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = .3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    Column {
                        HistorySection(
                            title = R.string.History,
                            icon = Icons.Default.ArrowRightAlt,
                        ) {
                            HomeScreenHistory(
                                history
                            ) { idx ->
                                onSearch(history[idx].url)
                            }
                        }

                        Spacer(Modifier.height(50.dp))
                    }
                }
            },

            frontLayerContent = {

                BookmarkSection(
                    title = "Explore Bookmarks",
                    bookmarkList = bookmarks,
                    onItemClicked = { onSearch(it.url) },
                    onDeleteClick = { viewModel.toggleBookmark(it) },
                    onEditClick = onEdit,
                    onExpand = { viewModel.editBookmarks(!editBookmarks) },
                    backgroundColor = MaterialTheme.colors.secondary
                )


            }

        )
    }
}