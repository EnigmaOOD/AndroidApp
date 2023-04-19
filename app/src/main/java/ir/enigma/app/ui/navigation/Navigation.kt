package ir.enigma.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.ui.auth.AuthScreen
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.group.*
import ir.enigma.app.ui.main.AddGroupScreen
import ir.enigma.app.ui.main.MainScreen

@Composable
fun Navigation(
) {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    val groupViewModel = GroupViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.name
    ) {

        composable(route = Screen.MainScreen.name) {
            MainScreen(
                navController = navController
            )
        }

        composable(route = Screen.AuthScreen.name) {
            AuthScreen(navController = navController, authViewModel)
        }

        composable(route = Screen.BuyScreen.name) {
            BuyScreen(navController = navController)
        }

        composable(route = Screen.GroupMembersScreen.name) {
            GroupMembersScreen(navController = navController, groupViewModel)
        }

        composable(route = Screen.GroupScreen.name) {
            GroupScreen(navController = navController, groupViewModel)
        }

        composable(route = Screen.AddGroupScreen.name) {
            AddGroupScreen(navController = navController)
        }

        composable(route = Screen.NewPurchaseScreen.name) {
            NewPurchaseScreen(navController = navController)
        }

    }
}

