package com.samuelseptiano.mvvmroom.ui.screens

/**
 * Created by samuel.septiano on 14/02/2026.
 */
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samuelseptiano.mvvmroom.ui.MainDrawerNavHost
import com.samuelseptiano.mvvmroom.ui.screens.bookmarkScreen.BookmarkViewModel
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineViewModel
import com.samuelseptiano.mvvmroom.ui.screens.searchScreen.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: HeadlineViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val mainNavController = rememberNavController()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "headlines"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                selectedRoute = currentRoute,
                onDestinationClicked = { route ->
                    scope.launch { drawerState.close() }
                    mainNavController.navigate(route) {
                        launchSingleTop = true
                        popUpTo("main") { saveState = true }
                        restoreState = true
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("News App") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, null)
                        }
                    }
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                MainDrawerNavHost(
                    navController = mainNavController,
                    rootNavController = navController,
                    viewModel = viewModel,
                    bookmarkViewModel = bookmarkViewModel,
                    searchViewModel = searchViewModel
                )
            }
        }
    }
}


