package ir.enigma.app.ui.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ir.enigma.app.R
import ir.enigma.app.component.HintText
import ir.enigma.app.component.THSpacer
import ir.enigma.app.component.TextBody2
import ir.enigma.app.ui.theme.IconSmall
import ir.enigma.app.util.toPrice

@Composable
fun SettleUpAmount(
    modifier: Modifier = Modifier,
    amount: Double,
    currency: String
) {

        val icon: Int
        val color: Color
        val hint: String
        if (amount >= 0) {
            icon = R.drawable.ic_arrow_square_down
            color = MaterialTheme.colors.secondary
            hint = "باید دریافت کنید"
        } else {
            icon = R.drawable.ic_arrow_square_up
            color = MaterialTheme.colors.error
            hint = "باید پرداخت کنید"
        }
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(IconSmall),
                painter = painterResource(icon),
                tint = color,
                contentDescription = null
            )
            Column {
                TextBody2(text = amount.toPrice(currency), color = color)
                THSpacer()
                HintText(hint)
            }
        }


}

