package com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.utils.NetworkMonitor
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onNewsClick: (News) -> Unit

) {
    val pagingItems = viewModel.bookmarks.collectAsLazyPagingItems()
    val context = LocalContext.current

    val networkMonitor = remember { NetworkMonitor(context) }

    LaunchedEffect(Unit) {
        networkMonitor.isConnected.collect { isConnected ->
            if (isConnected) {
                pagingItems.refresh()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarks") }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    count = pagingItems.itemCount,
                    key = { index -> pagingItems[index]?.url ?: index }
                ) { index ->

                    pagingItems[index]?.let { news ->
                        NewsCard(
                            news = news,
                            viewModel = viewModel,
                            pagingItems = pagingItems,
                            onClick = {
                                onNewsClick(news)
                            })
                    }
                }

                // Append loading
                if (pagingItems.loadState.append is LoadState.Loading) {
                    item { AppendLoading() }
                }

                // Append error
                if (pagingItems.loadState.append is LoadState.Error) {
                    val error = pagingItems.loadState.append as LoadState.Error
                    item {
                        RetryAppendItem(
                            errorMessage = error.error.message ?: "Error",
                            onRetry = { pagingItems.retry() }
                        )
                    }
                }
            }

            // First load loading
            if (pagingItems.loadState.refresh is LoadState.Loading) {
                FullScreenLoading()
            }

            // First load error
            if (pagingItems.loadState.refresh is LoadState.Error) {
                val error = pagingItems.loadState.refresh as LoadState.Error
                ErrorContent(
                    errorMessage = error.error.message ?: "Unknown error",
                    onRetry = { pagingItems.retry() }
                )
            }

            // Empty state
            if (pagingItems.loadState.refresh is LoadState.NotLoading &&
                pagingItems.itemCount == 0
            ) {
                Text(
                    text = "No news found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ErrorContent(errorMessage: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Failed to load alerts: $errorMessage",
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun NewsCard(
    news: News,
    viewModel: BookmarkViewModel,
    pagingItems: LazyPagingItems<News>,
    onClick: () -> Unit = {}
) {
    var isBookmarked by remember { mutableStateOf(news.isBookmarked) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = onClick
    ) {
        Column {

            IconButton(onClick = {
                /* Toggle bookmark */
                isBookmarked = !isBookmarked!!

                // Update Room via ViewModel
                viewModel.setBookmark(news.copy(isBookmarked = isBookmarked), "headline")
                pagingItems.refresh()

            }) {
                Icon(
                    imageVector = if (isBookmarked == true) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark"
                )
            }
            // 🖼 News Image
            news.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = news.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // 📰 Title
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 📄 Description
                news.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // 🧾 Metadata Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = news.source.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = formatTimestamp(news.publishedAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: String): String {
    return try {
        val dateTime = ZonedDateTime.parse(timestamp)
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    } catch (e: Exception) {
        timestamp
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AppendLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun RetryAppendItem(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onRetry) {
            Text("Retry")
        }
    }
}
