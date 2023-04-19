package ir.enigma.app.ui.group

import InputTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.enigma.app.component.*

@Composable
fun AddMemberDialog(onDismiss: () -> Unit) {
//    val context = LocalContext.current
    val email = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(elevation = 8.dp) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                TextBody2(text = "ایمیل عضو جدید را وارد نمایید:")

                InputTextField(
                    text = email,
//                    label = "ایمیل",
                    onValueChange = { email.value = it })

                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                    ) {
                        TextSubtitle1(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "انصراف"
                        )
                    }
                    SHSpacer()
                    Button(
                        onClick = {
//                            Toast.makeText(context, searchedFood, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                    ) {
                        TextSubtitle1(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "افزودن"
                        )
                    }
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
            AddMemberDialog() {
                showCustomDialog.value = !showCustomDialog.value
            }
        }
    }
}