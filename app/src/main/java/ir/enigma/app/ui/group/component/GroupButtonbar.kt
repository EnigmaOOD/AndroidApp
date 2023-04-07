package ir.enigma.app.ui.group.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.ui.main.component.SettleUpAmount
import ir.enigma.app.ui.theme.IconLarge

@Composable
fun GroupButtonbar(amount: Double, currency: String, isCredit: Boolean) {
    Box() {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .height(72.dp)
        ) {
            CardWithImageOrIcon(
                icon = true,
                resource = R.drawable.ic_fill_add,
                size = IconLarge,
                tint = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp
            )
        }

        Card(
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = RoundedCornerShape(0),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SettleUpAmount(
                    amount = amount,
                    currency = currency,
                    isCredit = isCredit
                )

                OutlinedButton(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colors.onBackground.copy(alpha = .3f)
                    ),
                ) {
                    TextBody2(text = "تسویه حساب")
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun p() {
    RtlThemePreview {
        GroupButtonbar(amount = 13000.0, currency = "تومان", isCredit = false)
    }
}