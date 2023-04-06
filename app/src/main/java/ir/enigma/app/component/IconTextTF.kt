package ir.enigma.app.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.IconVerySmall
import ir.enigma.app.util.toPrice

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    trueVar: Boolean,
    amount: Double,
    currency: String,
    contentDescription: String? = null
) {
    if (trueVar) {
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
            TextSubtitle1(
                text = "${amount.toPrice()} $currency",
                color = MaterialTheme.colors.secondary
            )
        }
    } else {
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
            TextSubtitle1(
                text = "${amount.toPrice()} $currency",
                color = MaterialTheme.colors.error
            )
        }
    }
}