package com.warmerhammer.acmemobilebrowser.ui.components

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.navBarItems
import com.warmerhammer.acmemobilebrowser.utility.validateSearch

@Composable
fun WebViewScreen(
    acmeUrl: AcmeUrl,
    bookmarked: Boolean,
    url: String,
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onSearch: (url: String) -> Unit,
    onBookmarkClick: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    var webView: WebView? = null
    var search by rememberSaveable { mutableStateOf("") }
    var backQueue by rememberSaveable { mutableStateOf(listOf<String>()) }
    var forwardQueue by rememberSaveable { mutableStateOf(listOf<String>()) }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.testTag("Webview"),
        bottomBar = {
            NavBarRow(
                bookmarked = bookmarked,
                navItems = navBarItems
            ) { item ->

                when (item) {
                    "back" -> {
                        if (webView?.canGoBack() == true) {
                            forwardQueue = forwardQueue + listOf(acmeUrl.url)
                            backQueue = backQueue.toMutableList().also {
                                it.removeLast()
                            }
                            webView?.goBack()
                        }
                    }
                    "forward" -> {
                        if (webView?.canGoForward() == true) {
                            backQueue = backQueue + listOf(acmeUrl.url)
                            forwardQueue = forwardQueue.toMutableList().also {
                                it.removeLast()
                            }

                            webView?.goForward()
                        }
                    }
                    "reload" -> {
                        webView?.reload()
                    }
                    "home" -> {
                        onHomeClick()
                    }
                    "bookmark" -> {
                        onBookmarkClick()
                    }
                    "go" -> {

                        if (
                            search.isNotEmpty() &&
                            acmeUrl.url != search
                        ) {
                            if (validateSearch(search))
                                webView?.loadUrl(search)
                            else
                                onSearch(search)
                        }
                    }
                }
            }
        },
        topBar = {
            SearchBar(
                acmeUrl.url,
                onSearch = { searchUrl ->
                    // add to queue and search only when a new url is requested
                    if (
                        acmeUrl.url != searchUrl
                    ) {
                        if (validateSearch(searchUrl))
                            webView?.loadUrl(searchUrl)
                        else
                            onSearch(searchUrl)
                    }
                },
                searchString = { searchString ->
                    search = searchString
                }
            )
        }
    ) { padding ->
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            var loading by rememberSaveable { mutableStateOf(false) }
            loadingIndicator(loading)

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                return false
                            }

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                loading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                if (url != null) {
                                    backQueue = backQueue + listOf(acmeUrl.url)
                                    onSearch(url)
                                    loading = false
                                }
                            }
                        }

                        if (validateSearch(url)) {
                            loadUrl(url)
                            webView = this
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter valid url.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }, update = {
                    webView = it
                })
        }
    }
}

@Composable
private fun loadingIndicator(active: Boolean = false) {
    if (active) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.secondary)
            }
        }
    }
}