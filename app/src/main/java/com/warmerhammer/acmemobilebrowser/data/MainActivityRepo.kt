package com.warmerhammer.acmemobilebrowser.data

import android.util.Log

class MainActivityRepo {

    val tabCache = mutableListOf<AcmeUrl>(
        AcmeUrl(
            "Home",
        )
    )
    val historyCache = mutableListOf<AcmeUrl>(
        AcmeUrl("https://neeva.com/"),
        AcmeUrl("https://www.google.com/"),
        AcmeUrl("https://www.ask.com/")
    )

    val bookmarkCache = mutableListOf<AcmeUrl>(
        AcmeUrl("https://neeva.com/"),
        AcmeUrl("https://www.google.com/"),
        AcmeUrl("https://www.ask.com/"),
        AcmeUrl("https://www.yahoo.com/"),
        AcmeUrl("https://kotlinlang.org/")
    )


    fun storeTab(url: String) {
        val newAcmeUrl = AcmeUrl(url)
        tabCache.add(newAcmeUrl)
    }

    fun updateCache(acmeUrl: AcmeUrl, newUrl: String) {
        val indexToUpdate = tabCache.indexOf(acmeUrl)
        val newAcmeUrl = AcmeUrl(
            url = newUrl,
            timestamp = System.currentTimeMillis()
        )

        if (indexToUpdate != -1) {
            // only update history if new acmeUrl
            if (tabCache[indexToUpdate].url != newUrl) {
                historyCache.add(newAcmeUrl)
            }

            tabCache[indexToUpdate] = newAcmeUrl
        }
    }

    fun toggleBookmark(acmeUrl: AcmeUrl): List<AcmeUrl> {
        val bookmark = bookmarkCache.find{ it.url == acmeUrl.url }

        if (
            bookmark == null
        ) {
            bookmarkCache.add(acmeUrl)
        } else {
            bookmarkCache.remove(bookmark)
        }

        return bookmarkCache
    }
}