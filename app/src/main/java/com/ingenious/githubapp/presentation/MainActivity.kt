package com.ingenious.githubapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.ingenious.githubapp.presentation.navigation.Route
import com.ingenious.githubapp.presentation.ui.theme.GithubAppTheme
import com.ingenious.githubapp.presentation.userdetails.UserDetailsScreen
import com.ingenious.githubapp.presentation.userdetails.UserDetailsViewModel
import com.ingenious.githubapp.presentation.userlist.UserListScreen
import com.ingenious.githubapp.presentation.userlist.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun AppNavHost(navController: NavHostController) {
        Scaffold {
            NavHost(
                navController = navController,
                startDestination = Route.UserList,
            ) {
                composable<Route.UserList> {
                    val viewmodel = hiltViewModel<UserListViewModel>()
                    val items = viewmodel.uiState.collectAsLazyPagingItems()

                    UserListScreen(
                        items = items,
                        onUserClicked = { login ->
                            navController.navigate(Route.UserDetail(login))
                        },
                    )
                }
                composable<Route.UserDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.UserDetail>()
                    val viewModel = hiltViewModel<UserDetailsViewModel>()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    UserDetailsScreen(
                        state = state,
                        navigateBack = { navController.popBackStack() },
                        loadUserDetails = { viewModel.getUserDetails(route.login) }
                    )
                }
            }
        }
    }
}