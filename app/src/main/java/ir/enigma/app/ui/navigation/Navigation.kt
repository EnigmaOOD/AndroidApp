package ir.enigma.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.ui.auth.AuthScreen
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.group.BuyScreen
import ir.enigma.app.ui.group.GroupMembersScreen
import ir.enigma.app.ui.group.GroupScreen
import ir.enigma.app.ui.main.MainScreen


@Composable
fun Navigation(
) {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.name
    ) {

        composable(route = Screen.MainScreen.name) {
            MainScreen(navController = navController)
        }

        composable(route = Screen.AuthScreen.name) {
            AuthScreen(navController = navController , authViewModel)
        }

        composable(route = Screen.BuyScreen.name) {
            BuyScreen(navController = navController)
        }

        composable(route = Screen.GroupMembersScreen.name) {
            GroupMembersScreen(navController = navController)
        }

        composable(route = Screen.GroupScreen.name) {
            GroupScreen(navController = navController)
        }

        composable(route = Screen.AddGroupScreen.name) {
            GroupScreen(navController = navController)
        }

    }
}

