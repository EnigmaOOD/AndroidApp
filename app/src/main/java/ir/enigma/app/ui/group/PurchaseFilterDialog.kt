package ir.enigma.app.ui.group

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.enigma.app.component.*

@Composable
fun PurchaseFilterDialog(onAction: (Int) -> Unit, onDismiss: () -> Unit) {
    val selectedIndex = remember { mutableStateOf(0) }
    val grpCategoriesName =
        listOf("خریدهای شما", "گران\u200Cترین", "ارزان\u200Cترین", "قدیمی\u200Cترین", "جدیدترین")

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(elevation = 8.dp) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                TextBody2(text = "فیلتر مورد نظر خود را انتخاب کنید:")
                MVSpacer()
                MultiToggleButton(
                    options = grpCategoriesName,
                    selectedIndex = selectedIndex.value,
                    onSelectedIndexChange = { index -> selectedIndex.value = index }
                )
                MVSpacer()
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
                        onClick = { onAction(selectedIndex.value) },
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