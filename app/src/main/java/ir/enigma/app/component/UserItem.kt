package ir.enigma.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.EnigmaAppTheme
import ir.enigma.app.ui.theme.IconDefaultPadding
import ir.enigma.app.ui.theme.IconMedium

@Composable
fun UserItem(
    modifier: Modifier = Modifier,

    CModifier: Modifier = Modifier,
    Cicon: Boolean,
    Cresource: Int,
    Csize: Dp = IconMedium,
    CcontentPadding: Dp = IconDefaultPadding,
    Ctint: Color = MaterialTheme.colors.onBackground,
    Celevation: Dp = 0.dp,
    CbackgroundColor: Color = MaterialTheme.colors.background,
    CcontentDescription: String? = null,

    Tmodifier: Modifier = Modifier,
    Ttext: String,
    Tcolor: Color = MaterialTheme.colors.onBackground,

    Imodifier: Modifier = Modifier,
    ItrueVar: Boolean,
    Itext: String,
    IcontentDescription: String? = null
) {
    Row(modifier = modifier) {
        CardWithImageOrIcon(
            modifier = CModifier.align(alignment = Alignment.CenterVertically),
            icon = Cicon,
            resource = Cresource,
            size = Csize,
            contentPadding = CcontentPadding,
            tint = Ctint,
            elevation = Celevation,
            backgroundColor = CbackgroundColor,
            contentDescription = CcontentDescription
        )

        TextH6(
            text = Ttext,
            modifier = Tmodifier
                .padding(start = 12.dp)
                .weight(1f)
                .align(alignment = Alignment.CenterVertically),
            color = Tcolor
        )

        IconText(
            modifier = Imodifier.align(alignment = Alignment.CenterVertically),
            trueVar = ItrueVar,
            text = Itext,
            contentDescription = IcontentDescription
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previ() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        EnigmaAppTheme {
            UserItem(
                Cicon = false,
                Cresource = R.drawable.ic_fill_birthday_cake,
                Csize = 50.dp,
                CcontentPadding = 12.dp,
                Ttext = "علی علوی",
                ItrueVar = false,
                Itext = "16000"
            )
        }
    }
}