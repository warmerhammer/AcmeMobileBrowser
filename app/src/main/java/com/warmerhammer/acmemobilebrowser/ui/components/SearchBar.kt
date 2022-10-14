package com.warmerhammer.acmemobilebrowser.ui.components

import android.util.Log
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.warmerhammer.acmemobilebrowser.ui.theme.SearchBarShape

@Composable
fun SearchBar(
    url: String,
    modifier: Modifier = Modifier,
    onSearch: (searchUrl: String) -> Unit,
    searchString: (search: String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf("") }
    value = url

    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colors.secondary,
        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = .4f)
    )

    Surface(
        modifier = Modifier
            .padding(PaddingValues(bottom = 10.dp, top = 10.dp, start = 16.dp, end = 16.dp))
            .fillMaxWidth()
            .testTag("Search Bar"),
        shape = SearchBarShape,
        elevation = 5.dp,
        color = Color.White
    ) {

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                value = value,
                onValueChange = {
                    value = it
                    searchString(value)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                placeholder = {
                    Text(
                        "Please enter a valid url...",
                        fontSize = 12.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = MaterialTheme.colors.secondary,
                    focusedIndicatorColor = MaterialTheme.colors.secondary,
                    placeholderColor = colors.secondary,
                    leadingIconColor = MaterialTheme.colors.primary,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Go,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        onSearch(value)
                    }
                ),
                maxLines = 1,
                modifier = modifier
                    .heightIn(min = 30.dp)
                    .padding(0.dp)
                    .testTag("Search Bar TextField")
            )
        }

    }
}

