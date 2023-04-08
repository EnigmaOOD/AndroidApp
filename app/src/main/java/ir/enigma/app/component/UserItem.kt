package ir.enigma.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.data.userAvatars
import ir.enigma.app.data.userE
import ir.enigma.app.model.User
import ir.enigma.app.ui.theme.*

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,

    trueVar: Boolean,
    amount: Double,
    currency: String
) {
    Row(modifier = modifier) {
        CardWithImageOrIcon(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            icon = false,
            resource = userAvatars[user.iconId],
            size = IconLarge,
            contentPadding = 0.dp,
            contentDescription = user.name,
        )

        TextH6(
            text = user.name,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
                .align(alignment = Alignment.CenterVertically),
        )

        IconText(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            trueVar = trueVar,
            amount = amount,
            currency = currency,
            contentDescription = "Debt or credit"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previ() {
    RtlThemePreview {
        UserItem(
            user = userE,
            trueVar = false,
            amount = 16000.0,
            currency = "تومان"
        )
    }
}

