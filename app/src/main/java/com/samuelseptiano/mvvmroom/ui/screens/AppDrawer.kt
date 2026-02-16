package com.samuelseptiano.mvvmroom.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Bookmark

/**
 * Created by samuel.septiano on 14/02/2026.
 */

@Composable
fun AppDrawer(
    selectedRoute: String?,
    onDestinationClicked: (String) -> Unit
) {

    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "News App",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        NavigationDrawerItem(
            label = { Text("Headlines") },
            selected = selectedRoute == "headlines",
            onClick = { onDestinationClicked("headlines") },
            icon = { Icon(Icons.Default.Home, null) }
        )

        NavigationDrawerItem(
            label = { Text("Search") },
            selected = selectedRoute == "search",
            onClick = { onDestinationClicked("search") },
            icon = { Icon(Icons.Default.Search, null) }
        )

        NavigationDrawerItem(
            label = { Text("Bookmarks") },
            selected = selectedRoute == "bookmarks",
            onClick = { onDestinationClicked("bookmarks") },
            icon = { Icon(Icons.Default.Bookmark, null) }
        )

    }
}
