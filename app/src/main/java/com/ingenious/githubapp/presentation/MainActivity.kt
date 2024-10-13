package com.ingenious.githubapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ingenious.githubapp.presentation.navigation.Route
import com.ingenious.githubapp.presentation.ui.theme.GithubAppTheme
import com.ingenious.githubapp.presentation.userdetails.UserDetailsScreen
import com.ingenious.githubapp.presentation.userlist.UserListScreen
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
                    UserListScreen(
                        onUserClicked = { username ->
                            navController.navigate(Route.UserDetail(username))
                        },
                        viewModel = hiltViewModel(),
                    )
                }
                composable<Route.UserDetail> { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username").orEmpty()
                    UserDetailsScreen(
                        username = username,
                        viewModel = hiltViewModel()
                    )
                }
            }
        }
    }
}