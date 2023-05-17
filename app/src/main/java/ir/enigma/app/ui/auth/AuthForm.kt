package ir.enigma.app.ui.auth

import InputTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import ir.enigma.app.component.*
import ir.enigma.app.R
import ir.enigma.app.data.userAvatars
import ir.enigma.app.ui.theme.*

@Composable
fun AuthForm(
    name: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    iconId: MutableState<Int>,
    loading: Boolean,
    forLoginState: MutableState<Boolean>,
    forLogin: Boolean,
    onClickGoogle: () -> Unit,
    onSubmit: (forLogin: Boolean) -> Unit,
) {
    val avatarSelectorDialog = remember { mutableStateOf(false) }

    val showErrors = remember {
        mutableStateOf(false)
    }


    val errors: List<Boolean>
    val title: String
    val changeScreenText: String
    val titleInvert: String
    val appName = stringResource(R.string.app_name)
    val passwordError: String
    if (forLogin) {
        title = "ورود"
        changeScreenText = "حسابی در $appName ندارید؟"
        titleInvert = "ثبت‌نام"
        errors = listOf(
            false,
            !isValidEmailAddress(email.value),
            password.value.isEmpty()
        )
        passwordError = "رمز عبور نمی\u200Cتواند خالی باشد"

    } else {
        title = "ثبت‌نام"
        changeScreenText = "قبلا در $appName حساب ساخته\u200Cاید؟"
        titleInvert = "ورود"
        errors = listOf(
            name.value.isEmpty(),
            !isValidEmailAddress(email.value),
            password.value.length < 8
        )
        passwordError = "رمز عبور باید حداقل ۸ کاراکتر باشد"
    }
    val valid: Boolean = !errors.contains(true)

    val submit = {
        showErrors.value = true
        if (valid && !loading)
            onSubmit(forLogin)

    }

    Column(modifier = Modifier.fillMaxWidth()) {

        LVSpacer()

        TextH5(title);



        if (avatarSelectorDialog.value)
            AvatarSelectorDialog(avatarSelectorDialog, iconId)

        if (!forLogin) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CardWithImageOrIcon(
                    icon = false,
                    resource = userAvatars[iconId.value],
                    size = IconExtraLarge
                ) {
                    avatarSelectorDialog.value = true
                }
                HintText(
                    text = "کاراکتر خود را انتخاب کنید",
                    color = MaterialTheme.colors.onSurface
                )

                MVSpacer()

                InputTextField(
                    modifier = Modifier.testTag("nameTextField"),
                    label = "نام و نام خانوادگی",
                    text = name,
                    imeAction = ImeAction.Next,
                    showError = showErrors.value, hasError = errors[0],
                )

            }
        }
        if (forLogin)
            LVSpacer()

        InputTextField(
            keyboardType = KeyboardType.Email,
            text = email,
            imeAction = ImeAction.Next,
            showError = showErrors.value, hasError = errors[1],
        )

        InputTextField(
            keyboardType = KeyboardType.Password,
            text = password,
            showError = showErrors.value, hasError = errors[2], imeAction = ImeAction.Done,
            error = passwordError,
            onAction = { submit() },
        )

        MVSpacer()

        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = submit,
            actionText = title,
            loading = loading
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HintText(changeScreenText)
            TextButton(
                contentPadding = PaddingValues(SpaceThin),
                onClick = {
                    showErrors.value = false
                    forLoginState.value = !forLoginState.value
                },

                ) {
                Text(titleInvert)
            }
        }
    }
}


@Composable
fun AvatarSelectorDialog(
    isShowDialogCharacter: MutableState<Boolean>,
    iconIndex: MutableState<Int>
) {
    if (isShowDialogCharacter.value) {
        Dialog(onDismissRequest = {
            isShowDialogCharacter.value = false
        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                MVSpacer()
                TextH5("انتخاب کاراکتر")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(SpaceMedium)
                ) {
                    itemsIndexed(userAvatars) { index, icon ->
                        Image(
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(SpaceMedium)
                                .clickable {
                                    iconIndex.value = index
                                    isShowDialogCharacter.value = false
                                }
                                .border(
                                    color = if (iconIndex.value == index) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        Color.Transparent
                                    },
                                    shape = CircleShape,
                                    width = BorderThin
                                )
                        )
                    }
                }

            }

        }
    }
}

