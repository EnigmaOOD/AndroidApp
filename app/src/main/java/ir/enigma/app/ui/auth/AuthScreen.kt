package ir.enigma.app.ui.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ir.enigma.app.ui.theme.SpaceLarge


@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxSize().padding(SpaceLarge)) {

                //todo: logo and app name

                AuthForm(
                    name = authViewModel.name,
                    email = authViewModel.email,
                    password = authViewModel.password,
                    loading = authViewModel.loading.value,
                    onClickGoogle = {},
                    onSubmit = { forLogin ->
                        if (forLogin)
                            authViewModel.login()
                        else
                            authViewModel.register()
                    }
                )
            }
        }
    }
}