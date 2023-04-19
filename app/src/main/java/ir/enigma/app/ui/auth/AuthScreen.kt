package ir.enigma.app.ui.auth


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.*


@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    //todo: remove default values
    val name = remember { mutableStateOf("a") }
    val email = remember { mutableStateOf("a@a.com") }
    val iconId = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf("12345678") }

    authViewModel.checkForToken(context = context)

    val scrollState = rememberScrollState()
    val isShowDialogCharacter = remember { mutableStateOf(false) }

    if (authViewModel.state.value is ApiResult.Success) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate(Screen.MainScreen.name)
        }
    }

    ApiScreen(
        backgroundColor = MaterialTheme.colors.background,
        apiResult = authViewModel.state,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(.75f),
                    painter = painterResource(id = R.drawable.logo_jiringi),
                    contentDescription = "Jiringi logo"
                )

                TextH4(
                    text = "جیرینگی",
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )

                LVSpacer()

                CardWithImageOrIcon(
                    icon = false,
                    resource = R.drawable.avt_0,
                    size = IconVeryLarge
                ) {
                    isShowDialogCharacter.value = !isShowDialogCharacter.value
                }
                HintText(
                    text = "کاراکتر خود را انتخاب کنید",
                    color = MaterialTheme.colors.onSurface
                )
            }

            if (isShowDialogCharacter.value) {
                Dialog(onDismissRequest = {
                    isShowDialogCharacter.value = !isShowDialogCharacter.value
                }) {
                    Card(elevation = 8.dp) {
                        Column(
                            Modifier
                                .background(Color.White)
                                .padding(SpaceSmall)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextSubtitle2(text = "انتخاب کاراکتر")
                                ExitIconButton() {
                                    isShowDialogCharacter.value = !isShowDialogCharacter.value
                                }
                            }

                            TVSpacer()

                            LazyColumn() {
                                items(5) { indexC ->
                                    LazyRow(
                                        modifier = Modifier.fillParentMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        items(4) { indexR ->
                                            val resourceName =
                                                "R.drawable.avt_${indexR + indexC}"
                                            CardWithImageOrIcon(
                                                icon = false,

                                                //ToDo: replace resourceName instead of R.drawable.avt_9
                                                resource = R.drawable.avt_9,

                                                size = IconSemiLarge
                                            ) {
                                                isShowDialogCharacter.value =
                                                    !isShowDialogCharacter.value
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            AuthForm(name = name,
                email = email,
                password = password,
                loading = authViewModel.state.value is ApiResult.Loading,
                onClickGoogle = {},
                onSubmit = { forLogin ->
                    if (forLogin) authViewModel.login(email.value, password.value)
                    else authViewModel.register(
                        context = context,
                        name = name.value,
                        email = email.value,
                        password = password.value,
                        iconId = iconId.value,
                    )
                })
        }
    }

    Log.d("Screen", "AuthScreen: " + authViewModel.state)
}