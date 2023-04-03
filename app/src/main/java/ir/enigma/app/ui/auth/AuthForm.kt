package ir.digiapp.flashcardstoreapp.ui.screens.auth

import InputTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import ir.enigma.app.ui.auth.isValidEmailAddress
import ir.enigma.app.component.EasyButton
import ir.enigma.app.component.HintText
import ir.enigma.app.component.MVSpacer
import ir.enigma.app.ui.theme.SpaceSmall
import ir.enigma.app.ui.theme.SpaceThin

@Composable
fun AuthForm(
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

    val errors = listOf(!isValidEmailAddress(email.value), password.value.length < 8)
    val valid = !errors.contains(true)


    InputTextField(
        keyboardType = KeyboardType.Email,
        text = email,
        showError = showErrors.value, hasError = errors[0],
    )

    val submit = {
        showErrors.value = true
        //if (valid && !loading) { todo
        onSubmit(forLogin.value)

        //}
    }


    InputTextField(
        keyboardType = KeyboardType.Password,
        text = password,
        showError = showErrors.value, hasError = errors[1], imeAction = ImeAction.Done,
        onAction = { submit() },
    )

    MVSpacer()

    EasyButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = submit,

    ) {

        Text(
            text = if (forLogin.value) "ورود" else "نام\u200Cثبت",
        )
    }
    HintText(
        text = "یا",
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceThin),
        textAlign = TextAlign.Center
    )


    Spacer(modifier = Modifier.height(SpaceSmall))

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClickGoogle
    ) {
        Text(text = "ورود با گوگل")
    }


}

