package com.ersubhadip.branchinternationalassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.Splash.route) {

        }
        composable(route = Destinations.Home.route) {

        }
        composable(
            route = Destinations.Chat.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { navBackStack ->
            val data = navBackStack.arguments?.getString("id")
        }
    }
}