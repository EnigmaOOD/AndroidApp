package ir.enigma.app.ui.group

import InputTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.component.SHSpacer
import ir.enigma.app.component.TextBody2
import ir.enigma.app.component.TextSubtitle1

@Composable
fun PurchaseFilterDialog(onDismiss: () -> Unit) {
    val filter = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(elevation = 8.dp) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                TextBody2(text = "فیلتر")

                InputTextField(
                    text = filter,
                    label = "خریدار",
                    onValueChange = { filter.value = it })

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = { onDismiss() },
                    ) {
                        TextSubtitle1(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "انصراف"
                        )
                    }
                    SHSpacer()
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = {
//                            Toast.makeText(context, searchedFood, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                    ) {
                        TextSubtitle1(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            text = "تایید"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun m() {
    RtlThemePreview {
        var showCustomDialog = remember { mutableStateOf(true) }
        if (showCustomDialog.value) {
            PurchaseFilterDialog() {
                showCustomDialog.value = !showCustomDialog.value
            }
        }
    }
}