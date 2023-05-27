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
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer

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
            SplashScreen(navController = navController, authViewModel)
            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to SplashScreen",
            )
        }

        composable(route = Screen.MainScreen.name) {
            MainScreen(
                navController = navController,
                mainViewModel
            )
            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to MainScreen",
            )
        }

        composable(route = Screen.AuthScreen.name) {
            AuthScreen(navController = navController, authViewModel)

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to AuthScreen",
            )
        }




        composable(route = Screen.EditProfileScreen.name) {
            EditProfileScreen(navController = navController, authViewModel)

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to EditProfileScreen",
            )
        }

        composable(route = Screen.GroupMembersScreen.name) {
            GroupMembersScreen(navController = navController, groupViewModel)

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to GroupMembersScreen",
            )
        }

        composable(route = Screen.GroupScreen.name + "/{groupId}") { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")!!.toInt()
            GroupScreen(
                navController = navController,
                groupViewModel,
                groupId
            )

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to GroupScreen with groupId: $groupId",
            )
        }

        composable(route = Screen.AddGroupScreen.name) {
            AddGroupScreen(navController = navController, addGroupViewModel)

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to AddGroupScreen",
            )
        }

        composable(route = Screen.NewPurchaseScreen.name + "/{amount}") {
            val amount = it.arguments?.getString("amount")!!

            NewPurchaseScreen(
                navController = navController, groupViewModel,
                amount.toDouble()
            )

            MyLog.log(
                StructureLayer.Screen,
                "Composable",
                "Navigation",
                type = LogType.Info,
                "to NewPurchaseScreen with amount: $amount",
            )
        }

    }
}

