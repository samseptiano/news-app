package com.samuelseptiano.mvvmroom.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.ui.screens.MainScreen
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkViewModel
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineViewModel
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchViewModel
import kotlinx.serialization.json.Json

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Composable
fun RootNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        // Drawer section
        composable("main") {
            MainScreen(navController = navController)
        }

        composable(
            route = "news_detail/{newsJson}",
            arguments = listOf(
                navArgument("newsJson") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val newsJson =
                backStackEntry.arguments?.getString("newsJson")

            val viewModel: HeadlineViewModel = hiltViewModel()

            val news = newsJson?.let {
                Json.decodeFromString<News>(Uri.decode(it))
            }

            news?.let {
                HeadlineDetailScreen(
                    news = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "bookmark_detail/{newsJson}",
            arguments = listOf(
                navArgument("newsJson") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val newsJson =
                backStackEntry.arguments?.getString("newsJson")

            val viewModel: BookmarkViewModel = hiltViewModel()

            val news = newsJson?.let {
                Json.decodeFromString<News>(Uri.decode(it))
            }

            news?.let {
                BookmarkDetailScreen(
                    news = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = "search_detail/{newsJson}",
            arguments = listOf(
                navArgument("newsJson") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val newsJson =
                backStackEntry.arguments?.getString("newsJson")

            val viewModel: SearchViewModel = hiltViewModel()

            val news = newsJson?.let {
                Json.decodeFromString<News>(Uri.decode(it))
            }

            news?.let {
                SearchDetailScreen(
                    news = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}


