package ir.enigma.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.enigma.app.data.userE
import ir.enigma.app.model.User

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    isMe: Boolean = false,

    trueVar: Boolean,
    amount: Double,
    currency: String
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        AvatarWithText(user = user, isMe = isMe)

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

