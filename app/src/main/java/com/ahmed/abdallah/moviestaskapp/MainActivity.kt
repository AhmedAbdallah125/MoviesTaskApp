package com.ahmed.abdallah.moviestaskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed.abdallah.moviestaskapp.naviagtion.MainNavigation
import com.ahmed.abdallah.moviestaskapp.presentation.details.DetailsScreen
import com.ahmed.abdallah.moviestaskapp.presentation.details.DetailsViewModel
import com.ahmed.abdallah.moviestaskapp.presentation.home.HomeScreen
import com.ahmed.abdallah.moviestaskapp.presentation.home.HomeViewModel
import com.ahmed.abdallah.moviestaskapp.ui.theme.MoviesTaskAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MoviesTaskAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    MainNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = MainNavigation.Home.route,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(100)) },
    ) {
        composable(route = MainNavigation.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = MainNavigation.Details.routeWithArg,
            arguments = MainNavigation.Details.navArguments
        ) { backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val movieId =
                backStackEntry.arguments?.getString(MainNavigation.Details.id)

            if (movieId != null) {
                DetailsScreen(
                    viewModel = viewModel,
                    id = movieId,
                    navController = navController
                )
            }
        }
    }
}