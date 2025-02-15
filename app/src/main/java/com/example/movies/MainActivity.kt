package com.example.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.userInterface.DetailsScreen
import com.example.movies.userInterface.HomeScreen
import com.example.movies.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                val viewModel: MovieViewModel = hiltViewModel()
                AppNavigation(viewModel)
            }
        }
    }
}
@Composable
fun AppNavigation(viewModel: MovieViewModel) {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Ensures UI starts below the notification panel
        ) {
            composable("home") {
                HomeScreen(viewModel) { itemId ->
                    navController.navigate("details/$itemId")
                }
            }
            composable("details/{itemId}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")?.toInt() ?: 0
                DetailsScreen(itemId, viewModel)
            }
        }
    }
}
