package ir.enigma.app.ui.main.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.me
import ir.enigma.app.data.userAvatars
import ir.enigma.app.model.User
import ir.enigma.app.ui.theme.*


@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    me: User,
    credit: Double,
    dept: Double,
    currency: String
) {

    Column(
        modifier = modifier,
    ) {

        LogoAndAppName()
        MVSpacer()
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            CardWithImageOrIcon(
                size = IconVeryLarge,
                icon = false,
                elevation = ElevationSmall,
                resource = userAvatars[me.iconId],
                contentPadding = 0.dp,
                backgroundColor = UserProfileBackground
            )


            SettleUpAmount(amount = credit, currency = currency, isCredit = true)

            HorizontalDivider(verticalPadding = IconVeryLarge/4)

            SettleUpAmount(amount = dept, currency = currency, isCredit = false)

        }

    }
}

@Composable
fun LogoAndAppName() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            tint = MaterialTheme.colors.primary,
            contentDescription = "App Icon",
            modifier = Modifier.size(IconLarge)
        )
        SHSpacer()
        TextH6(stringResource(R.string.app_name))
    }
}

@Preview
@Composable
fun MainTopBarPreview() {
    RtlThemePreview {
        Surface {

            Card {
                MainTopBar(
                    Modifier.fillMaxWidth().padding(SpaceMedium),
                    me,
                    32000.0,
                    12000.0,
                    "تومان"
                )
            }
        }
    }
}