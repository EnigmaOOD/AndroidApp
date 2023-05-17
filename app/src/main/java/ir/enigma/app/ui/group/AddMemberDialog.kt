package ir.enigma.app.ui.group

import InputTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus

@Composable
fun AddMemberDialog(onDismiss: () -> Unit, state: ApiResult<Any>, onAction: (String) -> Unit) {

    val email = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(elevation = 8.dp) {
            Column(
                Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                TextBody2(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ایمیل عضو جدید را وارد نمایید:"
                )

                InputTextField(
                    text = email,
                    keyboardType = KeyboardType.Email,
                    error = state.message ?: "",
                    hasError = state.status == ApiStatus.ERROR,
                    showError = true,
                    onValueChange = { email.value = it })

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        onClick = { onDismiss() },
                    ) {
                        Text(
                            text = "انصراف"
                        )
                    }
                    SHSpacer()
                    LoadingButton(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        onClick = {
                            onAction(email.value)
                        },
                        loading = state.status == ApiStatus.LOADING,
                        actionText = "افزودن"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun v() {
    RtlThemePreview {
        var showCustomDialog = remember { mutableStateOf(true) }
        if (showCustomDialog.value) {
            AddMemberDialog({},
                ApiResult.Error(_message = "کاربر با این ایمیل وجود ندارد"),
                { email ->
                    showCustomDialog.value = !showCustomDialog.value
                }
            )
        }
    }
}