package ir.enigma.app.ui.auth

import InputTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.enigma.app.component.*
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.R

@Composable
fun AuthForm(
    name: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    loading: Boolean,
    onClickGoogle: () -> Unit,
    onSubmit: (forLogin: Boolean) -> Unit,
) {

    val forLogin = remember {
        mutableStateOf(true)
    }

    val showErrors = remember {
        mutableStateOf(false)
    }


    val errors: List<Boolean>;

    val title: String;
    val changeScreenText: String;
    val titleInvert: String;
    val appName = stringResource(R.string.app_name)
    if (forLogin.value) {
        title = "ورود"
        changeScreenText = "حسابی در " + appName + " ندارید؟"
        titleInvert = "ثبت‌نام"
        errors = listOf(
            false,
            !isValidEmailAddress(email.value),
            false
        )
    } else {
        title = "ثبت‌نام"
        changeScreenText = "قبلا در ${stringResource(R.string.app_name)} حساب ساخته\u200Cاید؟"
        titleInvert = "ورود"
        errors = listOf(
            name.value.isEmpty(),
            !isValidEmailAddress(email.value),
            password.value.length < 8
        )
    }
    val valid: Boolean = !errors.contains(true)

    val submit = {
        showErrors.value = true
        if (valid && !loading)
            onSubmit(forLogin.value)

    }

    TextH5(title);

    LVSpacer()

    if (!forLogin.value)
        InputTextField(
            label = "نام و نام خانوادگی",
            text = name,
            imeAction = ImeAction.Next,
            showError = showErrors.value, hasError = errors[0],
        )

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
    ) { submit() }

    MVSpacer()

    LoadingButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = submit,
        actionText = title,
        loading = loading
    )

    SVSpacer()

    HintText(
        text = "یا",
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceThin),
        textAlign = TextAlign.Center
    )

    SVSpacer()

    BackgroundOutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClickGoogle
    ) {

        Text(text = "ورود با گوگل")
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HintText(changeScreenText)
        EasyButton(
            buttonTheme = ButtonTheme.PrimaryText,
            onClick = {
                showErrors.value = false
                forLogin.value = !forLogin.value
            },
            text = titleInvert
        )
    }

}


