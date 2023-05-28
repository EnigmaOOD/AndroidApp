package ir.enigma.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ir.enigma.app.R
import ir.enigma.app.component.EasyButton
import ir.enigma.app.component.LVSpacer
import ir.enigma.app.data.ApiResult
import ir.enigma.app.repostitory.UserRepository.Companion.UN_AUTHORIZE_ERROR
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.SpaceLarge
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.SharedPrefManager
import ir.enigma.app.util.StructureLayer
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier.fillMaxSize().padding(SpaceLarge),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            val loadedToken =
                remember { SharedPrefManager(context).getString(SharedPrefManager.KEY_TOKEN) }


            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Jiringi logo"
            )

            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h3
            )
            LVSpacer()
            LVSpacer()
            LaunchedEffect(Unit) {
                authViewModel.checkForToken(sharedPrefManager)
                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "SplashScreen",
                    type = LogType.Info,
                    "SplashScreen check for token from authViewModel",
                )
            }
            if (loadedToken == null || authViewModel.state.value.message == UN_AUTHORIZE_ERROR) {
                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "SplashScreen",
                    type = LogType.Info,
                    "SplashScreen loadedToken == null || " +
                            "authViewModel.state.value.message == UN_AUTHORIZE_ERROR  " +
                            "navigate to AuthScreen",
                )
                LaunchedEffect(key1 = Unit) {
                    delay(2000)
                    navController.navigate(Screen.AuthScreen.name) {
                        popUpTo(Screen.SplashScreen.name) {
                            inclusive = true
                        }
                    }
                }
            } else if (authViewModel.state.value is ApiResult.Loading) {
                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "SplashScreen",
                    type = LogType.Info,
                    "SplashScreen Loading",
                )
                CircularProgressIndicator()
            } else if (authViewModel.state.value is ApiResult.Success) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Screen.MainScreen.name) {
                        popUpTo(Screen.SplashScreen.name) {
                            inclusive = true
                        }
                    }
                }
                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "SplashScreen",
                    type = LogType.Info,
                    "SplashScreen Success load token and set me, navigate to main",
                )
            } else if (authViewModel.state.value is ApiResult.Error) {

                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "SplashScreen",
                    type = LogType.Info,
                    "SplashScreen Error: message: ${authViewModel.state.value.message}, show try again",
                )

                EasyButton(
                    text = "تلاش مجدد",
                    onClick = {
                        authViewModel.checkForToken(sharedPrefManager)
                    }
                )

            }
        }

    }
}
