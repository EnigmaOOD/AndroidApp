package ir.enigma.app.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import ir.enigma.app.R
import ir.enigma.app.ui.theme.IconVerySmall
import ir.enigma.app.util.toPrice

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    amount: Double,
    currency: String,
    contentDescription: String? = null
) {
    if (amount > 0) {
        Row(modifier = modifier) {
            Icon(
                modifier = Modifier
                    .size(IconVerySmall)
                    .align(alignment = Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_arrow_circle_down),
                contentDescription = contentDescription,
                tint = MaterialTheme.colors.secondary
            )
            THSpacer()
            HintText(
                modifier = Modifier.testTag("deptOrCreditAmount"),
                text = "${amount.toPrice()} $currency", color = MaterialTheme.colors.secondary
            )
        }
    } else if (amount < 0) {
        Row(modifier = modifier) {
            Icon(
                modifier = Modifier
                    .size(IconVerySmall)
                    .align(alignment = Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_arrow_circle_up),
                contentDescription = contentDescription,
                tint = MaterialTheme.colors.error
            )
            THSpacer()
            HintText(
                modifier = Modifier.testTag("deptOrCreditAmount"),
                text = "${amount.toPrice()} $currency", color = MaterialTheme.colors.error
            )
        }
    } else {
        HintText(modifier = modifier, text = "0 $currency")
    }
}