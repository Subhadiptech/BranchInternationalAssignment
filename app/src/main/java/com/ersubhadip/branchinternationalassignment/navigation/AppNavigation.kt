package com.ersubhadip.branchinternationalassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ersubhadip.branchinternationalassignment.presentation.chat.ChatScreen
import com.ersubhadip.branchinternationalassignment.presentation.home.HomeScreen
import com.ersubhadip.branchinternationalassignment.presentation.login.LoginScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Destinations.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Destinations.Chat.route + "/{id}/{user_id}/{date}/{body}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("user_id") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("date") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("body") {
                    type = NavType.StringType
                    nullable = false
                }

            )
        ) { navBackStack ->
            val id = navBackStack.arguments?.getString("id")
            val userID = navBackStack.arguments?.getString("user_id")
            val date = navBackStack.arguments?.getString("date")
            val body = navBackStack.arguments?.getString("body")
            if (id != null && userID != null && date != null && body != null) {
                ChatScreen(
                    id = id.toInt(),
                    userId = userID,
                    body = body,
                    date = date,
                    navController = navController
                )
            } else {
                return@composable
            }
        }
    }
}