package com.ersubhadip.branchinternationalassignment.navigation

sealed class Destinations(val route: String) {
    object Login : Destinations(Routes.Login)
    object Home : Destinations(Routes.Home)
    object Chat : Destinations(Routes.Chat)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}