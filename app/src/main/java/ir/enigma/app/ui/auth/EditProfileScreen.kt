package ir.enigma.app.ui.auth

import InputTextField
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.data.userAvatars
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.IconExtraLarge
import ir.enigma.app.util.SharedPrefManager

@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val isShowDialogCharacter = remember { mutableStateOf(false) }
    val iconId = remember { mutableStateOf(me.iconId) }
    val name = remember { mutableStateOf(me.name) }

    if (isShowDialogCharacter.value) {
        AvatarSelectorDialog(isShowDialogCharacter, iconId)
    }

    val state = authViewModel.editUserState.value

    if (state is ApiResult.Loading)
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }

    if (state is ApiResult.Success) {
        LaunchedEffect(key1 = Unit) {
            Log.d("Screen", "EditProfileScreen: navController.popBackStack()")
            navController.popBackStack()
            authViewModel.editFinish()
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary
    )


    ApiScreen(
        modifier = Modifier.fillMaxSize(),
        apiResult = authViewModel.editUserState,
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                TextH6(text = "ویرایش اطلاعات حساب کاربری", color = MaterialTheme.colors.onPrimary)
            }
        },
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardWithImageOrIcon(
                        modifier = Modifier.testTag("characterCard"),
                        icon = false,
                        resource = userAvatars[iconId.value],
                        size = IconExtraLarge
                    ) {
                        isShowDialogCharacter.value = !isShowDialogCharacter.value
                    }
                    TVSpacer()
                    HintText(
                        text = "برای تغییر کاراکتر خود کلیک کنید",
                        color = MaterialTheme.colors.onSurface
                    )
                }
                LVSpacer()
                TextBody1(text = "ایمیل شما: ${me.email}")
                LVSpacer()
                InputTextField(
                    modifier = Modifier.testTag("nameTextField"),
                    text = name,
                    label = "نام و نام خانوادگی",
                    showError =true,
                    hasError = name.value.isEmpty(),
                    error = "نام و نام خانوادگی نمی\u200Cتواند خالی باشد",
                    onValueChange = { name.value = it }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                        .testTag("exitButton"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    onClick = {
                        authViewModel.logout(context)
                        navController.navigate(Screen.AuthScreen.name) {
                            popUpTo(Screen.EditProfileScreen.name) {
                                inclusive = true
                            }
                        }
                    },

                    ) {

                    Text("خروج از حساب کاربری")

                }
            }
            EasyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .testTag("editButton"),
                onClick = {
                    if (name.value.isNotEmpty()){
                        authViewModel.editProfile(name.value, iconId.value)
                    }
                },
                text = "تایید"
            )
        }
    }
}