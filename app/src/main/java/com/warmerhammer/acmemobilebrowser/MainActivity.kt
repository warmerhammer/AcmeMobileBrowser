package com.warmerhammer.acmemobilebrowser

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.ui.editbookmarkscreen.EditBookmarkScreen
import com.warmerhammer.acmemobilebrowser.ui.components.TabBarRow
import com.warmerhammer.acmemobilebrowser.ui.components.WebViewScreen
import com.warmerhammer.acmemobilebrowser.ui.homescreen.HomeScreen
import com.warmerhammer.acmemobilebrowser.ui.theme.AcmeMobileBrowserTheme
import com.warmerhammer.acmemobilebrowser.ui.theme.acme_divider_color
import com.warmerhammer.acmemobilebrowser.utility.validateSearch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainActivityViewModel()
        setContent {
            App(
                context = this,
                viewModel
            )
        }
    }
}

@Composable
fun App(
    context: Context,
    viewModel: MainActivityViewModel = viewModel()
) {
    AcmeMobileBrowserTheme {
        val navController = rememberNavController()
        val tabs by viewModel.tabs.collectAsState()
        val bookmarks by viewModel.bookmarks.collectAsState()
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colors.primary,
            topBar = {
                TabBarRow(
                    tabs = tabs,
                    onTabSelected = { tab ->
                        viewModel.updateCache(tab, tab.url)
                        when (tab.url) {
                            "Home" -> navController.navigate(HomeScreen.route)
                            else -> navController.navigateToWebView(tab.url)
                        }
                    },
                    addTab = {
                        viewModel.storeTab("Home")
                        viewModel.updateCache(AcmeUrl("Home"), "Home")
                        navController.navigate(HomeScreen.route)
                        Toast.makeText(context, "New tab", Toast.LENGTH_SHORT).show()
                    }
                )
            },
        ) { padding ->


            NavHost(
                navController = navController,
                startDestination = HomeScreen.route,
                modifier = Modifier
                    .padding(PaddingValues(top = 0.dp, bottom = 0.dp, start = .5.dp, end = .5.dp))
            ) {
                //Homescreen
                composable(route = HomeScreen.route) {
                    HomeScreen(
                        viewModel = viewModel,
                        onSearch = { searchUrl ->

                            if (validateSearch(searchUrl)) {
                                // pass in current tab and newUrl
                                when (searchUrl) {
                                    "Home" -> navController.navigate(HomeScreen.route)
                                    else -> navController.navigateToWebView(searchUrl)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter valid url.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        },
                        onEdit = {
                            navController.navigate(BookmarkScreen.route)
                        }
                    )
                }

                //WebviewScreen
                composable(
                    route = WebViewScreen.routeWithArgs,
                    arguments = WebViewScreen.arguments
                ) { backStackEntry ->
                    WebViewScreen(
                        acmeUrl = tabs.first(),
                        bookmarked = bookmarks.any { it.url == tabs.first().url },
                        backStackEntry.arguments?.getString("urlArg")!!,
                        onHomeClick = { navController.navigate(HomeScreen.route) },
                        onSearch = { searchUrl ->
                            val isValid = validateSearch(searchUrl)
                            // pass in current tab and newUrl
                            if (isValid) {
                                viewModel.updateCache(tabs.first(), searchUrl)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter valid url.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onBookmarkClick = {
                            viewModel.toggleBookmark(tabs.first())
                        }
                    )
                }

                //BookmarkScreen
                composable(route = BookmarkScreen.route) {
                    EditBookmarkScreen(
                        viewModel,
                        onDelete = { bookmarks ->
                            bookmarks.forEach { acmeUrl ->
                                viewModel.toggleBookmark(acmeUrl)
                                navController.navigate(BookmarkScreen.route)
                            }
                        },
                        onClose = {
                            navController.navigate(HomeScreen.route)
                        }
                    )
                }
            }
        }
    }
}

fun NavHostController.navigateToWebView(url: String) {
    val encodedUrl =
        URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    this.navigate(
        WebViewScreen.route + "/" + encodedUrl
    )
}



