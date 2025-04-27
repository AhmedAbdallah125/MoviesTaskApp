package com.ahmed.abdallah.moviestaskapp.naviagtion

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class MainNavigation(val route: String) {

    data object Home : MainNavigation("home")

    data object Details : MainNavigation("details") {
        const val movieId = "id"
        val routeWithArg = "$route/{$movieId}"
        val navArguments = listOf(navArgument(movieId) { type = NavType.StringType })
    }

}