package ir.enigma.app.ui.group.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.ui.main.component.SettleUpAmount
import ir.enigma.app.ui.main.component.ShimmerItem
import ir.enigma.app.ui.main.component.ShimmerRectangle
import ir.enigma.app.ui.theme.*
import kotlin.math.abs

@Composable
fun GroupButtonbar(
    amount: Double,
    currency: String,
    shimmer: Boolean = false,
    onLeaveGroup: () -> Unit = {},
    onSettleUp: (Double) -> Unit = {},
    onAddPurchase: () -> Unit
) {
    Box() {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .height(72.dp)
        ) {
            CardWithImageOrIcon(
                modifier = Modifier.testTag("addPurchase"),
                icon = true,
                resource = R.drawable.ic_fill_add,
                size = IconSemiLarge,
                tint = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp, onClick = onAddPurchase
            )
        }

        Card(
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = RoundedCornerShape(0),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 2.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (shimmer) {
                    ShimmerRectangle()
                    ShimmerRectangle()
                } else {
                    SettleUpAmount(
                        amount = abs(amount),
                        currency = currency,
                        isCredit = amount > 0
                    )

                    OutlinedButton(
                        modifier = Modifier.testTag("leaveOrSettleUp"),
                        onClick = {
                            if (amount == 0.0) {
                                onLeaveGroup()
                            } else {
                                onSettleUp(amount)
                            }
                        },
                        border = BorderStroke(
                            color = MaterialTheme.colors.onBackgroundAlpha3,
                            width = BorderThin
                        ),
                    ) {
                        if (amount == 0.0) {
                            TextBody2(text = "ترک گروه" , color = MaterialTheme.colors.error)
                        } else {
                            TextBody2(text = "تسویه حساب")
                        }
                    }
                }
            }
        }
    }
}
