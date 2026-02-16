package com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.samuelseptiano.mvvmroom.data.model.News
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


/**
 * Created by samuel.septiano on 14/02/2026.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkDetailScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    news: News,
    onBack: () -> Unit
) {

    var isBookmarked by remember { mutableStateOf(news.isBookmarked) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        viewModel.refreshHeadlines() // or pass pagingItems.refresh() directly
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            // News Image
            news.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = news.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatTimestamp(news.publishedAt),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.End
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = news.source.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                news.author?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description / Content
            news.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            news.content?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bookmark / Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    /* Toggle bookmark */
                    isBookmarked = !isBookmarked!!

                    // Update Room via ViewModel
                    viewModel.setBookmark(news.copy(isBookmarked = isBookmarked), "headline")

                }) {
                    Icon(
                        imageVector = if (isBookmarked == true) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }

                IconButton(onClick = { /* Share news URL */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
