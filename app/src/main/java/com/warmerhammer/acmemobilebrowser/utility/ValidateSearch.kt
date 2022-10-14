package com.warmerhammer.acmemobilebrowser.utility

fun validateSearch(search: String): Boolean {
    if (
        search.isEmpty()
    ) {
        return false
    }

    if (
        (search.length > 7 && search.substring(0, 7) == "http://") ||
        (search.length > 8 && search.substring(0, 8) == "https://")
    ) {
        return true
    }

    return false
}