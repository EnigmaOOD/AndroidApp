package ir.digiapp.flashcardstoreapp.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import ir.digiapp.flashcardstoreapp.ui.components.CustomButton
import ir.digiapp.flashcardstoreapp.ui.components.CustomOutlinedButton
import ir.digiapp.flashcardstoreapp.ui.components.HintText
import ir.digiapp.flashcardstoreapp.ui.components.input.INPUT_TYPE_EMAIL
import ir.digiapp.flashcardstoreapp.ui.components.input.INPUT_TYPE_PASSWORD
import ir.digiapp.flashcardstoreapp.ui.components.input.InputTextField
import ir.digiapp.flashcardstoreapp.ui.theme.ColorGoogle
import ir.digiapp.flashcardstoreapp.ui.theme.Dimen
import ir.digiapp.flashcardstoreapp.ui.theme.Strings
import ir.digiapp.flashcardstoreapp.util.isValidEmailAddress

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
        inputType = INPUT_TYPE_EMAIL,
        text = email,
        showError = showErrors.value, hasError = errors[0]
    )

    val submit = {
        showErrors.value = true
        //if (valid && !loading) { todo
            onSubmit(forLogin.value)

        //}
    }


    InputTextField(
        inputType = INPUT_TYPE_PASSWORD,
        text = password,
        showError = showErrors.value, hasError = errors[1], imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = { submit() })
    )

    Spacer(modifier = Modifier.height(Dimen.smallSpace))

    CustomButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = submit,
//        enabled = valid && !loading  todo
    ) {

        Text(
            text = if (forLogin.value) Strings.LOGIN else Strings.REGISTER,
        )
    }
    HintText(
        text = Strings.OR,
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.thinSpace),
        textAlign = TextAlign.Center
    )

    CustomOutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { forLogin.value = !forLogin.value }) {
        Text(text = if (forLogin.value) Strings.REGISTER else Strings.LOGIN)
    }
    Spacer(modifier = Modifier.height(Dimen.smallSpace))
    CustomOutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(ButtonDefaults.outlinedBorder.width, ColorGoogle),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorGoogle),
        onClick = onClickGoogle) {
        Text(text = Strings.LOG_IN_WITH_GOOGLE)
    }


}

