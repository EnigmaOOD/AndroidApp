package ir.enigma.app

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.model.User
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.SplashScreen
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.auth.AuthScreen
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.auth.EditProfileScreen
import ir.enigma.app.ui.group.*
import ir.enigma.app.ui.main.AddGroupScreen
import ir.enigma.app.ui.main.MainScreen
import ir.enigma.app.ui.main.MainViewModel
import ir.enigma.app.ui.navigation.Screen


@Composable
fun TestNavHost(
    navController: NavHostController,
    mockMainRepository: MainRepository,
    mockUserRepository: UserRepository,
    startDestination: String = Screen.SplashScreen.name
) {
    val groupViewModel = GroupViewModel(mockMainRepository)
    val authViewModel = AuthViewModel(mockUserRepository)
    val mainViewModel = MainViewModel(mockMainRepository)
    val addGroupViewModel = AddGroupViewModel(mockMainRepository)
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.SplashScreen.name) {
            SplashScreen(navController = navController, authViewModel)
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

        composable(route = Screen.AddGroupScreen.name) {
            AddGroupScreen(navController = navController, addGroupViewModel)
        }

        composable(route = Screen.GroupScreen.name) {
            GroupScreen(navController = navController, groupViewModel, 0)
        }

        composable(route = Screen.GroupScreen.name) {
            GroupScreen(
                navController = navController,
                groupViewModel,
                groupId = 0
            )
        }

        composable(route = Screen.NewPurchaseScreen.name + "/{amount}") {
            val amount = it.arguments?.getString("amount")!!
            NewPurchaseScreen(
                navController = navController, groupViewModel,
                amount.toDouble()
            )
        }

    }



}

