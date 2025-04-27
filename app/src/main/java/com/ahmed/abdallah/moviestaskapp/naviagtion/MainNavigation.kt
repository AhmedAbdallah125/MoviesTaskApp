package com.ahmed.abdallah.moviestaskapp.naviagtion

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class MainNavigation(val route: String) {

    data object Home : MainNavigation("home")

    data object Details : MainNavigation("details") {
        const val id = "id"
        val routeWithArg = "$route/{$id}"
        val navArguments = listOf(navArgument(id) { type = NavType.StringType })
    }

}