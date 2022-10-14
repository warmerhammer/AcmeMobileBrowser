package com.warmerhammer.acmemobilebrowser

import android.content.Context
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import com.warmerhammer.acmemobilebrowser.data.MainActivityRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepo = MainActivityRepo(),
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    private val _tabs = MutableStateFlow<List<AcmeUrl>>(emptyList())
    val tabs: StateFlow<List<AcmeUrl>>
        get() = _tabs

    private val _history = MutableStateFlow<List<AcmeUrl>>(emptyList())
    val history: StateFlow<List<AcmeUrl>>
        get() = _history

    private val _bookmarks = MutableStateFlow<List<AcmeUrl>>(emptyList())
    val bookmarks: StateFlow<List<AcmeUrl>>
        get() = _bookmarks

    private val _editBookmarks = MutableStateFlow(false)
    val editBookmarks : StateFlow<Boolean>
        get() = _editBookmarks

    init {
        update()
    }

    private fun getTabs() {
        viewModelScope.launch {
            val sortedTabs = withContext(defaultDispatcher) {
                repository.tabCache
                    .sortedByDescending { it.timestamp }
            }

            _tabs.value = sortedTabs
        }
    }

    private fun getHistory() {
        viewModelScope.launch {
            val history = withContext(defaultDispatcher) {
                repository.historyCache.sortedByDescending { it.timestamp }
            }
            _history.value = history
        }
    }

    private fun getBookmarks() {
        viewModelScope.launch {
            val bookmarks = withContext(defaultDispatcher) {
                repository.bookmarkCache.sortedByDescending { it.timestamp }
            }
            _bookmarks.value = bookmarks
        }
    }

    private fun update() {
        getTabs()
        getHistory()
        getBookmarks()
    }

    fun storeTab(url: String) {
        repository.storeTab(url)
        update()
    }

    fun updateCache(acmeUrl: AcmeUrl, newUrl: String) {
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.updateCache(acmeUrl, newUrl)
            }
            update()
        }
    }

    fun toggleBookmark(acmeUrl: AcmeUrl) {
        val newCache = repository.toggleBookmark(acmeUrl)
        _bookmarks.value = newCache.sortedByDescending { it.timestamp }
    }

    fun editBookmarks(value: Boolean) = run { _editBookmarks.value = value }
}

