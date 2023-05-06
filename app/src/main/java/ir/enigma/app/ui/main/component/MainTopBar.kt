package ir.enigma.app.ui.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

import ir.enigma.app.data.userAvatars
import ir.enigma.app.model.User
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier.weight(1f)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = ElevationSmall,
                    shape = CircleShape
                ) {
                    Image(
                        painterResource(userAvatars[me.iconId]),
                        contentDescription = "User Avatar"
                    )
                }
            }


            MHSpacer()

            SettleUpAmount(amount = credit, currency = currency, isCredit = true)
            SHSpacer()
            Box(
                modifier = Modifier
                    .height(IconMedium)
                    .width(1.dp)
                    .background(color = MaterialTheme.colors.onBackgroundAlpha3)
            )
            SHSpacer()
            SettleUpAmount(amount = dept, currency = currency, isCredit = false)

        }

    }
}

@Composable
fun LogoAndAppName() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.app_logo),
            tint = MaterialTheme.colors.primary,
            contentDescription = "App Icon",
            modifier = Modifier.size(IconLarge)
        )
        SHSpacer()
        TextH6(stringResource(R.string.app_name), color = MaterialTheme.colors.primary)
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