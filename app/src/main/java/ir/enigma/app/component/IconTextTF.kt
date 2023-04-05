package ir.enigma.app.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.EnigmaAppTheme
import ir.enigma.app.ui.theme.IconMedium
import ir.enigma.app.ui.theme.IconVerySmall

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    trueVar: Boolean,
    text: String,
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
            TextSubtitle1(
                modifier = Modifier.padding(start = 6.dp),
                text = "$text تومان",
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
            TextSubtitle1(
                modifier = Modifier.padding(start = 6.dp),
                text = "$text تومان",
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Preview
@Composable
fun prev() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        EnigmaAppTheme {
            IconText(trueVar = true, text = "16000")
        }
    }
}
