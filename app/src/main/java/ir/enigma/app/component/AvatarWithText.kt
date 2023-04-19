package ir.enigma.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.data.userAvatars
import ir.enigma.app.data.userB
import ir.enigma.app.model.User
import ir.enigma.app.ui.theme.IconLarge

@Composable
fun AvatarWithText(user: User, isMe: Boolean = false) {
    Row() {
        CardWithImageOrIcon(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            icon = false,
            resource = userAvatars[user.iconId],
            size = IconLarge,
            contentPadding = 0.dp,
            contentDescription = user.name,
            border = isMe,
        )

        TextBody2(
            text = user.name,
            modifier = Modifier
                .padding(start = 12.dp)
                .align(alignment = Alignment.CenterVertically),
        )
    }
}

@Preview
@Composable
fun sh() {
    RtlThemePreview {
        AvatarWithText(userB)
    }
}