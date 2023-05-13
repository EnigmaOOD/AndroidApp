package ir.enigma.app.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.ui.SplashScreen
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.auth.AuthScreen
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.auth.EditProfileScreen
import ir.enigma.app.ui.group.*
import ir.enigma.app.ui.main.AddGroupScreen

import ir.enigma.app.ui.main.MainScreen
import ir.enigma.app.ui.main.MainViewModel

@Composable
fun Navigation(
) {
    val navController = rememberNavController()
    val groupViewModel = hiltViewModel<GroupViewModel>()
    val authViewModel = hiltViewModel<AuthViewModel>()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val addGroupViewModel = hiltViewModel<AddGroupViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.name
    ) {


        composable(route = Screen.SplashScreen.name) {
            SplashScreen(navController = navController , authViewModel)
        }

        composable(route = Screen.MainScreen.name) {
            MainScreen(
                navController = navController,
                mainViewModel
            )
        }

        composable(route = Screen.AuthScreen.name) {
            AuthScreen(navController = navController, authViewModel)
        }

        composable(route = Screen.AuthScreen.name) {
            AuthScreen(navController = navController, authViewModel)
        }

        composable(route = Screen.EditProfileScreen.name) {
            EditProfileScreen(navController = navController, authViewModel)
        }

        composable(route = Screen.GroupMembersScreen.name) {
            GroupMembersScreen(navController = navController, groupViewModel)
        }

        composable(route = Screen.GroupScreen.name + "/{groupId}") { backStackEntry ->
            GroupScreen(
                navController = navController,
                groupViewModel,
                backStackEntry.arguments?.getString("groupId")!!.toInt()
            )
        }

        composable(route = Screen.AddGroupScreen.name) {
            AddGroupScreen(navController = navController,addGroupViewModel)
        }

        composable(route = Screen.NewPurchaseScreen.name + "/{amount}") {
            val amount = it.arguments?.getString("amount")!!
            Log.d("Screen", "Navigation: $amount");
            NewPurchaseScreen(
                navController = navController, groupViewModel,
                amount.toDouble()
            )
        }

    }
}

