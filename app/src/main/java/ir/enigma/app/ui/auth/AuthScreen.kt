package ir.enigma.app.ui.auth


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.auth.AuthViewModel.Companion.EMAIL_VERIFICATION
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.*
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.SharedPrefManager
import ir.enigma.app.util.StructureLayer


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val iconId = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf("") }


    val forLoginState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val state = authViewModel.state.value
    if (state.data != null) {
        LaunchedEffect(key1 = Unit) {

            navController.navigate(Screen.MainScreen.name) {
                popUpTo(Screen.AuthScreen.name) {
                    inclusive = true
                }
            }

        }
    } else if (state.status == ApiStatus.SUCCESS && state.message == EMAIL_VERIFICATION) {
        forLoginState.value = true
    }

    ApiScreen(
        backgroundColor = MaterialTheme.colors.background,
        apiResult = authViewModel.state,
    ) {
        AnimatedContent(
            targetState = forLoginState.value,
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(durationMillis = 200)
                    ) with slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 200)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 200)
                    ) with slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge)
                    .verticalScroll(scrollState)
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

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
                    }
                }

                MyLog.log(
                    StructureLayer.Screen,
                    "Composable",
                    "AuthScreen",
                    LogType.Info,
                    "authViewModel state: ${authViewModel.state.value}"
                )

                AuthForm(
                    name = name,
                    email = email,
                    password = password,
                    iconId = iconId,
                    loading = authViewModel.state.value is ApiResult.Loading,
                    onClickGoogle = {},
                    onSubmit = { forLogin ->
                        MyLog.log(
                            StructureLayer.Screen,
                            "Composable",
                            "AuthScreen",
                            LogType.Info,
                            "onSubmit for ${if (forLogin) "login" else "register"} , email: ${email.value} , name: ${name.value} , iconId: ${iconId.value} "
                        )
                        if (forLogin) authViewModel.login(
                            sharedPrefManager,
                            email.value,
                            password.value
                        )
                        else authViewModel.register(
                            name = name.value,
                            email = email.value,
                            password = password.value,
                            iconId = iconId.value,
                        )
                    },
                    forLogin = it,
                    forLoginState = forLoginState,
                )

            }
        }

    }

}


