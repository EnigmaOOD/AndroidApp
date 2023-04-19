package ir.enigma.app.ui.auth


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.Message
import ir.enigma.app.ui.MessageType
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.SpaceLarge


@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    //todo: remove default values
    val name = remember { mutableStateOf("a") }
    val email = remember { mutableStateOf("a@a.com") }
    val iconId = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf("12345678") }

    authViewModel.checkForToken(context = context)

    if (authViewModel.state.value is ApiResult.Success) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(Screen.MainScreen.name)
        }
    }

    ApiScreen(
        backgroundColor = MaterialTheme.colors.background,
        apiResult = authViewModel.state,
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(SpaceLarge)) {

            //todo: logo and app name

            AuthForm(
                name = name,
                email = email,
                password = password,
                loading = authViewModel.state.value is ApiResult.Loading,
                onClickGoogle = {},
                onSubmit = { forLogin ->
                    if (forLogin)
                        authViewModel.login(email.value, password.value)
                    else
                        authViewModel.register(
                            context = context,
                            name = name.value,
                            email = email.value,
                            password = password.value,
                            iconId = iconId.value,
                        )
                }
            )
        }
    }

    Log.d("Screen", "AuthScreen: " + authViewModel.state)


}