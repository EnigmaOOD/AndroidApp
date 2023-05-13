package ir.enigma.app.ui.auth

import InputTextField
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.data.userAvatars
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.theme.IconVeryLarge
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.ui.group.PurchaseFilterDialog
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.IconExtraLarge

@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
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

    if (state is ApiResult.Success)
        LaunchedEffect(key1 = Unit) {
            navController.popBackStack()
        }

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
                    text = name,
                    label = "نام و نام خانوادگی",
                    onValueChange = { name.value = it }
                )
            }
            EasyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                onClick = {
                    authViewModel.editProfile(name.value, iconId.value)
                    navController.navigate(Screen.MainScreen.name)
                },
                text = "تایید"
            )
        }
    }
}