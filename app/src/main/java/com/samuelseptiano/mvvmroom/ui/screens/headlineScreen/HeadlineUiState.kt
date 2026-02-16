package com.samuelseptiano.mvvmroom.ui.screens.headlineScreen

import com.samuelseptiano.mvvmroom.data.model.News
import java.util.Collections.emptyList

/**
 * Created by samuel.septiano on 24/10/2025.
 */
data class HeadlineUiState(
    val newsList: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isAppending: Boolean = false,
    val error: Throwable? = null,
    val currentPage: Int = 1,
    val endReached: Boolean = false
)