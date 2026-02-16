package com.samuelseptiano.mvvmroom.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samuelseptiano.mvvmroom.data.model.News
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkScreen
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkViewModel
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineScreen
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineViewModel
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchDetailScreen
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchScreen
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Created by samuel.septiano on 14/02/2026.
 */
@Composable
fun MainDrawerNavHost(
    navController: NavHostController,
    rootNavController: NavHostController,
    viewModel: HeadlineViewModel,
    bookmarkViewModel: BookmarkViewModel,
    searchViewModel: SearchViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "headlines"
    ) {

        composable("headlines") {
            HeadlineScreen(viewModel) { news ->
                val json = Uri.encode(Json.encodeToString(news))
                rootNavController.navigate("news_detail/$json")
            }
        }

        composable("bookmarks") {
            BookmarkScreen(bookmarkViewModel) { news ->
                val json = Uri.encode(Json.encodeToString(news))
                rootNavController.navigate("bookmark_detail/$json")
            }
        }

        composable("search") {
            SearchScreen(searchViewModel) { news ->
                val json = Uri.encode(Json.encodeToString(news))
                rootNavController.navigate("search_detail/$json")
            }
        }
    }
}
