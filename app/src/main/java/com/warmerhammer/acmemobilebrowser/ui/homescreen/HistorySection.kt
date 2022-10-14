package com.warmerhammer.acmemobilebrowser.ui.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.warmerhammer.acmemobilebrowser.data.AcmeUrl
import java.util.*

@Composable
fun HistorySection(
    title: Int,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(id = title).uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(start = 18.dp, end = 5.dp),
                color = MaterialTheme.colors.surface
            )

            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier
                    .height(15.dp),
                tint = MaterialTheme.colors.surface

            )
        }
        content()
    }
}

@Composable
fun HomeScreenHistory(
    history: List<AcmeUrl>,
    onClick: (idx: Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
    ) {
        items(history.size) { idx ->
            HomeScreenHistoryElement(pos = idx + 1, text = history[idx].url) {
                onClick(idx)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenHistoryElement(
    pos: Int,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.primary.copy(alpha = .3f),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(PaddingValues(bottom = 15.dp, start = 10.dp, end = 10.dp))
                .widthIn(max = 200.dp),
        ) {
            Text(
                text = pos.toString(),
                color = MaterialTheme.colors.surface
            )
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = 5.dp),
                color = MaterialTheme.colors.surface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }

}